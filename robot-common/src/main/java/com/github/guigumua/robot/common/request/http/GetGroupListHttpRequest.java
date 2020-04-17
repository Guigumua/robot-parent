package com.github.guigumua.robot.common.request.http;

import com.github.guigumua.robot.common.request.CoolQHttpRequest;
import com.github.guigumua.robot.common.request.GetGroupListRequest;

public class GetGroupListHttpRequest extends GetGroupListRequest implements CoolQHttpRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1714810955761835859L;
	private final String uri = "/get_group_list";

	@Override
	public String uri() {
		return this.uri;
	}
}
