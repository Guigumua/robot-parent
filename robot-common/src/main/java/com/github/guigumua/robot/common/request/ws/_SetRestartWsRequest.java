package com.github.guigumua.robot.common.request.ws;

import com.github.guigumua.robot.common.request.CoolQWebSocketRequest;
import com.github.guigumua.robot.common.request.SetRestartRequest;

public class _SetRestartWsRequest extends SetRestartRequest implements CoolQWebSocketRequest {

	private static final long serialVersionUID = 4678093931669923790L;
	private final String action = "_set_restart";

	@Override
	public String getAction() {
		return this.action;
	}

}
