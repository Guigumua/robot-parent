package com.github.guigumua.robot.common.request.http;

import com.github.guigumua.robot.common.request.CoolQHttpRequest;
import com.github.guigumua.robot.common.request.SetGroupAdminRequest;

public class SetGroupAdminHttpRequest extends SetGroupAdminRequest implements CoolQHttpRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7836429090309600502L;
	private final String uri = "/set_group_admin";

	@Override
	public String uri() {
		return this.uri;
	}
}
