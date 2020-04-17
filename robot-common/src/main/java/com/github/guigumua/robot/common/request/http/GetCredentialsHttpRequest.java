package com.github.guigumua.robot.common.request.http;

import com.github.guigumua.robot.common.request.CoolQHttpRequest;
import com.github.guigumua.robot.common.request.GetCredentialsRequest;

public class GetCredentialsHttpRequest extends GetCredentialsRequest implements CoolQHttpRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1277104261620868470L;
	private final String uri = "/get_credentials";

	@Override
	public String uri() {
		return this.uri;
	}
}
