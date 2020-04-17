package com.github.guigumua.robot.common.request.ws;

import com.github.guigumua.robot.common.request.CoolQWebSocketRequest;
import com.github.guigumua.robot.common.request.GetCredentialsRequest;

public class GetCredentialsWsRequest extends GetCredentialsRequest implements CoolQWebSocketRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1277104261620868470L;
	private final String action = "get_credentials";

	public String getAction() {
		return this.action;
	}
}
