package com.github.guigumua.robot.common.request.ws;

import com.github.guigumua.robot.common.request.CoolQWebSocketRequest;
import com.github.guigumua.robot.common.request.SetGroupKickRequest;

public class SetGroupKickWsRequest extends SetGroupKickRequest implements CoolQWebSocketRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9026323192489325033L;
	private final String action = "set_group_kick";

	public String getAction() {
		return this.action;
	}

}
