package com.github.guigumua.robot.common.request.http;

import com.github.guigumua.robot.common.request.CoolQHttpRequest;
import com.github.guigumua.robot.common.request.GetVersionInfoRequest;

public class GetVersionInfoHttpRequest extends GetVersionInfoRequest implements CoolQHttpRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3476277689442448511L;
	private final String uri = "/get_version_info";

	@Override
	public String uri() {
		return this.uri;
	}

}
