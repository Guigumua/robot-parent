package com.github.guigumua.robot.common.request;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public abstract class GetCrsfTokenRequest implements CoolQRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2175152016555190573L;

	private volatile Response response;

	@JSONField(serialize = false)
	public Response getResponse() {
		return response;
	}

	public void setResponse(CoolQHttpRequest.Response<?> response) {
		this.response = (Response) response;
	}

	public static class ResponseData implements Serializable, CoolQHttpRequest.ResponseData {
		/**
		 * 
		 */
		private static final long serialVersionUID = -4285799380525281586L;
		private int token;

		public int getToken() {
			return token;
		}

		public void setToken(int token) {
			this.token = token;
		}

	}

	@Override
	public java.lang.Class<? extends CoolQHttpRequest.Response<?>> getResponseClass() {
		return GetCrsfTokenRequest.Response.class;

	}

	public static class Response extends CoolQHttpRequest.Response<ResponseData> {

		/**
		 * 
		 */
		private static final long serialVersionUID = -4217695247008136325L;

	}

}
