package com.github.guigumua.robot.common.request.ws;

import com.github.guigumua.robot.common.request.CoolQWebSocketRequest;
import com.github.guigumua.robot.common.request._SendGroupNoticeRequest;

public class _SendGroupNoticeWsRequest extends _SendGroupNoticeRequest implements CoolQWebSocketRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7083650596585880085L;

	private final String action = "_send_group_notice";

	public String getAction() {
		return this.action;
	}

}
