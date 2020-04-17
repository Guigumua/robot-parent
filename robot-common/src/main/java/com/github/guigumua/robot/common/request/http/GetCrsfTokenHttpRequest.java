package com.github.guigumua.robot.common.request.http;

import com.github.guigumua.robot.common.request.CoolQHttpRequest;
import com.github.guigumua.robot.common.request.GetCrsfTokenRequest;

public class GetCrsfTokenHttpRequest extends GetCrsfTokenRequest implements CoolQHttpRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2575631155416308406L;
	private final String uri = "/get_csrf_token";

	@Override
	public String uri() {
		return this.uri;
	}
}
