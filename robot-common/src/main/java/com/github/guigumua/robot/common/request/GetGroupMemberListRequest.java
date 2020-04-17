package com.github.guigumua.robot.common.request;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public abstract class GetGroupMemberListRequest implements CoolQRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6397081950732232246L;
	private long groupId;

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

	@Override
	public java.lang.Class<? extends CoolQHttpRequest.Response<?>> getResponseClass() {
		return GetGroupMemberListRequest.Response.class;

	}

	public static class Response extends CoolQHttpRequest.Response<List<GetGroupMemberInfoRequest.ResponseData>> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1347067371826364874L;

	}
}
