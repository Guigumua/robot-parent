package com.github.guigumua.robot.common.request.http;

import com.github.guigumua.robot.common.request.CoolQHttpRequest;
import com.github.guigumua.robot.common.request._GetGroupNoticeRequest;

public class _GetGroupNoticeHttpRequest extends _GetGroupNoticeRequest implements CoolQHttpRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3088713720156693563L;
	private final String uri = "/_get_group_notice";

	@Override
	public String uri() {
		return this.uri;
	}
}
