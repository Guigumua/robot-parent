package com.github.guigumua.robot.common.request.ws;

import com.github.guigumua.robot.common.request.CoolQWebSocketRequest;
import com.github.guigumua.robot.common.request.SetGroupBanRequest;

public class SetGroupBanWsRequest extends SetGroupBanRequest implements CoolQWebSocketRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1128801391019331657L;
	private final String action = "set_group_ban";

	public String getAction() {
		return this.action;
	}
}
