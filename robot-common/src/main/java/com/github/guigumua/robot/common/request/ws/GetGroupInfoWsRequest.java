package com.github.guigumua.robot.common.request.ws;

import com.github.guigumua.robot.common.request.CoolQWebSocketRequest;
import com.github.guigumua.robot.common.request.GetGroupInfoRequest;

public class GetGroupInfoWsRequest extends GetGroupInfoRequest implements CoolQWebSocketRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 137199848338169887L;
	private final String action = "get_group_info";

	public String getAction() {
		return this.action;
	}

}
