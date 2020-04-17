package com.github.guigumua.robot.common.request.ws;

import com.github.guigumua.robot.common.request.CoolQWebSocketRequest;
import com.github.guigumua.robot.common.request.SetGroupAdminRequest;

public class SetGroupAdminWsRequest extends SetGroupAdminRequest implements CoolQWebSocketRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7836429090309600502L;
	private final String action = "set_group_admin";

	public String getAction() {
		return this.action;
	}
}
