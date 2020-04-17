package com.github.guigumua.robot.starter.server.filter;

import com.github.guigumua.robot.common.event.Event;
import com.github.guigumua.robot.starter.server.listener.ListenerContext;

public interface ListenerFilter {
	/**
	 * 拦截器接口定义
	 * 
	 * @param event   事件
	 * @param context 监听器上下文
	 * @param args    将来可能进行拓展
	 * @return
	 */
	boolean apply(Event event, ListenerContext context, Object... args);
}
