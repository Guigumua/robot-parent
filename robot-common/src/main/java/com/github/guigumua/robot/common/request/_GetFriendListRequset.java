package com.github.guigumua.robot.common.request;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public abstract class _GetFriendListRequset implements CoolQRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = 674978016213680923L;
	private boolean flat;

	private volatile Response response;
	private volatile FlatResponse flatResponse;

	@JSONField(serialize = false)
	public CoolQHttpRequest.Response<?> getResponse() {
		return flat ? flatResponse : response;
	}

	public void setResponse(CoolQHttpRequest.Response<?> response) {
		if (flat) {
			this.flatResponse = (FlatResponse) response;
		} else {
			this.response = (Response) response;
		}
	}

	public boolean isFlat() {
		return flat;
	}

	public void setFlat(boolean flat) {
		this.flat = flat;
	}

	public static class FriendGroup implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = -5637095149762022720L;
		private int friendGroupId;
		private String friendGroupName;
		private List<Friend> friends;

		public int getFriendGroupId() {
			return friendGroupId;
		}

		public void setFriendGroupId(int friendGroupId) {
			this.friendGroupId = friendGroupId;
		}

		public String getFriendGroupName() {
			return friendGroupName;
		}

		public void setFriendGroupName(String friendGroupName) {
			this.friendGroupName = friendGroupName;
		}

		public List<Friend> getFriends() {
			return friends;
		}

		public void setFriends(List<Friend> friends) {
			this.friends = friends;
		}
	}

	public static class Friend implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = -4595551965557568647L;
		private String nickname;
		private String remark;
		private long userId;
		private int friendGroupId;

		public String getNickname() {
			return nickname;
		}

		public void setNickname(String nickname) {
			this.nickname = nickname;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		public long getUserId() {
			return userId;
		}

		public void setUserId(long userId) {
			this.userId = userId;
		}

		public int getFriendGroupId() {
			return friendGroupId;
		}

		public void setFriendGroupId(int friendGroupId) {
			this.friendGroupId = friendGroupId;
		}
	}

	public static class ResponseData /* extends JSONArray */ implements CoolQRequest.ResponseData {

		/**
		 * 
		 */
		private static final long serialVersionUID = -3233541950366227588L;

	}

	public static class FlatResponseData implements CoolQRequest.ResponseData {
		/**
		 * 
		 */
		private static final long serialVersionUID = 4923060503545731954L;
		private List<FriendGroup> friendGroups;
		private List<Friend> friends;

		public List<FriendGroup> getFriendGroups() {
			return friendGroups;
		}

		public void setFriendGroups(List<FriendGroup> friendGroups) {
			this.friendGroups = friendGroups;
		}

		public List<Friend> getFriends() {
			return friends;
		}

		public void setFriends(List<Friend> friends) {
			this.friends = friends;
		}
	}

	@Override
	public Class<? extends CoolQRequest.Response<?>> getResponseClass() {
		return flat ? _GetFriendListRequset.FlatResponse.class : _GetFriendListRequset.Response.class;

	}

	public static class Response extends CoolQRequest.Response<List<ResponseData>> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 6406305746441070005L;

	}

	public static class FlatResponse extends CoolQRequest.Response<FlatResponseData> {

		/**
		 * 
		 */
		private static final long serialVersionUID = -8866396514524771728L;

	}
}
