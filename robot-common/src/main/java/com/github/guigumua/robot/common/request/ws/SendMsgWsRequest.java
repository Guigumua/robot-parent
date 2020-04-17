package com.github.guigumua.robot.common.request.ws;

import com.github.guigumua.robot.common.request.CoolQWebSocketRequest;
import com.github.guigumua.robot.common.request.SendMsgRequest;

public class SendMsgWsRequest extends SendMsgRequest implements CoolQWebSocketRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4826500962677858565L;
	private final String SEND_PRIVATE_MSG = "send_private_msg";
	private final String SEND_GROUP_MSG = "send_group_msg";
	private final String SEND_DISCUSS_MSG = "send_discuss_msg";
	private final String SEND_MSG = "send_msg";

	public String getAction() {
		String messageType = getMessageType();
		if (PRIVATE_TYPE.equals(messageType)) {
			return SEND_PRIVATE_MSG;
		} else if (GROUP_TYPE.equals(messageType)) {
			return SEND_GROUP_MSG;
		} else if (DISCUSS_TYPE.equals(messageType)) {
			return SEND_DISCUSS_MSG;
		} else {
			return SEND_MSG;
		}
	}

}
