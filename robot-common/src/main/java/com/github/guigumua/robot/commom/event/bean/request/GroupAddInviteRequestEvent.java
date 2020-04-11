package com.github.guigumua.robot.commom.event.bean.request;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.guigumua.robot.commom.event.EventType;

public class GroupAddInviteRequestEvent extends RequestEvent implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6607196418054727997L;
	private long groupId;
	private String subType;
	@JSONField(serialize = false, deserialize = false)
	protected EventType eventType = EventType.GROUP_REQUEST;

	@Override
	public EventType getEventType() {
		return this.eventType;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	private EventResponse response = new EventResponse();

	public EventResponse getResponse() {
		return response;
	}

	public static class EventResponse extends RequestEventResponse {
		/**
		 * 
		 */
		private static final long serialVersionUID = -100413996735083459L;
		private String reason;

		public String getReason() {
			return reason;
		}

		public void setReason(String reason) {
			this.reason = reason;
		}

	}
}
