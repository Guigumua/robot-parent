package com.github.guigumua.robot.commom.event.bean.message;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.guigumua.robot.commom.event.EventType;
import com.github.guigumua.robot.commom.request.bean.SetGroupAnonymousBanRequest;

public class GroupMessageEvent extends MessageEvent implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3674426525546659733L;
	private String subType;
	private long groupId;
	private Anonymous anonymous;
	@JSONField(serialize = false, deserialize = false)
	protected EventType eventType = EventType.GROUP_MESSAGE;
	private EventResponse response = new EventResponse();

	public EventResponse getResponse() {
		return response;
	}

	@Override
	public EventType getEventType() {
		return this.eventType;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
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

	public Anonymous getAnonymous() {
		return anonymous;
	}

	public void setAnonymous(Anonymous anonymous) {
		this.anonymous = anonymous;
	}

	@Override
	public String toString() {
		return "GroupMessageEvent [subType=" + subType + ", groupId=" + groupId + ", anonymous=" + anonymous
				+ ", eventType=" + eventType + "]";
	}

	public static class Anonymous extends SetGroupAnonymousBanRequest.Anonymous implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 4575991367161369696L;

	}

	public static class EventResponse extends MsgEventResponse {
		/**
		 * 
		 */
		private static final long serialVersionUID = -150416743303743437L;
		private boolean atSender;
		private boolean delelte;
		private boolean kick;
		private boolean ban;
		private int banDuration;

		public boolean isAtSender() {
			return atSender;
		}

		public void setAtSender(boolean atSender) {
			this.atSender = atSender;
		}

		public boolean isDelelte() {
			return delelte;
		}

		public void setDelelte(boolean delelte) {
			this.delelte = delelte;
		}

		public boolean isKick() {
			return kick;
		}

		public void setKick(boolean kick) {
			this.kick = kick;
		}

		public boolean isBan() {
			return ban;
		}

		public void setBan(boolean ban) {
			this.ban = ban;
		}

		public int getBanDuration() {
			return banDuration;
		}

		public void setBanDuration(int banDuration) {
			this.banDuration = banDuration;
		}

	}

}
