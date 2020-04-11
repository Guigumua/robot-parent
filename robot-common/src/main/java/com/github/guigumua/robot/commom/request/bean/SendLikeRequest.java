package com.github.guigumua.robot.commom.request.bean;

import java.io.Serializable;

import com.github.guigumua.robot.commom.request.CoolQHttpRequest;

public class SendLikeRequest extends CoolQHttpRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5517880910213805076L;
	private volatile Response response;

	public Response getResponse() {
		return response;
	}

	public void setResponse(CoolQHttpRequest.Response<?> response) {
		this.response = (Response) response;
	}

	private long userId;
	private int times;

	private final String uri = "/send_like";

	@Override
	public String uri() {
		return this.uri;
	}

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

	@SuppressWarnings("rawtypes")
	@Override
	public java.lang.Class<? extends CoolQHttpRequest.Response> getResponseClass() {
		return SendLikeRequest.Response.class;

	}

	public static class Response extends CoolQHttpRequest.Response<ResponseData> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 577391156998423159L;

	}

}
