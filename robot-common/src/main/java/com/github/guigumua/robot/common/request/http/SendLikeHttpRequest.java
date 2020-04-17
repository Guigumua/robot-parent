package com.github.guigumua.robot.common.request.http;

import com.github.guigumua.robot.common.request.CoolQHttpRequest;
import com.github.guigumua.robot.common.request.SendLikeRequest;

public class SendLikeHttpRequest extends SendLikeRequest implements CoolQHttpRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5425278632884396119L;
	private final String uri = "/send_like";

	@Override
	public String uri() {
		return this.uri;
	}
}
