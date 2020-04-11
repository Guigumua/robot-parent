package com.github.guigumua.robot.commom.event.bean.notice;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.guigumua.robot.commom.event.Event;
import com.github.guigumua.robot.commom.event.EventType;

public class NoticeEvent implements Event {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8006313527523748336L;
	protected String postType;
	protected String noticeType;
	protected long userId;
	protected long selfId;
	public long getSelfId() {
		return selfId;
	}

	@JSONField(serialize = false, deserialize = false)
	protected EventType eventType = EventType.NOTICE;

	public EventType getEventType() {
		return eventType;
	}

	@JSONField(serialize = false, deserialize = false)
	protected NoticeEventResponse reponse = new NoticeEventResponse();

	@Override
	public NoticeEventResponse getResponse() {
		return this.reponse;
	}

	public String getPostType() {
		return postType;
	}

	public void setPostType(String postType) {
		this.postType = postType;
	}

	public String getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public static class NoticeEventResponse implements EventResponse {

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
