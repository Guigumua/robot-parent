package com.github.guigumua.robot.common.request.http;

import com.github.guigumua.robot.common.request.CoolQHttpRequest;
import com.github.guigumua.robot.common.request.GetGroupInfoRequest;

public class GetGroupInfoHttpRequest extends GetGroupInfoRequest implements CoolQHttpRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 137199848338169887L;
	private final String uri = "/get_group_info";

	@Override
	public String uri() {
		return this.uri;
	}
}
