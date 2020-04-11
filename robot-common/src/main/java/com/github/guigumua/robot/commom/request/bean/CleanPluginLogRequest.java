package com.github.guigumua.robot.commom.request.bean;

import java.io.Serializable;

import com.github.guigumua.robot.commom.request.CoolQHttpRequest;

public class CleanPluginLogRequest extends CoolQHttpRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2569646971975453909L;
	private final String uri = "/clean_plugin_log";
	private volatile Response response;

	public Response getResponse() {
		return response;
	}

	public void setResponse(CoolQHttpRequest.Response<?> response) {
		this.response = (Response) response;
	}

	@Override
	public String uri() {
		return this.uri;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public java.lang.Class<? extends CoolQHttpRequest.Response> getResponseClass() {
		return CleanPluginLogRequest.Response.class;

	}

	public static class Response extends CoolQHttpRequest.Response<ResponseData> {

		/**
		 * 
		 */
		private static final long serialVersionUID = -8127641213002027786L;

	}
}
