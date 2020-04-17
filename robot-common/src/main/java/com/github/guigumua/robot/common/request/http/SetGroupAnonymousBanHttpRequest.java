package com.github.guigumua.robot.common.request.http;

import com.github.guigumua.robot.common.request.CoolQHttpRequest;
import com.github.guigumua.robot.common.request.SetGroupAnonymousBanRequest;

public class SetGroupAnonymousBanHttpRequest extends SetGroupAnonymousBanRequest implements CoolQHttpRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1268618030281251821L;
	private final String uri = "/set_group_anonymous_ban";

	@Override
	public String uri() {
		return this.uri;
	}
}
