package com.github.guigumua.robot.common.request.ws;

import com.github.guigumua.robot.common.request.CoolQWebSocketRequest;
import com.github.guigumua.robot.common.request._GetGroupNoticeRequest;

public class _GetGroupNoticeWsRequest extends _GetGroupNoticeRequest implements CoolQWebSocketRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3088713720156693563L;
	private final String action = "_get_group_notice";

	@Override
	public String getAction() {
		return this.action;
	}

}
