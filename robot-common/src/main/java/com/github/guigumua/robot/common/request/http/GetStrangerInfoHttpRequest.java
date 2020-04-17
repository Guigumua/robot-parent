package com.github.guigumua.robot.common.request.http;

import com.github.guigumua.robot.common.request.CoolQHttpRequest;
import com.github.guigumua.robot.common.request.GetStrangerInfoRequest;

public class GetStrangerInfoHttpRequest extends GetStrangerInfoRequest implements CoolQHttpRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5561324526569462104L;
	private final String uri = "/get_stranger_info";

	@Override
	public String uri() {
		return this.uri;
	}

}
