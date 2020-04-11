package com.github.guigumua.robot.commom.event.bean.message;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.guigumua.robot.commom.event.EventType;

public class PrivateMessageEvent extends MessageEvent implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8537555890254509518L;
	private final String messageType = "private";
	private String subType;
	@JSONField(serialize = false, deserialize = false)
	protected EventType eventType = EventType.PRIVATE_MESSAGE;
	private EventResponse response = new EventResponse();

	@Override
	public EventResponse getResponse() {
		return this.response;
	}

	@Override
	public EventType getEventType() {
		return this.eventType;
	}

	public String getMessageType() {
		return messageType;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRawMessage() {
		return rawMessage;
	}

	public void setRawMessage(String rawMessage) {
		this.rawMessage = rawMessage;
	}

	public int getFont() {
		return font;
	}

	public void setFont(int font) {
		this.font = font;
	}

	public Sender getSender() {
		return sender;
	}

	public void setSender(Sender sender) {
		this.sender = sender;
	}

	public static class EventResponse extends MsgEventResponse {
		/**
		 * 
		 */
		private static final long serialVersionUID = -2037444430449397522L;
	}
}
