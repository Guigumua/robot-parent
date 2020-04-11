package com.github.guigumua.robot.commom.request.bean;

import java.io.Serializable;

import com.github.guigumua.robot.commom.request.CoolQHttpRequest;

public class SetGroupLeaveRequest extends CoolQHttpRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3962925664811490203L;
	private long groupId;
	private boolean isDismiss;
	private final String uri = "/set_group_leave";
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

	@SuppressWarnings("rawtypes")
	@Override
	public java.lang.Class<? extends CoolQHttpRequest.Response> getResponseClass() {
		return SetGroupLeaveRequest.Response.class;

	}

	public static class Response extends CoolQHttpRequest.Response<ResponseData> {

		/**
		 * 
		 */
		private static final long serialVersionUID = -529737369286416068L;

	}

}
