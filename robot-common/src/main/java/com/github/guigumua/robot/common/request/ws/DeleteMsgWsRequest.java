package com.github.guigumua.robot.common.request.ws;

import com.github.guigumua.robot.common.request.CoolQWebSocketRequest;
import com.github.guigumua.robot.common.request.DeleteMsgRequest;

public class DeleteMsgWsRequest extends DeleteMsgRequest implements CoolQWebSocketRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4305418548007919956L;
	private final String action = "delete_msg";

	public String getAction() {
		return this.action;
	}
}
