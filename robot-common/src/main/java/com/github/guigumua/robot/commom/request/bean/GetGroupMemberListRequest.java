package com.github.guigumua.robot.commom.request.bean;

import java.io.Serializable;
import java.util.List;

import com.github.guigumua.robot.commom.request.CoolQHttpRequest;

public class GetGroupMemberListRequest extends CoolQHttpRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6397081950732232246L;
	private long groupId;
	private final String uri = "/get_group_member_info";
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

	@SuppressWarnings("rawtypes")
	@Override
	public java.lang.Class<? extends CoolQHttpRequest.Response> getResponseClass() {
		return GetGroupMemberListRequest.Response.class;

	}

	public static class Response extends CoolQHttpRequest.Response<List<GetGroupMemberInfoRequest.ResponseData>> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1347067371826364874L;

	}
}
