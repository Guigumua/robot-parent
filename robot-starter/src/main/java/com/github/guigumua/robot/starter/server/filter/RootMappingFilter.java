package com.github.guigumua.robot.starter.server.filter;

import com.github.guigumua.robot.common.event.Event;
import com.github.guigumua.robot.starter.annotation.GlobalFilter;
import com.github.guigumua.robot.starter.annotation.MatchType;
import com.github.guigumua.robot.starter.annotation.MatchType.MatchTypeModel;
import com.github.guigumua.robot.starter.server.listener.ListenerContext;

@GlobalFilter
public class RootMappingFilter implements MappingFilter {
	@Override
	public boolean apply(Event event, ListenerContext context, Object... args) {
		MatchTypeModel matchTypeModel = context.getMatchTypeModel();
		switch (matchTypeModel) {
		case ANY: {
			MatchType[] matchTypes = context.getMatchTypes();
			for (MatchType matchType : matchTypes) {
				if (matchType.matches(context, event)) {
					return true;
				}
			}

			return false;
		}
		case NONE: {
			MatchType[] matchTypes = context.getMatchTypes();
			for (MatchType matchType : matchTypes) {
				if (matchType.matches(context, event)) {
					return false;
				}
			}
			return true;
		}
		case ALL: {
			MatchType[] matchTypes = context.getMatchTypes();
			for (MatchType matchType : matchTypes) {
				if (!matchType.matches(context, event)) {
					return false;
				}
			}
			return true;
		}
		default:
			return true;
		}

	}

	@Override
	public int order() {
		return Integer.MIN_VALUE;
	}
}
