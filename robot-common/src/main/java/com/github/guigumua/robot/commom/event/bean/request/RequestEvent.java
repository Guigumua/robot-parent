package com.github.guigumua.robot.commom.event.bean.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.guigumua.robot.commom.event.Event;
import com.github.guigumua.robot.commom.event.EventType;

public class RequestEvent implements Event {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7600881536420771290L;
	protected String postType;
	protected String requestType;
	protected long userId;
	protected String comment;
	protected String flag;
	protected long selfId;

	public long getSelfId() {
		return selfId;
	}

	@JSONField(serialize = false, deserialize = false)
	protected EventResponse reponse = new RequestEventResponse();
	@JSONField(serialize = false, deserialize = false)
	protected EventType eventType = EventType.REQUEST;

	public EventType getEventType() {
		return eventType;
	}

	@Override
	public EventResponse getResponse() {
		return this.reponse;
	}

	public String getPostType() {
		return postType;
	}

	public void setPostType(String postType) {
		this.postType = postType;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

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

	public static class RequestEventResponse implements EventResponse {

		/**
		 * 
		 */
		private static final long serialVersionUID = 592280869110182341L;
		private boolean block;
		private boolean approve;

		@Override
		public void setBlock(boolean block) {
			this.block = block;
		}

		@Override
		public boolean isBlock() {
			return this.block;
		}

		public boolean isApprove() {
			return approve;
		}

		public void setApprove(boolean approve) {
			this.approve = approve;
		}

	}
}
