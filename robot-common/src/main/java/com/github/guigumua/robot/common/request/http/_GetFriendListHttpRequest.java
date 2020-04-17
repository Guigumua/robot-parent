package com.github.guigumua.robot.common.request.http;

import com.github.guigumua.robot.common.request.CoolQHttpRequest;
import com.github.guigumua.robot.common.request._GetFriendListRequset;

public class _GetFriendListHttpRequest extends _GetFriendListRequset implements CoolQHttpRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7344949288998777474L;
	private final String uri = "/_get_friend_list";

	@Override
	public String uri() {
		return this.uri;
	}

}
