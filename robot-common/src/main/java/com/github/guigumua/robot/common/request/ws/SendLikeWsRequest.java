package com.github.guigumua.robot.common.request.ws;

import com.github.guigumua.robot.common.request.CoolQWebSocketRequest;
import com.github.guigumua.robot.common.request.SendLikeRequest;

public class SendLikeWsRequest extends SendLikeRequest implements CoolQWebSocketRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5425278632884396119L;
	private final String action = "send_like";

	public String getAction() {
		return this.action;
	}
}
