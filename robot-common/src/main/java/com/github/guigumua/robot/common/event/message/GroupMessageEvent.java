package com.github.guigumua.robot.common.event.message;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.guigumua.robot.common.event.EventType;

public class GroupMessageEvent extends MessageEvent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3674426525546659733L;
	private final String messageType = "group";
	private String subType;
	private long groupId;
	private Anonymous anonymous;

	@JSONField(serialize = false)
	@Override
	public EventType getEventType() {
		return EventType.GROUP_MESSAGE;
	}

	@Override
	public String getMessageType() {
		return messageType;
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

	public Anonymous getAnonymous() {
		return anonymous;
	}

	public void setAnonymous(Anonymous anonymous) {
		this.anonymous = anonymous;
	}

	public static class Anonymous implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = -5189548584889177192L;
		private long id;
		private String name;
		private String flag;

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getFlag() {
			return flag;
		}

		public void setFlag(String flag) {
			this.flag = flag;
		}
	}

	@JSONField(serialize = false)
	private EventResponse response = new EventResponse();

	public EventResponse getResponse() {
		return response;
	}

	public static class EventResponse extends MessageEvent.EventResponse {
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
