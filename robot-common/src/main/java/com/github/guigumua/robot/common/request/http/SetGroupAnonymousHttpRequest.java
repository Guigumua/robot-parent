package com.github.guigumua.robot.common.request.http;

import com.github.guigumua.robot.common.request.CoolQHttpRequest;
import com.github.guigumua.robot.common.request.SetGroupAnonymousRequest;

public class SetGroupAnonymousHttpRequest extends SetGroupAnonymousRequest implements CoolQHttpRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4472305033622545541L;
	private final String uri = "/set_group_anonymous";

	@Override
	public String uri() {
		return this.uri;
	}
}
