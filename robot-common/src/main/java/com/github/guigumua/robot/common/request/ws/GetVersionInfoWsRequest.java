package com.github.guigumua.robot.common.request.ws;

import com.github.guigumua.robot.common.request.CoolQWebSocketRequest;
import com.github.guigumua.robot.common.request.GetVersionInfoRequest;

public class GetVersionInfoWsRequest extends GetVersionInfoRequest implements CoolQWebSocketRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3476277689442448511L;
	private final String action = "get_version_info";

	public String getAction() {
		return this.action;
	}

}
