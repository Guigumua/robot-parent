package com.github.guigumua.robot.commom.event.bean.notice;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.guigumua.robot.commom.event.EventType;

public class GroupAdminNoticeEvent extends NoticeEvent implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8454966821424760625L;
	/**
	 * set:设置管理员 unset:取消管理员
	 */
	private String subType;
	/**
	 * 群号
	 */
	private long groupId;
	@JSONField(serialize = false, deserialize = false)
	protected EventType eventType = EventType.GROUP_ADMIN_NOTICE;

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

	@Override
	public String toString() {
		return "GroupAdminChangeNotice [subType=" + subType + ", groupId=" + groupId + ", postType=" + postType
				+ ", noticeType=" + noticeType + ", userId=" + userId + "]";
	}

}
