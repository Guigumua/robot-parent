package com.github.guigumua.robot.common.request.ws;

import com.github.guigumua.robot.common.request.CoolQWebSocketRequest;
import com.github.guigumua.robot.common.request.SetGroupAnonymousBanRequest;

public class SetGroupAnonymousBanWsRequest extends SetGroupAnonymousBanRequest implements CoolQWebSocketRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1268618030281251821L;
	private final String action = "set_group_anonymous_ban";

	public String getAction() {
		return this.action;
	}

}
