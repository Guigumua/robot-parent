package com.github.guigumua.robot.common.request.ws;

import com.github.guigumua.robot.common.request.CoolQWebSocketRequest;
import com.github.guigumua.robot.common.request.GetCookiesRequest;

public class GetCookiesWsRequest extends GetCookiesRequest implements CoolQWebSocketRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1210788646165987595L;
	private final String action = "get_cookies";

	public String getAction() {
		return this.action;
	}
}
