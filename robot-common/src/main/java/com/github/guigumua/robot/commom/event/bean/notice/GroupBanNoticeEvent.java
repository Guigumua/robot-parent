package com.github.guigumua.robot.commom.event.bean.notice;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.guigumua.robot.commom.event.EventType;

public class GroupBanNoticeEvent extends NoticeEvent implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9068422629975989597L;
	private String subType;
	private long groupId;
	private long operatorId;
	private long duration;
	@JSONField(serialize = false, deserialize = false)
	protected EventType eventType = EventType.GROUP_BAN_NOTICE;

	@Override
	public EventType getEventType() {
		return this.eventType;
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

	@Override
	public String toString() {
		return "GroupMutedNotice [subType=" + subType + ", groupId=" + groupId + ", operatorId=" + operatorId
				+ ", duration=" + duration + ", postType=" + postType + ", noticeType=" + noticeType + ", userId="
				+ userId + "]";
	}

}
