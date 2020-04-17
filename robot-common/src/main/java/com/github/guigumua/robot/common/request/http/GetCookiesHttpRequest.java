package com.github.guigumua.robot.common.request.http;

import com.github.guigumua.robot.common.request.CoolQHttpRequest;
import com.github.guigumua.robot.common.request.GetCookiesRequest;

public class GetCookiesHttpRequest extends GetCookiesRequest implements CoolQHttpRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1210788646165987595L;
	private final String uri = "/get_cookies";

	@Override
	public String uri() {
		return this.uri;
	}
}
