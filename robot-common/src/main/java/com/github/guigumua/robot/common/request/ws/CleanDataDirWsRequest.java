package com.github.guigumua.robot.common.request.ws;

import com.github.guigumua.robot.common.request.CleanDataDirRequest;
import com.github.guigumua.robot.common.request.CoolQWebSocketRequest;

public class CleanDataDirWsRequest extends CleanDataDirRequest implements CoolQWebSocketRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7905231475974163594L;
	private final String action = "clean_data_dir";

	public String getAction() {
		return this.action;
	}
}
