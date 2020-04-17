package com.github.guigumua.robot.common.request.ws;

import com.github.guigumua.robot.common.request.CoolQWebSocketRequest;
import com.github.guigumua.robot.common.request._GetFriendListRequset;

public class _GetFriendListWsRequest extends _GetFriendListRequset implements CoolQWebSocketRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4906383672818902911L;
	private final String action = "_get_friend_list";

	@Override
	public String getAction() {
		return this.action;
	}
}
