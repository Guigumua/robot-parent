package com.github.guigumua.robot.common.event.message;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.guigumua.robot.common.event.EventType;

public class DiscussMessageEvent extends MessageEvent {
	/**
	 * 
	 */
	/**
	 * 
	 */
	private static final long serialVersionUID = -1985646106140830426L;
	private int discussId;
	private final String messageType = "discuss";

	@JSONField(serialize = false)
	@Override
	public EventType getEventType() {
		return EventType.DISCUSS_MESSAGE;
	}

	@Override
	public String getMessageType() {
		return messageType;
	}

	public int getDiscussId() {
		return discussId;
	}

	public void setDiscussId(int discussId) {
		this.discussId = discussId;
	}

	@JSONField(serialize = false, deserialize = false)
	private EventResponse response = new EventResponse();

	public EventResponse getResponse() {
		return response;
	}

	public static class EventResponse extends MessageEvent.EventResponse {
		/**
		 * 
		 */
		private static final long serialVersionUID = 9162034149163552318L;
		private boolean atSender;

		public boolean isAtSender() {
			return atSender;
		}

		public void setAtSender(boolean atSender) {
			this.atSender = atSender;
		}

	}
}
