package com.github.guigumua.robot.common.request;

import com.alibaba.fastjson.annotation.JSONField;

public abstract class GetCookiesRequest implements CoolQRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = 461388587828928512L;
	private String domain;

	private volatile Response response;

	@JSONField(serialize = false)
	public Response getResponse() {
		return response;
	}

	public void setResponse(CoolQHttpRequest.Response<?> response) {
		this.response = (Response) response;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public static class ResponseData implements CoolQHttpRequest.ResponseData {
		/**
		 * 
		 */
		private static final long serialVersionUID = 255999726960497870L;
		private String cookies;

		public String getCookies() {
			return cookies;
		}

		public void setCookies(String cookies) {
			this.cookies = cookies;
		}
	}

	@Override
	public java.lang.Class<? extends CoolQHttpRequest.Response<?>> getResponseClass() {
		return GetCookiesRequest.Response.class;

	}

	public static class Response extends CoolQHttpRequest.Response<ResponseData> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 7949421509947850807L;

	}
}
