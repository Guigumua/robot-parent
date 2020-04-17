package com.github.guigumua.robot.common.request;

import com.alibaba.fastjson.annotation.JSONField;

public abstract class GetImageRequest implements CoolQRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1346716860879630652L;
	private String file;

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

	public static class ResponseData implements CoolQHttpRequest.ResponseData {
		/**
		 * 
		 */
		private static final long serialVersionUID = -6702585775761645317L;

		public String getFile() {
			return file;
		}

		public void setFile(String file) {
			this.file = file;
		}

		private String file;
	}

	@Override
	public java.lang.Class<? extends CoolQHttpRequest.Response<?>> getResponseClass() {
		return GetImageRequest.Response.class;

	}

	public static class Response extends CoolQHttpRequest.Response<ResponseData> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 5169707910438769063L;

	}
}
