package com.github.guigumua.robot.commom.event.bean.message;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.guigumua.robot.commom.event.EventType;

public class DiscussMessageEvent extends MessageEvent implements Serializable {
	/**
	 * 
	 */
	/**
	 * 
	 */
	private static final long serialVersionUID = -1985646106140830426L;
	private int discussId;
	@JSONField(serialize = false, deserialize = false)
	protected EventType eventType = EventType.DISCUSS_MESSAGE;
	private EventResponse response = new EventResponse();

	public EventResponse getResponse() {
		return response;
	}

	@Override
	public EventType getEventType() {
		return eventType;
	}

	public int getDiscussId() {
		return discussId;
	}

	public void setDiscussId(int discussId) {
		this.discussId = discussId;
	}

	public static class EventResponse extends MsgEventResponse {
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
