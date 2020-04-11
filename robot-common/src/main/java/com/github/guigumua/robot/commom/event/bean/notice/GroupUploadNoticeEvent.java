package com.github.guigumua.robot.commom.event.bean.notice;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.guigumua.robot.commom.event.EventType;

public class GroupUploadNoticeEvent extends NoticeEvent implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 461907822669915104L;
	private long groupId;
	private FileInfo file;
	@JSONField(serialize = false, deserialize = false)
	protected EventType eventType = EventType.GROUP_ADMIN_NOTICE;

	@Override
	public EventType getEventType() {
		return this.eventType;
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

	@Override
	public String toString() {
		return "GroupFileUploadNotice [groupId=" + groupId + ", file=" + file + ", postType=" + postType
				+ ", noticeType=" + noticeType + ", userId=" + userId + "]";
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

		@Override
		public String toString() {
			return "FileInfo [id=" + id + ", name=" + name + ", size=" + size + ", busid=" + busid + "]";
		}

	}

}
