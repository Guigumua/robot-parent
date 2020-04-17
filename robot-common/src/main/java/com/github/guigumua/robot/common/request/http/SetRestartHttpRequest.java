package com.github.guigumua.robot.common.request.http;

import com.github.guigumua.robot.common.request.CoolQHttpRequest;
import com.github.guigumua.robot.common.request.SetRestartRequest;

public class SetRestartHttpRequest extends SetRestartRequest implements CoolQHttpRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5773736634403831196L;
	private final String uri = "/_set_restart";

	@Override
	public String uri() {
		return this.uri;
	}
}
