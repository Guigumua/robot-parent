package com.github.guigumua.robot.common.request.ws;

import com.github.guigumua.robot.common.request.CoolQWebSocketRequest;
import com.github.guigumua.robot.common.request.GetLoginInfoRequest;

public class GetLoginInfoWsRequest extends GetLoginInfoRequest implements CoolQWebSocketRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7922869805256090035L;
	private final String action = "get_login_info";

	public String getAction() {
		return this.action;
	}
}
