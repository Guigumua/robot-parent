package com.github.guigumua.robot.common.request.ws;

import com.github.guigumua.robot.common.request.CoolQWebSocketRequest;
import com.github.guigumua.robot.common.request.GetCrsfTokenRequest;

public class GetCrsfTokenWsRequest extends GetCrsfTokenRequest implements CoolQWebSocketRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2575631155416308406L;
	private final String action = "get_csrf_token";

	public String getAction() {
		return this.action;
	}
}
