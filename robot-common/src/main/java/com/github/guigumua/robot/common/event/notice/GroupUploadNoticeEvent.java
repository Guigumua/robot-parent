package com.github.guigumua.robot.common.event.notice;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.guigumua.robot.common.event.EventType;

public class GroupUploadNoticeEvent extends NoticeEvent implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 461907822669915104L;
	private final String noticeType = "group_upload";
	private long groupId;
	private FileInfo file;

	@JSONField(serialize = false)
	@Override
	public EventType getEventType() {
		return EventType.GROUP_UPLOAD_NOTICE;
	}

	@Override
	public String getNoticeType() {
		return this.noticeType;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public FileInfo getFile() {
		return file;
	}

	public void setFile(FileInfo file) {
		this.file = file;
	}

	public static class FileInfo implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = -2858425917282408783L;
		private String id;
		private String name;
		private long size;
		private long busid;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public long getSize() {
			return size;
		}

		public void setSize(long size) {
			this.size = size;
		}

		public long getBusid() {
			return busid;
		}

		public void setBusid(long busid) {
			this.busid = busid;
		}

	}
}
