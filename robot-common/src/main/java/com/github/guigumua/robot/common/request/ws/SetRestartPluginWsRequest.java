package com.github.guigumua.robot.common.request.ws;

import com.github.guigumua.robot.common.request.CoolQWebSocketRequest;
import com.github.guigumua.robot.common.request.SetRestartPluginRequest;

public class SetRestartPluginWsRequest extends SetRestartPluginRequest implements CoolQWebSocketRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3031345574243294931L;
	private final String action = "set_restart_plugin";

	public String getAction() {
		return this.action;
	}

}
