package com.github.guigumua.robot.common.request.http;

import com.github.guigumua.robot.common.request.CoolQHttpRequest;
import com.github.guigumua.robot.common.request._SendGroupNoticeRequest;

public class _SendGroupNoticeHttpRequest extends _SendGroupNoticeRequest implements CoolQHttpRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7083650596585880085L;

	private final String uri = "/_send_group_notice";

	@Override
	public String uri() {
		return this.uri;
	}

}
