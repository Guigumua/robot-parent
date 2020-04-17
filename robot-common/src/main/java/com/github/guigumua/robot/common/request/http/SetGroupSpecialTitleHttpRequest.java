package com.github.guigumua.robot.common.request.http;

import com.github.guigumua.robot.common.request.CoolQHttpRequest;
import com.github.guigumua.robot.common.request.SetGroupSpecialTitleRequest;

public class SetGroupSpecialTitleHttpRequest extends SetGroupSpecialTitleRequest implements CoolQHttpRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -21584713753623827L;
	private final String uri = "/set_group_special_title";

	@Override
	public String uri() {
		return this.uri;
	}
}
