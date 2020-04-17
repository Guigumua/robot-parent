package com.github.guigumua.robot.common.request.ws;

import com.github.guigumua.robot.common.request.CoolQWebSocketRequest;
import com.github.guigumua.robot.common.request.SetGroupSpecialTitleRequest;

public class SetGroupSpecialTitleWsRequest extends SetGroupSpecialTitleRequest implements CoolQWebSocketRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -21584713753623827L;
	private final String action = "set_group_special_title";

	public String getAction() {
		return this.action;
	}
}
