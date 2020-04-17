package com.github.guigumua.robot.common.request;

import com.alibaba.fastjson.annotation.JSONField;

public abstract class SetRestartRequest implements CoolQRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = -596029590451992140L;
	private boolean cleanLog;
	private boolean cleanCache;
	private boolean cleanEvent;

	private volatile Response response;

	@JSONField(serialize = false)
	public Response getResponse() {
		return response;
	}

	public void setResponse(CoolQHttpRequest.Response<?> response) {
		this.response = (Response) response;
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

	@Override
	public java.lang.Class<? extends CoolQHttpRequest.Response<?>> getResponseClass() {
		return SetRestartRequest.Response.class;

	}

	public static class Response extends CoolQHttpRequest.Response<ResponseData> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 5021700233378001058L;

	}
}
