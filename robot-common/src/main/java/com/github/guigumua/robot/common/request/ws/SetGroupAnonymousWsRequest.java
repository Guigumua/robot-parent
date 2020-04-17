package com.github.guigumua.robot.common.request.ws;

import com.github.guigumua.robot.common.request.CoolQWebSocketRequest;
import com.github.guigumua.robot.common.request.SetGroupAnonymousRequest;

public class SetGroupAnonymousWsRequest extends SetGroupAnonymousRequest implements CoolQWebSocketRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4472305033622545541L;
	private final String action = "set_group_anonymous";

	public String getAction() {
		return this.action;
	}
}
