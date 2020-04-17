package com.github.guigumua.robot.common.request.http;

import com.github.guigumua.robot.common.request.CoolQHttpRequest;
import com.github.guigumua.robot.common.request.SetGroupLeaveRequest;

public class SetGroupLeaveHttpRequest extends SetGroupLeaveRequest implements CoolQHttpRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5891358393362785106L;
	private final String uri = "/set_group_leave";

	@Override
	public String uri() {
		return this.uri;
	}
}
