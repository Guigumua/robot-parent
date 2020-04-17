package com.github.guigumua.robot.common.request.ws;

import com.github.guigumua.robot.common.request.CoolQWebSocketRequest;
import com.github.guigumua.robot.common.request.GetGroupMemberInfoRequest;

public class GetGroupMemberInfoWsRequest extends GetGroupMemberInfoRequest implements CoolQWebSocketRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6834714629731296330L;
	private final String action = "get_group_member_info";

	public String getAction() {
		return this.action;
	}

}
