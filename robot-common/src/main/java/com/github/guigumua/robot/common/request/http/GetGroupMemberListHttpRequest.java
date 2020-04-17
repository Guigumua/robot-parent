package com.github.guigumua.robot.common.request.http;

import com.github.guigumua.robot.common.request.CoolQHttpRequest;
import com.github.guigumua.robot.common.request.GetGroupMemberListRequest;

public class GetGroupMemberListHttpRequest extends GetGroupMemberListRequest implements CoolQHttpRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1363754286645015435L;
	private final String uri = "/get_group_member_info";

	@Override
	public String uri() {
		return this.uri;
	}
}
