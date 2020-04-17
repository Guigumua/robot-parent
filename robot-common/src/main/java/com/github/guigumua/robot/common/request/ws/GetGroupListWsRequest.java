package com.github.guigumua.robot.common.request.ws;

import com.github.guigumua.robot.common.request.CoolQWebSocketRequest;
import com.github.guigumua.robot.common.request.GetGroupListRequest;

public class GetGroupListWsRequest extends GetGroupListRequest implements CoolQWebSocketRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1714810955761835859L;
	private final String action = "get_group_list";

	public String getAction() {
		return this.action;
	}
}
