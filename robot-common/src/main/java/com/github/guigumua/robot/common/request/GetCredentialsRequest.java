package com.github.guigumua.robot.common.request;

import com.alibaba.fastjson.annotation.JSONField;

public abstract class GetCredentialsRequest implements CoolQRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5357964751260892391L;

	private volatile Response response;

	@JSONField(serialize = false)
	public Response getResponse() {
		return response;
	}

	public void setResponse(CoolQHttpRequest.Response<?> response) {
		this.response = (Response) response;
	}

	public static class ResponseData implements CoolQHttpRequest.ResponseData {
		/**
		 * 
		 */
		private static final long serialVersionUID = 4485915664994824013L;
		private String cookies;
		private int crsfToken;

		public String getCookies() {
			return cookies;
		}

		public void setCookies(String cookies) {
			this.cookies = cookies;
		}

		public int getCrsfToken() {
			return crsfToken;
		}

		public void setCrsfToken(int crsfToken) {
			this.crsfToken = crsfToken;
		}

	}

	@Override
	public java.lang.Class<? extends CoolQHttpRequest.Response<?>> getResponseClass() {
		return GetCredentialsRequest.Response.class;

	}

	public static class Response extends CoolQHttpRequest.Response<ResponseData> {

		/**
		 * 
		 */
		private static final long serialVersionUID = -3239232094651282195L;

	}

}
