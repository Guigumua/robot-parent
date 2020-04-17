package com.github.guigumua.robot.common.request.ws;

import com.github.guigumua.robot.common.request.CoolQWebSocketRequest;
import com.github.guigumua.robot.common.request.SetFriendAddRequestRequest;

public class SetFriendAddRequestWsRequest extends SetFriendAddRequestRequest implements CoolQWebSocketRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6173430712453021310L;
	private final String action = "set_friend_add_request";

	public String getAction() {
		return this.action;
	}
}
