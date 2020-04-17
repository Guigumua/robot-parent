package com.github.guigumua.robot.common.request.http;

import com.github.guigumua.robot.common.request.CoolQHttpRequest;
import com.github.guigumua.robot.common.request.DeleteMsgRequest;

public class DeleteMsgHttpRequest extends DeleteMsgRequest implements CoolQHttpRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4305418548007919956L;
	private final String uri = "/delete_msg";

	@Override
	public String uri() {
		return this.uri;
	}
}
