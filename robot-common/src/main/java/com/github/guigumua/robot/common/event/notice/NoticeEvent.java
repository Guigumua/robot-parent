package com.github.guigumua.robot.common.event.notice;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.guigumua.robot.common.event.Event;
import com.github.guigumua.robot.common.event.EventType;

public abstract class NoticeEvent implements Event {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8006313527523748336L;
	protected final String postType = "notice";
	protected long userId;
	@JSONField(serialize = false)
	protected long selfId;

	public long getSelfId() {
		return selfId;
	}

	public void setSelfId(long selfId) {
		this.selfId = selfId;
	}

	@JSONField(serialize = false)
	public EventType getEventType() {
		return EventType.NOTICE;
	}

	@JSONField(serialize = false, deserialize = false)
	protected EventResponse reponse = new EventResponse();

	@Override
	public EventResponse getResponse() {
		return this.reponse;
	}

	public String getPostType() {
		return postType;
	}

	public abstract String getNoticeType();

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public static class EventResponse implements Event.EventResponse {
		/**
		 * 
		 */
		private static final long serialVersionUID = -1903330835035261864L;
		private boolean block;

		@Override
		public void setBlock(boolean block) {
			this.block = block;
		}

		@Override
		public boolean isBlock() {
			return this.block;
		}

	}
}
