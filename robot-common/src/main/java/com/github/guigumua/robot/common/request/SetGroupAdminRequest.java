package com.github.guigumua.robot.common.request;

import com.alibaba.fastjson.annotation.JSONField;

public abstract class SetGroupAdminRequest implements CoolQRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3406576102984572397L;
	private long groupId;
	private long userId;
	private boolean enable;

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

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	@Override
	public java.lang.Class<? extends CoolQHttpRequest.Response<?>> getResponseClass() {
		return SetGroupAdminRequest.Response.class;

	}

	public static class Response extends CoolQHttpRequest.Response<ResponseData> {

		/**
		 * 
		 */
		private static final long serialVersionUID = -8835212672603683342L;

	}
}
