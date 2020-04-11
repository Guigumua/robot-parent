package com.github.guigumua.robot.commom.request.bean;

import java.io.Serializable;

import com.github.guigumua.robot.commom.request.CoolQHttpRequest;

public class GetGroupMemberInfoRequest extends CoolQHttpRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4986568922340219105L;
	private long groupId;
	private long userId;
	private boolean noCache;
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

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public boolean isNoCache() {
		return noCache;
	}

	public void setNoCache(boolean noCache) {
		this.noCache = noCache;
	}

	public static class ResponseData implements Serializable, CoolQHttpRequest.ResponseData {
		/**
		 * 
		 */
		private static final long serialVersionUID = -4115271414798664638L;
		private long groupId;
		private long userId;
		private String nickname;
		private String card;
		private String sex;
		private int age;
		private String area;
		private int joinTime;
		private int lastSentTime;
		private String level;
		private String role;
		private boolean unfriendly;
		private String title;
		private int titleExpireTime;
		private boolean cardChangeable;

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

		public String getNickname() {
			return nickname;
		}

		public void setNickname(String nickname) {
			this.nickname = nickname;
		}

		public String getCard() {
			return card;
		}

		public void setCard(String card) {
			this.card = card;
		}

		public String getSex() {
			return sex;
		}

		public void setSex(String sex) {
			this.sex = sex;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public String getArea() {
			return area;
		}

		public void setArea(String area) {
			this.area = area;
		}

		public int getJoinTime() {
			return joinTime;
		}

		public void setJoinTime(int joinTime) {
			this.joinTime = joinTime;
		}

		public int getLastSentTime() {
			return lastSentTime;
		}

		public void setLastSentTime(int lastSentTime) {
			this.lastSentTime = lastSentTime;
		}

		public String getLevel() {
			return level;
		}

		public void setLevel(String level) {
			this.level = level;
		}

		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}

		public boolean isUnfriendly() {
			return unfriendly;
		}

		public void setUnfriendly(boolean unfriendly) {
			this.unfriendly = unfriendly;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public int getTitleExpireTime() {
			return titleExpireTime;
		}

		public void setTitleExpireTime(int titleExpireTime) {
			this.titleExpireTime = titleExpireTime;
		}

		public boolean isCardChangeable() {
			return cardChangeable;
		}

		public void setCardChangeable(boolean cardChangeable) {
			this.cardChangeable = cardChangeable;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public java.lang.Class<? extends CoolQHttpRequest.Response> getResponseClass() {
		return GetGroupMemberInfoRequest.Response.class;

	}

	public static class Response extends CoolQHttpRequest.Response<ResponseData> {

		/**
		 * 
		 */
		private static final long serialVersionUID = -8920450901362965353L;

	}
}
