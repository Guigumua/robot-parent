package com.github.guigumua.robot.common.request.http;

import com.github.guigumua.robot.common.request.CoolQHttpRequest;
import com.github.guigumua.robot.common.request.GetImageRequest;

public class GetImageHttpRequest extends GetImageRequest implements CoolQHttpRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8110122256271661345L;
	private final String uri = "/get_image";

	@Override
	public String uri() {
		return this.uri;
	}
}
