package com.github.guigumua.robot.common.request.http;

import com.github.guigumua.robot.common.request.CoolQHttpRequest;
import com.github.guigumua.robot.common.request.SetGroupCardRequest;

public class SetGroupCardHttpRequest extends SetGroupCardRequest implements CoolQHttpRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2706100476252022361L;
	private final String uri = "/set_group_card";

	@Override
	public String uri() {
		return this.uri;
	}
}
