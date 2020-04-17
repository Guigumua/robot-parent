package com.github.guigumua.robot.starter.server.configuration;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import org.springframework.context.ApplicationContextAware;

import com.github.guigumua.robot.common.event.Event;
import com.github.guigumua.robot.starter.server.listener.ListenerContext;

import io.netty.channel.Channel;

public interface RobotServer extends ApplicationContextAware {
	/**
	 * 解析msg
	 * 
	 * @param msg 读事件发生时的消息内容
	 * @return
	 */
	Event resolveMsg(Object msg);

	/**
	 * 通过事件注册机器人
	 * 
	 * @param channel
	 * @param event
	 */
	void registerClientByEvent(Channel channel, Event event);

	/**
	 * 监听器映射过滤器，定义监听器与事件的映射规则
	 * 
	 * @param event 事件
	 * @return
	 */
	Set<ListenerContext> mappingFilter(Event event);

	/**
	 * 参数注入
	 * 
	 * @param context
	 * @param event
	 */
	void paramInject(ListenerContext context, Event event);

	/**
	 * 前置拦截器，定义监听器执行前要做的任务
	 * 
	 * @param context 监听器上下文，包含监听器的详细信息
	 * @param event   事件
	 * @param args    监听器上的参数列表
	 * @return 是否拦截
	 */
	boolean preFilter(ListenerContext context, Event event, Object... args);

	/**
	 * 执行监听器
	 * 
	 * @param context 监听器上下文
	 * @param event   事件
	 * @param args    参数列表
	 * @return 监听器的返回值
	 */
	Object doHandler(ListenerContext context, Event event, Object... args)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;

	/**
	 * 后置拦截器
	 * 
	 * @param context 监听器上下文
	 * @param event   事件
	 * @param args    参数列表
	 * @return 是否拦截后续逻辑（v0.0.1目前只能拦截@Listener注解中的isBreak）
	 */
	boolean postFilter(ListenerContext context, Event event, Object... args);

	/**
	 * 结果处理器
	 * 
	 * @param context 监听器上下文
	 * @param channel 此次事件中读写用的通道
	 * @return 是否用通道写回了结果
	 */
	boolean resultHandler(ListenerContext context, Channel channel, Object... args);

	/**
	 * 启动服务器
	 * 
	 * @throws InterruptedException
	 */
	void start() throws InterruptedException;

}
