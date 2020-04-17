package com.github.guigumua.robot.common.request.http;

import com.github.guigumua.robot.common.request.CoolQHttpRequest;
import com.github.guigumua.robot.common.request.GetFriendListRequest;

public class GetFriendListHttpRequest extends GetFriendListRequest implements CoolQHttpRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3825540683927729685L;
	private final String uri = "/get_friend_list";

	@Override
	public String uri() {
		return this.uri;
	}
}
