package com.github.guigumua.robot.common.request;

import com.alibaba.fastjson.annotation.JSONField;

public abstract class SetDiscussLeaveRequest implements CoolQRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4528286573648703266L;
	private long discussId;

	private volatile Response response;

	@JSONField(serialize = false)
	public Response getResponse() {
		return response;
	}

	public void setResponse(CoolQHttpRequest.Response<?> response) {
		this.response = (Response) response;
	}

	public long getDiscussId() {
		return discussId;
	}

	public void setDiscussId(long discussId) {
		this.discussId = discussId;
	}

	@Override
	public java.lang.Class<? extends CoolQHttpRequest.Response<?>> getResponseClass() {
		return SetDiscussLeaveRequest.Response.class;

	}

	public static class Response extends CoolQHttpRequest.Response<ResponseData> {

		/**
		 * 
		 */
		private static final long serialVersionUID = -8640554724989143642L;

	}

}
