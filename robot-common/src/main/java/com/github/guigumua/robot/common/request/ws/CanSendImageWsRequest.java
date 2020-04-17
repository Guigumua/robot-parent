package com.github.guigumua.robot.common.request.ws;

import com.github.guigumua.robot.common.request.CanSendImageRequest;
import com.github.guigumua.robot.common.request.CoolQWebSocketRequest;

public class CanSendImageWsRequest extends CanSendImageRequest implements CoolQWebSocketRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4006997286127788587L;
	private final String action = "can_send_image";

	public String getAction() {
		return this.action;
	}
}
