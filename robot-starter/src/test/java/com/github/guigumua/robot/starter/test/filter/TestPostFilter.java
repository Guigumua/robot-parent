package com.github.guigumua.robot.starter.test.filter;

import com.github.guigumua.robot.common.event.Event;
import com.github.guigumua.robot.starter.server.filter.PostFilter;
import com.github.guigumua.robot.starter.server.listener.ListenerContext;

public class TestPostFilter implements PostFilter {

	@Override
	public boolean apply(Event event, ListenerContext context, Object... args) {
		return true;
	}

}
