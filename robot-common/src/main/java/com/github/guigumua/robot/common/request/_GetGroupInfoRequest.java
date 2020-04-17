package com.github.guigumua.robot.common.request;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public abstract class _GetGroupInfoRequest implements CoolQRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5269035127395237138L;
	private long groupId;
	private volatile Response response;

	@JSONField(serialize = false)
	public Response getResponse() {
		return response;
	}

	public void setResponse(CoolQRequest.Response<?> response) {
		this.response = (Response) response;
	}


	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public static class ResponseData implements CoolQRequest.ResponseData {
		/**
		 * 
		 */
		private static final long serialVersionUID = -1626017701821572582L;
		private long groupId;
		private String groupName;
		private long createTime;
		private int category;
		private int memberCount;
		private int maxMemberCount;
		private String introduction;
		private List<Admin> admins;
		private int adminCount;
		private int maxAdminCount;
		private long ownerId;

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

		public long getCreateTime() {
			return createTime;
		}

		public void setCreateTime(long createTime) {
			this.createTime = createTime;
		}

		public int getCategory() {
			return category;
		}

		public void setCategory(int category) {
			this.category = category;
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

		public String getIntroduction() {
			return introduction;
		}

		public void setIntroduction(String introduction) {
			this.introduction = introduction;
		}

		public List<Admin> getAdmins() {
			return admins;
		}

		public void setAdmins(List<Admin> admins) {
			this.admins = admins;
		}

		public int getAdminCount() {
			return adminCount;
		}

		public void setAdminCount(int adminCount) {
			this.adminCount = adminCount;
		}

		public int getMaxAdminCount() {
			return maxAdminCount;
		}

		public void setMaxAdminCount(int maxAdminCount) {
			this.maxAdminCount = maxAdminCount;
		}

		public long getOwnerId() {
			return ownerId;
		}

		public void setOwnerId(long ownerId) {
			this.ownerId = ownerId;
		}

		public static class Admin implements Serializable {
			/**
			 * 
			 */
			private static final long serialVersionUID = -7764135609528820621L;
			private long userId;
			private String nickname;
			private String role;

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

			public String getRole() {
				return role;
			}

			public void setRole(String role) {
				this.role = role;
			}
		}
	}

	@Override
	public Class<? extends CoolQRequest.Response<?>> getResponseClass() {
		return _GetGroupInfoRequest.Response.class;

	}

	public static class Response extends CoolQRequest.Response<ResponseData> {

		/**
		 * 
		 */
		private static final long serialVersionUID = -4928770801818051203L;

	}
}
