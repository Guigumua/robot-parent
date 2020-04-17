package com.github.guigumua.robot.common.request.http;

import com.github.guigumua.robot.common.request.CoolQHttpRequest;
import com.github.guigumua.robot.common.request.SetGroupWholeBanRequest;

public class SetGroupWholeBanHttpRequest extends SetGroupWholeBanRequest implements CoolQHttpRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4499500252512307108L;
	private final String uri = "/set_group_whole_ban";

	@Override
	public String uri() {
		return this.uri;
	}
}
