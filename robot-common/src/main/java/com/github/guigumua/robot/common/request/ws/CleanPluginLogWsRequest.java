package com.github.guigumua.robot.common.request.ws;

import com.github.guigumua.robot.common.request.CleanPluginLogRequest;
import com.github.guigumua.robot.common.request.CoolQWebSocketRequest;

public class CleanPluginLogWsRequest extends CleanPluginLogRequest implements CoolQWebSocketRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7539655302332954764L;
	private final String action = "clean_plugin_log";

	public String getAction() {
		return this.action;
	}
}
