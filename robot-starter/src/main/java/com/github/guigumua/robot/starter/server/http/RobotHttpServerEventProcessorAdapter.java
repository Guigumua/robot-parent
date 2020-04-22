package com.github.guigumua.robot.starter.server.http;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.guigumua.robot.common.event.Event;
import com.github.guigumua.robot.common.util.JsonUtil;
import com.github.guigumua.robot.starter.server.RobotServerEventProcessor;
import com.github.guigumua.robot.starter.server.listener.ListenerContext;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

public class RobotHttpServerEventProcessorAdapter implements RobotServerEventProcessor {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	protected final DefaultFullHttpResponse DEFAULT_RESPONSE = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
			HttpResponseStatus.NO_CONTENT);

	@Override
	public Event resolveEvent(Object msg) {
		if (msg instanceof FullHttpRequest) {
			FullHttpRequest request = (FullHttpRequest) msg;
			String idStr = request.headers().get("X-Self-ID");
			if (!StringUtils.isWhitespace(idStr)) {
				long id = Long.parseLong(idStr);
				Event event = JsonUtil.getEvent(request.content().toString(CharsetUtil.UTF_8));
				if (event != null) {
					event.setSelfId(id);
				}
				logger.debug("事件解析完成：{}", JsonUtil.toJSON(event));
				return event;
			}
		}
		return null;
	}

	@Override
	public void defaultRespond(ChannelHandlerContext ctx) {
		logger.debug("返回默认响应");
		ctx.writeAndFlush(DEFAULT_RESPONSE.retain());
	}

	@Override
	public boolean resultHandler(ListenerContext context, Object result, Channel channel) {
		if (result instanceof Event.EventResponse) {
			DefaultFullHttpResponse res = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
			ByteBuf content = res.content();
			String json = JsonUtil.toJSON(result);
			content.writeBytes(json.getBytes(CharsetUtil.UTF_8));
			res.headers().add(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
			channel.writeAndFlush(res);
			logger.debug("结果处理完成，返回直接响应：{}", json);
			return true;
		}
		return false;
	}
}
