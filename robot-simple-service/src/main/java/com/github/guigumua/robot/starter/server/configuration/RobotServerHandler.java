package com.github.guigumua.robot.starter.server.configuration;

import java.lang.reflect.Parameter;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.guigumua.robot.commom.event.Event;
import com.github.guigumua.robot.commom.event.EventType;
import com.github.guigumua.robot.commom.event.bean.message.MessageEvent;
import com.github.guigumua.robot.commom.util.JsonUtil;
import com.github.guigumua.robot.common.client.RobotClient;
import com.github.guigumua.robot.starter.configuration.RobotManager;
import com.github.guigumua.robot.starter.server.filter.EventFilter;
import com.github.guigumua.robot.starter.server.listenerhandler.HandlerManager;
import com.github.guigumua.robot.starter.server.listenerhandler.ListenerHandler;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

@Sharable
public class RobotServerHandler extends ChannelInboundHandlerAdapter {
	private static final Logger logger = LoggerFactory.getLogger(RobotServerHandler.class);
	private HandlerManager handlerManager;
	private RobotManager robotManager;

	public RobotServerHandler(HandlerManager handlerManager, RobotManager robotManager) {
		this.handlerManager = handlerManager;
		this.robotManager = robotManager;
	}

	private static final DefaultFullHttpResponse DEFAULT_RESPONSE = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
			HttpResponseStatus.NO_CONTENT);

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		long start = System.currentTimeMillis();
		FullHttpRequest request = (FullHttpRequest) msg;
		String json = request.content().toString(CharsetUtil.UTF_8);
		Event event = JsonUtil.getEvent(json);
		long id = Long.parseLong(request.headers().get("X-Self-ID"));
		RobotClient client = robotManager.get(id);
		/*
		 * 如果接收到的事件来自没有在robotManager中注册的cq端，就新增到robotManager中
		 */
		if (client == null) {
			logger.info("开始通过上报事件注册机器人，使用默认端口5700");
			InetSocketAddress addr = (InetSocketAddress) ctx.channel().remoteAddress();
			String host = addr.getHostName();
			robotManager.registerRobotClient(id,host, 5700);
		}
		// 获取监听这个事件的listenerHandler集合
		EventType type = event.getEventType();
		Set<ListenerHandler> handlers = handlerManager.getHandlers(type);
		// 如果是监听消息事件，先正则过滤
		if (EventType.isListenMsg(type.getCode())) {
			handlers = handlers.stream().filter(handler -> {
				String regex = handler.getRegex();
				String message = ((MessageEvent) event).getMessage();
				return message.matches(regex);
			}).collect(Collectors.toSet());
		}
		// 执行
		boolean isResponsed = false; // 是否直接响应过
		for (ListenerHandler handler : handlers) {
			// 拦截器
			EventFilter[] filters = handler.getFilters();
			boolean isFilter = false;
			for (EventFilter filter : filters) {
				if (filter.doFilter(event, client)) {
					isFilter = true;
					break;
				}
			}
			// 有拦截器返回true则跳过
			if (isFilter)
				continue;
			// 参数注入
			Map<Integer, Parameter> paramsMap = handler.getParamsMap();
			Object[] params = handler.getParams();
			for (Entry<Integer, Parameter> e : paramsMap.entrySet()) {
				Integer i = e.getKey();
				Parameter p = e.getValue();
				// 如果事件的类是参数类的子类
				if (p.getType().isInstance(event)) {
					params[i] = event;
				}
				// 如果参数类是RobotClient
				if (p.getType().isAssignableFrom(RobotClient.class)) {
					params[i] = client;
				}
			}
			// 执行
			Object result = handler.getMethod().invoke(handler.getInvokeObj(), params);
			// 直接响应，只会执行第一个直接响应
			if (result instanceof Event.EventResponse && !isResponsed) {
				DefaultFullHttpResponse res = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
				res.content().writeBytes(JsonUtil.toJSON(result).getBytes(CharsetUtil.UTF_8));
				res.headers().add(HttpHeaderNames.CONTENT_LENGTH, res.content().readableBytes());
				ctx.writeAndFlush(res);
				isResponsed = true;
			}
			// 执行完成后是否跳过其他监听器的执行
			if (handler.isBreak()) {
				break;
			}
		}
		if (isResponsed) {
			long end = System.currentTimeMillis();
			logger.debug("处理事件用时：{}ms", end - start);
			return;
		}
		DEFAULT_RESPONSE.retain();
		ctx.writeAndFlush(DEFAULT_RESPONSE);
		long end = System.currentTimeMillis();
		logger.debug("处理事件用时：{}ms", end - start);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
	}

}
