package com.github.guigumua.robot.common.event.request;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.guigumua.robot.common.event.EventType;

public class GroupAddInviteRequestEvent extends RequestEvent implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6607196418054727997L;
	private long groupId;
	private String subType;
	private final String requestType = "group";

	@JSONField(serialize = false, deserialize = false)
	@Override
	public EventType getEventType() {
		return EventType.GROUP_REQUEST;
	}

	@Override
	public String getRequestType() {
		return requestType;
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

	@JSONField(serialize = false, deserialize = false)
	private EventResponse response = new EventResponse();

	public EventResponse getResponse() {
		return response;
	}

	public static class EventResponse extends RequestEvent.EventResponse {
		/**
		 * 
		 */
		private static final long serialVersionUID = -100413996735083459L;
		private String reason;

		public String getReason() {
			return reason;
		}

		public EventResponse setReason(String reason) {
			this.reason = reason;
			return this;
		}

	}
}
