package com.github.guigumua.robot.common.event.notice;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.guigumua.robot.common.event.EventType;

public class FriendAddNoticeEvent extends NoticeEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 209935235010995291L;
	private final String noticeType = "friend_add";
	private String comment;
	private String flag;

	@JSONField(serialize = false)
	@Override
	public EventType getEventType() {
		return EventType.FRIEND_ADD_NOTICE;
	}

	@Override
	public String getNoticeType() {
		return noticeType;
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

}
