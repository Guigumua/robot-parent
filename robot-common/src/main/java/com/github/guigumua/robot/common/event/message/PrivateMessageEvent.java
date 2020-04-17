package com.github.guigumua.robot.common.event.message;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.guigumua.robot.common.event.EventType;

public class PrivateMessageEvent extends MessageEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2656799296919755835L;
	private final String messageType = "private";
	private String subType;

	public String getMessageType() {
		return messageType;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	@JSONField(serialize = false)
	@Override
	public EventType getEventType() {
		return EventType.PRIVATE_MESSAGE;
	}

	@JSONField(serialize = false)
	private EventResponse response = new EventResponse();

	@Override
	public EventResponse getResponse() {
		return response;
	}

	public static class EventResponse extends MessageEvent.EventResponse {
		/**
		 * 
		 */
		private static final long serialVersionUID = -8191575479750934383L;
		/**
		 * 
		 */
		private boolean autoEscape;

		public static long getSerialversionuid() {
			return serialVersionUID;
		}

		public boolean isAutoEscape() {
			return autoEscape;
		}

		public void setAutoEscape(boolean autoEscape) {
			this.autoEscape = autoEscape;
		}

	}

}
