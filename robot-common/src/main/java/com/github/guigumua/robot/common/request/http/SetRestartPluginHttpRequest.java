package com.github.guigumua.robot.common.request.http;

import com.github.guigumua.robot.common.request.CoolQHttpRequest;
import com.github.guigumua.robot.common.request.SetRestartPluginRequest;

public class SetRestartPluginHttpRequest extends SetRestartPluginRequest implements CoolQHttpRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3031345574243294931L;
	private final String uri = "/set_restart_plugin";

	@Override
	public String uri() {
		return this.uri;
	}
}
