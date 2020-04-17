package com.github.guigumua.robot.common.event.notice;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.guigumua.robot.common.event.EventType;

public class GroupDecreaseNoticeEvent extends NoticeEvent implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7475202506244013471L;
	private String subType;
	private long groupId;
	/**
	 * 操作者id
	 */
	private long operatorId;
	private final String noticeType = "group_decrease";

	@JSONField(serialize = false)
	@Override
	public EventType getEventType() {
		return EventType.GROUP_DECREASE_NOTICE;
	}

	@Override
	public String getNoticeType() {
		return noticeType;
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
}
