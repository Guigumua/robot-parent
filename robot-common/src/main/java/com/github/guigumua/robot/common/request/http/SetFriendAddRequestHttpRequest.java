package com.github.guigumua.robot.common.request.http;

import com.github.guigumua.robot.common.request.CoolQHttpRequest;
import com.github.guigumua.robot.common.request.SetFriendAddRequestRequest;

public class SetFriendAddRequestHttpRequest extends SetFriendAddRequestRequest implements CoolQHttpRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6173430712453021310L;
	private final String uri = "/set_friend_add_request";

	@Override
	public String uri() {
		return this.uri;
	}
}
