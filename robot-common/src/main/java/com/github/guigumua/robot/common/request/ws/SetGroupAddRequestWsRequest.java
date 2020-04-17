package com.github.guigumua.robot.common.request.ws;

import com.github.guigumua.robot.common.request.CoolQWebSocketRequest;
import com.github.guigumua.robot.common.request.SetGroupAddRequestRequest;

public class SetGroupAddRequestWsRequest extends SetGroupAddRequestRequest implements CoolQWebSocketRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2514247245528756932L;
	private final String action = "set_group_add_request";

	public String getAction() {
		return this.action;
	}

}
