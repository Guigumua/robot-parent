package com.github.guigumua.robot.common.request.http;

import com.github.guigumua.robot.common.request.CleanDataDirRequest;
import com.github.guigumua.robot.common.request.CoolQHttpRequest;

public class CleanDataDirHttpRequest extends CleanDataDirRequest implements CoolQHttpRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7905231475974163594L;
	private final String uri = "/clean_data_dir";

	@Override
	public String uri() {
		return this.uri;
	}
}
