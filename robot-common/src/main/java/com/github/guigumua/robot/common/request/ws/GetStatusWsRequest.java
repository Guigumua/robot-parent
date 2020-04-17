package com.github.guigumua.robot.common.request.ws;

import com.github.guigumua.robot.common.request.CoolQWebSocketRequest;
import com.github.guigumua.robot.common.request.GetStatusRequest;

public class GetStatusWsRequest extends GetStatusRequest implements CoolQWebSocketRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8244846239177347605L;
	private final String action = "get_status";

	public String getAction() {
		return this.action;
	}
}
