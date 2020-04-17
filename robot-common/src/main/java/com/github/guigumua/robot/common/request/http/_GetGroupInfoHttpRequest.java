package com.github.guigumua.robot.common.request.http;

import com.github.guigumua.robot.common.request.CoolQHttpRequest;
import com.github.guigumua.robot.common.request._GetGroupInfoRequest;

public class _GetGroupInfoHttpRequest extends _GetGroupInfoRequest implements CoolQHttpRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6810189144251082338L;
	private final String uri = "/_get_group_info";

	@Override
	public String uri() {
		return this.uri;
	}
}
