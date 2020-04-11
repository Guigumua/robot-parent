package com.github.guigumua.robot.commom.event.bean.notice;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.guigumua.robot.commom.event.EventType;

public class FriendAddNoticeEvent extends NoticeEvent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 209935235010995291L;
	private String comment;
	private String flag;

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

	@JSONField(serialize = false, deserialize = false)
	private final EventType eventType = EventType.FRIEND_ADD_NOTICE;

	@Override
	public EventType getEventType() {
		return this.eventType;
	}

	@Override
	public String toString() {
		return "FriendAddNotice [postType=" + postType + ", noticeType=" + noticeType + ", userId=" + userId + "]";
	}

}
