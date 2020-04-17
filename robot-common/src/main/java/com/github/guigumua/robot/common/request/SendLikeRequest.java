package com.github.guigumua.robot.common.request;

import com.alibaba.fastjson.annotation.JSONField;

public abstract class SendLikeRequest implements CoolQRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5517880910213805076L;
	private volatile Response response;

	@JSONField(serialize = false)
	public Response getResponse() {
		return response;
	}

	public void setResponse(CoolQHttpRequest.Response<?> response) {
		this.response = (Response) response;
	}

	private long userId;
	private int times;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	@Override
	public java.lang.Class<? extends CoolQHttpRequest.Response<?>> getResponseClass() {
		return SendLikeRequest.Response.class;

	}

	public static class Response extends CoolQHttpRequest.Response<ResponseData> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 577391156998423159L;

	}

}
