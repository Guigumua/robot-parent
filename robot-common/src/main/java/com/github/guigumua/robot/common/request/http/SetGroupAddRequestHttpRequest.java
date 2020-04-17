package com.github.guigumua.robot.common.request.http;

import com.github.guigumua.robot.common.request.CoolQHttpRequest;
import com.github.guigumua.robot.common.request.SetGroupAddRequestRequest;

public class SetGroupAddRequestHttpRequest extends SetGroupAddRequestRequest implements CoolQHttpRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2514247245528756932L;
	private final String uri = "/set_group_add_request";

	@Override
	public String uri() {
		return this.uri;
	}
}
