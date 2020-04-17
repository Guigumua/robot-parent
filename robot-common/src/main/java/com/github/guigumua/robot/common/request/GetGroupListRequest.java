package com.github.guigumua.robot.common.request;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public abstract class GetGroupListRequest implements CoolQRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3396192460417484855L;

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
		private static final long serialVersionUID = -5697350226670287760L;
		private long groupId;
		private String groupName;

		public long getGroupId() {
			return groupId;
		}

		public void setGroupId(long groupId) {
			this.groupId = groupId;
		}

		public String getGroupName() {
			return groupName;
		}

		public void setGroupName(String groupName) {
			this.groupName = groupName;
		}
	}

	@Override
	public java.lang.Class<? extends CoolQHttpRequest.Response<?>> getResponseClass() {
		return GetGroupListRequest.Response.class;

	}

	public static class Response extends CoolQHttpRequest.Response<List<ResponseData>> {

		/**
		 * 
		 */
		private static final long serialVersionUID = -8546862370761080328L;

	}
}
