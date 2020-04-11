package com.github.guigumua.robot.commom.event.bean.request;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.guigumua.robot.commom.event.EventType;

public class FriendAddRequestEvent extends RequestEvent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3510165971855531449L;
	@JSONField(serialize = false, deserialize = false)
	protected EventType eventType = EventType.FRIEND_REQUSET;

	@Override
	public EventType getEventType() {
		return this.eventType;
	}

	private EventResponse response = new EventResponse();

	public EventResponse getResponse() {
		return response;
	}

	public static class EventResponse extends RequestEventResponse {
		/**
		 * 
		 */
		private static final long serialVersionUID = -2616244590190245834L;
		private String remark;

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

	}
}
