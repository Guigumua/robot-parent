package com.github.guigumua.robot.common.request.http;

import com.github.guigumua.robot.common.request.CoolQHttpRequest;
import com.github.guigumua.robot.common.request.SetDiscussLeaveRequest;

public class SetDiscussLeaveHttpRequest extends SetDiscussLeaveRequest implements CoolQHttpRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7909677304753152410L;
	private final String uri = "/set_discuss_leave";

	@Override
	public String uri() {
		return this.uri;
	}
}
