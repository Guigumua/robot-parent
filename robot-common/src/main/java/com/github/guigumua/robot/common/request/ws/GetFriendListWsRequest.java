package com.github.guigumua.robot.common.request.ws;

import com.github.guigumua.robot.common.request.CoolQWebSocketRequest;
import com.github.guigumua.robot.common.request.GetFriendListRequest;

public class GetFriendListWsRequest extends GetFriendListRequest implements CoolQWebSocketRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3825540683927729685L;
	private final String action = "get_friend_list";

	public String getAction() {
		return this.action;
	}
}
