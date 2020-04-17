package com.github.guigumua.robot.common.request.http;

import com.github.guigumua.robot.common.request.CoolQHttpRequest;
import com.github.guigumua.robot.common.request._GetVipInfoRequest;

public class _GetVipInfoHttpRequest extends _GetVipInfoRequest implements CoolQHttpRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -861065484699329306L;

	private final String uri = "/_get_vip_info";

	@Override
	public String uri() {
		return this.uri;
	}
}
