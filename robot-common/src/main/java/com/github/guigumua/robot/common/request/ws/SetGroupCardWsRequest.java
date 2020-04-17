package com.github.guigumua.robot.common.request.ws;

import com.github.guigumua.robot.common.request.CoolQWebSocketRequest;
import com.github.guigumua.robot.common.request.SetGroupCardRequest;

public class SetGroupCardWsRequest extends SetGroupCardRequest implements CoolQWebSocketRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2706100476252022361L;
	private final String action = "set_group_card";

	public String getAction() {
		return this.action;
	}

}
