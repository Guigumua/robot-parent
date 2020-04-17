package com.github.guigumua.robot.common.request.ws;

import com.github.guigumua.robot.common.request.CoolQWebSocketRequest;
import com.github.guigumua.robot.common.request.GetGroupMemberListRequest;

public class GetGroupMemberListWsRequest extends GetGroupMemberListRequest implements CoolQWebSocketRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1363754286645015435L;
	private final String action = "get_group_member_info";

	public String getAction() {
		return this.action;
	}

}
