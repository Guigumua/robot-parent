package com.github.guigumua.robot.common.request.ws;

import com.github.guigumua.robot.common.request.CoolQWebSocketRequest;
import com.github.guigumua.robot.common.request.SetDiscussLeaveRequest;

public class SetDiscussLeaveWsRequest extends SetDiscussLeaveRequest implements CoolQWebSocketRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7909677304753152410L;
	private final String action = "set_discuss_leave";

	public String getAction() {
		return this.action;
	}
}
