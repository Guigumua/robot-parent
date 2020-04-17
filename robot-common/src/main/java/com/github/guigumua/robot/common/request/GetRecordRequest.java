package com.github.guigumua.robot.common.request;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public abstract class GetRecordRequest implements CoolQRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4667945661751484387L;
	private String file;
	private String outFormat;
	private boolean fullPath;

	private volatile Response response;

	@JSONField(serialize = false)
	public Response getResponse() {
		return response;
	}

	public void setResponse(CoolQHttpRequest.Response<?> response) {
		this.response = (Response) response;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getOutFormat() {
		return outFormat;
	}

	public void setOutFormat(String outFormat) {
		this.outFormat = outFormat;
	}

	public boolean isFullPath() {
		return fullPath;
	}

	public void setFullPath(boolean fullPath) {
		this.fullPath = fullPath;
	}

	public static class ResponseData implements Serializable, CoolQHttpRequest.ResponseData {
		/**
		 * 
		 */
		private static final long serialVersionUID = 7645026268140767509L;
		private String file;

		public String getFile() {
			return file;
		}

		public void setFile(String file) {
			this.file = file;
		}

	}

	@Override
	public java.lang.Class<? extends CoolQHttpRequest.Response<?>> getResponseClass() {
		return GetRecordRequest.Response.class;

	}

	public static class Response extends CoolQHttpRequest.Response<ResponseData> {

		/**
		 * 
		 */
		private static final long serialVersionUID = -8920450901362965353L;

	}

}
