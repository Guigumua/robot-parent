package com.github.guigumua.robot.commom.request.bean;

import java.io.Serializable;

import com.github.guigumua.robot.commom.request.CoolQHttpRequest;

public class SetGroupBanRequest extends CoolQHttpRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5037059041870356114L;
	private long groupId;
	private long userId;
	private int duration;
	private final String uri = "/set_group_ban";
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

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public java.lang.Class<? extends CoolQHttpRequest.Response> getResponseClass() {
		return SetGroupBanRequest.Response.class;

	}

	public static class Response extends CoolQHttpRequest.Response<ResponseData> {

		/**
		 * 
		 */
		private static final long serialVersionUID = -3616540684637142421L;

	}
}
