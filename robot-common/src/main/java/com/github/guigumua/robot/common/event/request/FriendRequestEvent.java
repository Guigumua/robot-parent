package com.github.guigumua.robot.common.event.request;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.guigumua.robot.common.event.EventType;

public class FriendRequestEvent extends RequestEvent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3510165971855531449L;
	private final String requestType = "friend";

	@JSONField(serialize = false, deserialize = false)
	@Override
	public EventType getEventType() {
		return EventType.FRIEND_REQUSET;
	}

	@JSONField(serialize = false, deserialize = false)
	private EventResponse response = new EventResponse();

	public EventResponse getResponse() {
		return response;
	}
	
	@Override
	public String getRequestType() {
		return requestType;
	}
	public static class EventResponse extends RequestEvent.EventResponse {
		/**
		 * 
		 */
		private static final long serialVersionUID = -2616244590190245834L;
		private String remark;

		public String getRemark() {
			return remark;
		}

		public EventResponse setRemark(String remark) {
			this.remark = remark;
			return this;
		}

	}
}
