package com.github.guigumua.robot.common.request;

import com.alibaba.fastjson.annotation.JSONField;

public abstract class SetGroupLeaveRequest implements CoolQRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3962925664811490203L;
	private long groupId;
	private boolean isDismiss;

	private volatile Response response;

	@JSONField(serialize = false)
	public Response getResponse() {
		return response;
	}

	public void setResponse(CoolQHttpRequest.Response<?> response) {
		this.response = (Response) response;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public boolean isDismiss() {
		return isDismiss;
	}

	public void setDismiss(boolean isDismiss) {
		this.isDismiss = isDismiss;
	}

	@Override
	public java.lang.Class<? extends CoolQHttpRequest.Response<?>> getResponseClass() {
		return SetGroupLeaveRequest.Response.class;

	}

	public static class Response extends CoolQHttpRequest.Response<ResponseData> {

		/**
		 * 
		 */
		private static final long serialVersionUID = -529737369286416068L;

	}

}
