package com.github.guigumua.robot.common.request.ws;

import com.github.guigumua.robot.common.request.CoolQWebSocketRequest;
import com.github.guigumua.robot.common.request.SetGroupLeaveRequest;

public class SetGroupLeaveWsRequest extends SetGroupLeaveRequest implements CoolQWebSocketRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5891358393362785106L;
	private final String action = "set_group_leave";

	public String getAction() {
		return this.action;
	}
}
