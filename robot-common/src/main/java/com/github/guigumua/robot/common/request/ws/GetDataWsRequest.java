package com.github.guigumua.robot.common.request.ws;

import com.github.guigumua.robot.common.request.CoolQWebSocketRequest;
import com.github.guigumua.robot.common.request.GetDataRequest;

public class GetDataWsRequest extends GetDataRequest implements CoolQWebSocketRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 458658451602753745L;
	private final String action = "data";

	public String getAction() {
		return this.action;
	}
}
