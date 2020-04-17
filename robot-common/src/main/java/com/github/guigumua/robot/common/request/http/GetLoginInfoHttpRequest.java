package com.github.guigumua.robot.common.request.http;

import com.github.guigumua.robot.common.request.CoolQHttpRequest;
import com.github.guigumua.robot.common.request.GetLoginInfoRequest;

public class GetLoginInfoHttpRequest extends GetLoginInfoRequest implements CoolQHttpRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7922869805256090035L;
	private final String uri = "/get_login_info";

	@Override
	public String uri() {
		return this.uri;
	}
}
