package com.github.guigumua.robot.common.request;

import com.alibaba.fastjson.annotation.JSONField;

public abstract class CanSendImageRequest implements CoolQRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3704160743916817219L;

	public void setResponse(CoolQHttpRequest.Response<?> response) {
		this.response = (Response) response;
	}

	private volatile Response response;

	@JSONField(serialize = false)
	public Response getResponse() {
		return response;
	}

	public static class ResponseData implements CoolQHttpRequest.ResponseData {
		/**
		 * 
		 */
		private static final long serialVersionUID = -1212054266818615022L;
		private boolean yes;

		public boolean isYes() {
			return yes;
		}

		public void setYes(boolean yes) {
			this.yes = yes;
		}

	}

	@Override
	public java.lang.Class<? extends CoolQHttpRequest.Response<?>> getResponseClass() {
		return CanSendImageRequest.Response.class;

	}

	public static class Response extends CoolQHttpRequest.Response<ResponseData> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 5258894400364372462L;

	}
}
