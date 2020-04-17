package com.github.guigumua.robot.common.request.ws;

import com.github.guigumua.robot.common.request.CoolQWebSocketRequest;
import com.github.guigumua.robot.common.request.SetGroupWholeBanRequest;

public class SetGroupWholeBanWsRequest extends SetGroupWholeBanRequest implements CoolQWebSocketRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4499500252512307108L;
	private final String action = "set_group_whole_ban";

	public String getAction() {
		return this.action;
	}
}
