package com.github.guigumua.robot.common.event.notice;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.guigumua.robot.common.event.EventType;

public class GroupAdminNoticeEvent extends NoticeEvent implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8454966821424760625L;
	private final String noticeType = "group_admin";
	/**
	 * set:设置管理员 unset:取消管理员
	 */
	private String subType;
	private long groupId;
	@JSONField(serialize = false)
	@Override
	public EventType getEventType() {
		return EventType.GROUP_ADMIN_NOTICE;
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

	@Override
	public String getNoticeType() {
		return this.noticeType;
	}
}
