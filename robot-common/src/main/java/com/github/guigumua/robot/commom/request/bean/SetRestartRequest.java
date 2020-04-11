package com.github.guigumua.robot.commom.request.bean;

import java.io.Serializable;

import com.github.guigumua.robot.commom.request.CoolQHttpRequest;

public class SetRestartRequest extends CoolQHttpRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -596029590451992140L;
	private boolean cleanLog;
	private boolean cleanCache;
	private boolean cleanEvent;
	private final String uri = "/_set_restart";
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

	public boolean isCleanLog() {
		return cleanLog;
	}

	public void setCleanLog(boolean cleanLog) {
		this.cleanLog = cleanLog;
	}

	public boolean isCleanCache() {
		return cleanCache;
	}

	public void setCleanCache(boolean cleanCache) {
		this.cleanCache = cleanCache;
	}

	public boolean isCleanEvent() {
		return cleanEvent;
	}

	public void setCleanEvent(boolean cleanEvent) {
		this.cleanEvent = cleanEvent;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public java.lang.Class<? extends CoolQHttpRequest.Response> getResponseClass() {
		return SetRestartRequest.Response.class;

	}

	public static class Response extends CoolQHttpRequest.Response<ResponseData> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 5021700233378001058L;

	}
}
