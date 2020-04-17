package com.github.guigumua.robot.common.request;

import com.alibaba.fastjson.annotation.JSONField;

public abstract class CleanDataDirRequest implements CoolQRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3555723645197560343L;
	private String dataDir;

	private volatile Response response;

	@JSONField(serialize = false)
	public Response getResponse() {
		return response;
	}

	public void setResponse(CoolQHttpRequest.Response<?> response) {
		this.response = (Response) response;
	}

	public String getDataDir() {
		return dataDir;
	}

	public void setDataDir(String dataDir) {
		this.dataDir = dataDir;
	}

	@Override
	public java.lang.Class<? extends CoolQHttpRequest.Response<?>> getResponseClass() {
		return CleanDataDirRequest.Response.class;

	}

	public static class Response extends CoolQHttpRequest.Response<ResponseData> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 3848794580917289811L;

	}

}
