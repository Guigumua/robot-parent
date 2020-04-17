package com.github.guigumua.robot.common.request.http;

import com.github.guigumua.robot.common.request.CoolQHttpRequest;
import com.github.guigumua.robot.common.request.GetRecordRequest;

public class GetRecordHttpRequest extends GetRecordRequest implements CoolQHttpRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -897817699411206469L;
	private final String uri = "/get_record";

	@Override
	public String uri() {
		return this.uri;
	}
}
