package com.github.guigumua.robot.common.event.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.guigumua.robot.common.event.Event;
import com.github.guigumua.robot.common.event.EventType;

public abstract class RequestEvent implements Event {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7600881536420771290L;
	protected final String postType = "request";
	protected String requestType;
	protected long userId;
	protected String comment;
	protected String flag;
	protected long selfId;

	@JSONField(serialize = false)
	public long getSelfId() {
		return selfId;
	}

	public void setSelfId(long selfId) {
		this.selfId = selfId;
	}

	@JSONField(serialize = false, deserialize = false)
	public EventType getEventType() {
		return EventType.REQUEST;
	}

	public String getPostType() {
		return postType;
	}

	public abstract String getRequestType();

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public static abstract class EventResponse implements Event.EventResponse {

		/**
		 * 
		 */
		private static final long serialVersionUID = 592280869110182341L;
		private boolean block;
		private boolean approve;

		@Override
		public EventResponse setBlock(boolean block) {
			this.block = block;
			return this;
        }

		@Override
		public boolean isBlock() {
			return this.block;
		}

		public boolean isApprove() {
			return approve;
		}

		public EventResponse setApprove(boolean approve) {
			this.approve = approve;
			return this;
		}

	}
}
