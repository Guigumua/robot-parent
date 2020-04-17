package com.github.guigumua.robot.common.request.http;

import com.github.guigumua.robot.common.request.CoolQHttpRequest;
import com.github.guigumua.robot.common.request.GetGroupMemberInfoRequest;

public class GetGroupMemberInfoHttpRequest extends GetGroupMemberInfoRequest implements CoolQHttpRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6834714629731296330L;
	private final String uri = "/get_group_member_info";

	@Override
	public String uri() {
		return this.uri;
	}
}
