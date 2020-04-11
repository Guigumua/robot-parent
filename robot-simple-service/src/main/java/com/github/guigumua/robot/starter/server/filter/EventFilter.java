package com.github.guigumua.robot.starter.server.filter;

import com.github.guigumua.robot.commom.event.Event;
import com.github.guigumua.robot.common.client.RobotClient;

public interface EventFilter {

	boolean doFilter(Event e, RobotClient client);

}
