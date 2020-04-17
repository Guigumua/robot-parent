package com.github.guigumua.robot.common.request.http;

import com.github.guigumua.robot.common.request.CoolQHttpRequest;
import com.github.guigumua.robot.common.request.SetGroupKickRequest;

public class SetGroupKickHttpRequest extends SetGroupKickRequest implements CoolQHttpRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9026323192489325033L;
	private final String uri = "/set_group_kick";

	@Override
	public String uri() {
		return this.uri;
	}

}
