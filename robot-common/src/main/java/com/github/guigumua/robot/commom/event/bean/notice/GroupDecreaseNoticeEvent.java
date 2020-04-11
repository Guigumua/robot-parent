package com.github.guigumua.robot.commom.event.bean.notice;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.guigumua.robot.commom.event.EventType;

public class GroupDecreaseNoticeEvent extends NoticeEvent implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7475202506244013471L;
	private String subType;
	private long groupId;
	@JSONField(serialize = false, deserialize = false)
	protected EventType eventType = EventType.GROUP_DECREASE_NOTICE;

	@Override
	public EventType getEventType() {
		return this.eventType;
	}

	/**
	 * 操作者id
	 */
	private long operatorId;

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

	@Override
	public String toString() {
		return "MemberReduceNotice [subType=" + subType + ", groupId=" + groupId + ", operatorId=" + operatorId
				+ ", postType=" + postType + ", noticeType=" + noticeType + ", userId=" + userId + "]";
	}

}
