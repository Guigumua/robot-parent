package com.github.guigumua.robot.common.request.ws;

import com.github.guigumua.robot.common.request.CoolQWebSocketRequest;
import com.github.guigumua.robot.common.request._GetGroupInfoRequest;

public class _GetGroupInfoWsRequest extends _GetGroupInfoRequest implements CoolQWebSocketRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6810189144251082338L;
	private final String action = "_get_group_info";

	@Override
	public String getAction() {
		return this.action;
	}

}
