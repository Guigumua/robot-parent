package com.github.guigumua.robot.common.request.ws;

import com.github.guigumua.robot.common.request.CoolQWebSocketRequest;
import com.github.guigumua.robot.common.request.GetImageRequest;

public class GetImageWsRequest extends GetImageRequest implements CoolQWebSocketRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8110122256271661345L;
	private final String action = "get_image";

	public String getAction() {
		return this.action;
	}
}
