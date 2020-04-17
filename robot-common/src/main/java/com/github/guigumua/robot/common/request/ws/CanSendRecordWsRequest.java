package com.github.guigumua.robot.common.request.ws;

import com.github.guigumua.robot.common.request.CanSendRecordRequest;
import com.github.guigumua.robot.common.request.CoolQWebSocketRequest;

public class CanSendRecordWsRequest extends CanSendRecordRequest implements CoolQWebSocketRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6459661813962372588L;
	private final String action = "can_send_record";

	public String getAction() {
		return this.action;
	}
}
