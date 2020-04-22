package com.github.guigumua.robot.starter.server;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.guigumua.robot.common.event.Event;
import com.github.guigumua.robot.starter.client.RobotClient;
import com.github.guigumua.robot.starter.client.RobotManager;
import com.github.guigumua.robot.starter.server.filter.MappingFilter;
import com.github.guigumua.robot.starter.server.filter.PostFilter;
import com.github.guigumua.robot.starter.server.filter.PreFilter;
import com.github.guigumua.robot.starter.server.listener.ListenerContext;
import com.github.guigumua.robot.starter.server.listener.ListenerManager;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

public interface RobotServerEventProcessor {
	public static final Logger logger = LoggerFactory.getLogger(RobotServerEventProcessor.class);

	/**
	 * 解析msg
	 *
	 * @param msg 读事件发生时的消息内容
	 * @return 事件
	 */
	Event resolveEvent(Object msg);

	/**
	 * 进行默认响应
	 */
	default void defaultRespond(ChannelHandlerContext ctx) {
	}

	/**
	 * 通过事件 映射到可以执行的该事件的监听器
	 *
	 * @param event 事件
	 * @return 事件所映射的监听器集合
	 */
	default Set<ListenerContext> mapping(Event event, Set<MappingFilter> globalMappingFilters) {
		ListenerManager manager = ListenerManager.getInstance();
		Set<ListenerContext> listenerContexts = manager.getListenerChain(event.getEventType());
		listenerContexts = listenerContexts.stream().filter(listenerContext -> {
			for (MappingFilter filter : globalMappingFilters) {
				if (!filter.apply(event, listenerContext)) {
					logger.info("被全局映射拦截器：{}所拦截", filter);
					return false;
				}
			}
			List<MappingFilter> mappingFilters = listenerContext.getMappingFilters();
			for (MappingFilter filter : mappingFilters) {
				if (!filter.apply(event, listenerContext)) {
					logger.info("被映射拦截器：{}所拦截", filter);
					return false;
				}
			}
			return true;
		}).collect(Collectors.toSet());
		logger.debug("事件映射的监听器列表：{}", listenerContexts);
		return listenerContexts;
	}

	/**
	 * 参数注入
	 *
	 * @param context 监听器上下文
	 * @param event   事件
	 */
	default Object[] paramInject(ListenerContext context, Event event) {
		// 参数注入
		Map<Integer, Parameter> paramsMap = context.getParamsMap();
		Object[] preParams = context.getParams();
		int length = preParams.length;
		Object[] params = new Object[length];
		System.arraycopy(params, 0, preParams, 0, length);
		for (Map.Entry<Integer, Parameter> e : paramsMap.entrySet()) {
			Integer i = e.getKey();
			Parameter p = e.getValue();
			// 如果事件的类是参数类的子类
			if (p.getType().isInstance(event)) {
				params[i] = event;
			}
			// 如果参数类是RobotClient
			if (p.getType().isAssignableFrom(RobotClient.class)) {
				params[i] = RobotManager.getInstance().get(event.getSelfId());
			}
			Field[] fields = event.getClass().getFields();
			// event的参数
			for (Field field : fields) {
				if (field.getName().equals(p.getName()) && field.getType() == p.getType()) {
					try {
						params[i] = field.get(event);
					} catch (IllegalAccessException illegalAccessException) {
						illegalAccessException.printStackTrace();
					}
				}
			}
		}
		logger.debug("参数注入完成");
		return params;
	}

	/**
	 * @param context       监听上下文
	 * @param event         事件
	 * @param globalFilters 全局拦截器
	 * @return 是否放行
	 */
	default boolean preFilter(ListenerContext context, Event event, Object[] params, Set<PreFilter> globalFilters) {
		for (PreFilter filter : globalFilters) {
			if (!filter.apply(event, context, params)) {
				logger.debug("监听器被全局前置拦截器：{}拦截了", filter.getClass().getName());
				return false;
			}
		}
		for (PreFilter filter : context.getPreFilters()) {
			if (!filter.apply(event, context, params)) {
				logger.debug("监听器被前置拦截器：{}拦截了", filter.getClass().getName());
				return false;
			}
		}
		logger.debug("通过PreFilter处理");
		return true;
	}

	/**
	 * 执行监听器
	 *
	 * @param context 监听器上下文
	 * @param event   事件
	 */
	default Object doHandler(ListenerContext context, Event event, Object... params) throws Exception {
		return context.getMethod().invoke(context.getInvokeObj(), params);
	}

	/**
	 * 后置拦截器
	 *
	 * @param context 监听器上下文
	 * @param event   事件
	 * @return 是否拦截后续逻辑（v0.0.1目前只能拦截@Listener注解中的isBreak）
	 */
	default boolean postFilter(ListenerContext context, Event event, Object result, Set<PostFilter> globalPostFilters) {
		for (PostFilter filter : globalPostFilters) {
			if (!filter.apply(event, context, result)) {
				return false;
			}
		}
		for (PostFilter filter : context.getPostFilters()) {
			if (!filter.apply(event, context, result)) {
				return false;
			}
		}
		logger.debug("通过PostFilter处理");
		return true;
	}

	/**
	 * 结果处理器
	 *
	 * @param context 监听器上下文
	 * @param channel 此次事件中读写用的通道
	 * @return 是否用通道写回了结果
	 */
	default boolean resultHandler(ListenerContext context, Object result, Channel channel) {
		return false;
	}
}
