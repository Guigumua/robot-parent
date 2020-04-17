package com.github.guigumua.robot.common.event.notice;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.guigumua.robot.common.event.EventType;

public class GroupBanNoticeEvent extends NoticeEvent implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9068422629975989597L;
	private final String noticeType = "group_ban";
	private String subType;
	private long groupId;
	private long operatorId;
	private long duration;

	@JSONField(serialize = false)
	@Override
	public EventType getEventType() {
		return EventType.GROUP_BAN_NOTICE;
	}

	@Override
	public String getNoticeType() {
		return this.noticeType;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(long operatorId) {
		this.operatorId = operatorId;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

}
