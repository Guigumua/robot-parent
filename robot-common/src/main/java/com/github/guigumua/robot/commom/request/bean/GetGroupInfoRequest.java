package com.github.guigumua.robot.commom.request.bean;

import java.io.Serializable;

import com.github.guigumua.robot.commom.request.CoolQHttpRequest;

public class GetGroupInfoRequest extends CoolQHttpRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4463312485258228248L;
	private long groupId;
	private String naCache;
	private final String uri = "/get_group_info";
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

	@SuppressWarnings("rawtypes")
	@Override
	public java.lang.Class<? extends CoolQHttpRequest.Response> getResponseClass() {
		return GetGroupInfoRequest.Response.class;

	}

	public static class Response extends CoolQHttpRequest.Response<ResponseData> {

		/**
		 * 
		 */
		private static final long serialVersionUID = -4152629877271239353L;

	}
}
