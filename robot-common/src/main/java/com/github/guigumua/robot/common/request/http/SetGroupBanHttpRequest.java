package com.github.guigumua.robot.common.request.http;

import com.github.guigumua.robot.common.request.CoolQHttpRequest;
import com.github.guigumua.robot.common.request.SetGroupBanRequest;

public class SetGroupBanHttpRequest extends SetGroupBanRequest implements CoolQHttpRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1128801391019331657L;
	private final String uri = "/set_group_ban";

	@Override
	public String uri() {
		return this.uri;
	}
}
