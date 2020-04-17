package com.github.guigumua.robot.common.request;

import com.alibaba.fastjson.annotation.JSONField;

public abstract class CanSendRecordRequest implements CoolQRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3019182669984815689L;

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
		private static final long serialVersionUID = -2041554626382483500L;
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
		return CanSendRecordRequest.Response.class;

	}

	public static class Response extends CoolQHttpRequest.Response<ResponseData> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 6055170418355542932L;

	}
}
