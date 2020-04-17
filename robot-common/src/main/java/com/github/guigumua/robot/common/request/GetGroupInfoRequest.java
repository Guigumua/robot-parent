package com.github.guigumua.robot.common.request;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public abstract class GetGroupInfoRequest implements CoolQRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4463312485258228248L;
	private long groupId;
	private String naCache;

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

	public String getNaCache() {
		return naCache;
	}

	public void setNaCache(String naCache) {
		this.naCache = naCache;
	}

	public static class ResponseData implements Serializable, CoolQHttpRequest.ResponseData {
		/**
		 * 
		 */
		private static final long serialVersionUID = 3626158099413133343L;
		private long groupId;
		private String groupName;
		private int memberCount;
		private int maxMemberCount;

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

		public int getMemberCount() {
			return memberCount;
		}

		public void setMemberCount(int memberCount) {
			this.memberCount = memberCount;
		}

		public int getMaxMemberCount() {
			return maxMemberCount;
		}

		public void setMaxMemberCount(int maxMemberCount) {
			this.maxMemberCount = maxMemberCount;
		}

	}

	@Override
	public java.lang.Class<? extends CoolQHttpRequest.Response<?>> getResponseClass() {
		return GetGroupInfoRequest.Response.class;

	}

	public static class Response extends CoolQHttpRequest.Response<ResponseData> {

		/**
		 * 
		 */
		private static final long serialVersionUID = -4152629877271239353L;

	}
}
