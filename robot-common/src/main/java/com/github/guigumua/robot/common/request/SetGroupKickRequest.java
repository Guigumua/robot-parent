package com.github.guigumua.robot.common.request;

import com.alibaba.fastjson.annotation.JSONField;

public abstract class SetGroupKickRequest implements CoolQRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1741335524478741720L;

	private long groupId;
	private long userId;
	private boolean rejectAddRequest;
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

	public boolean isRejectAddRequest() {
		return rejectAddRequest;
	}

	public void setRejectAddRequest(boolean rejectAddRequest) {
		this.rejectAddRequest = rejectAddRequest;
	}

	public java.lang.Class<? extends CoolQHttpRequest.Response<?>> getResponseClass() {
		return SetGroupKickRequest.Response.class;

	}

	public static class Response extends CoolQHttpRequest.Response<ResponseData> {

		/**
		 * 
		 */
		private static final long serialVersionUID = -4091000785068499057L;

	}

}
