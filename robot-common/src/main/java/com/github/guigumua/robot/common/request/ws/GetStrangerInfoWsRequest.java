package com.github.guigumua.robot.common.request.ws;

import com.github.guigumua.robot.common.request.CoolQWebSocketRequest;
import com.github.guigumua.robot.common.request.GetStrangerInfoRequest;

public class GetStrangerInfoWsRequest extends GetStrangerInfoRequest implements CoolQWebSocketRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5561324526569462104L;
	private final String action = "get_stranger_info";

	public String getAction() {
		return this.action;
	}

}
