package com.github.guigumua.robot.commom.request.bean;

import java.io.Serializable;

import com.github.guigumua.robot.commom.request.CoolQHttpRequest;

public class SetGroupAnonymousRequest extends CoolQHttpRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5934355893872235593L;
	private long groupId;
	private boolean enable;
	private final String uri = "/set_group_anonymous";
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

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public java.lang.Class<? extends CoolQHttpRequest.Response> getResponseClass() {
		return SetGroupAnonymousRequest.Response.class;

	}

	public static class Response extends CoolQHttpRequest.Response<ResponseData> {

		/**
		 * 
		 */
		private static final long serialVersionUID = -8219036289827811405L;

	}
}
