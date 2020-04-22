package com.github.guigumua.robot.starter.test.filter;

import com.github.guigumua.robot.common.event.Event;
import com.github.guigumua.robot.starter.annotation.GlobalFilter;
import com.github.guigumua.robot.starter.server.filter.MappingFilter;
import com.github.guigumua.robot.starter.server.listener.ListenerContext;

@GlobalFilter
public class TestMappingFilter implements MappingFilter {

	@Override
	public boolean apply(Event event, ListenerContext context, Object... args) {
		return true;
	}

	@Override
	public int order() {
		return Integer.MIN_VALUE;
	}
}
