package com.github.guigumua.robot.common.request.http;

import com.github.guigumua.robot.common.request.CoolQHttpRequest;
import com.github.guigumua.robot.common.request.GetStatusRequest;

public class GetStatusHttpRequest extends GetStatusRequest implements CoolQHttpRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8244846239177347605L;
	private final String uri = "/get_status";

	@Override
	public String uri() {
		return this.uri;
	}
}
