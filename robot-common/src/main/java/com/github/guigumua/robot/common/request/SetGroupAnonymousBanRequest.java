package com.github.guigumua.robot.common.request;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public abstract class SetGroupAnonymousBanRequest implements CoolQRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4454456017330752982L;
	private long groupId;
	private Anonymous anonymous;
	private String flag;
	private int duration;

	private volatile Response response;

	@JSONField(serialize = false)
	public Response getResponse() {
		return response;
	}

	public void setResponse(CoolQRequest.Response<?> response) {
		this.response = (Response) response;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public Anonymous getAnonymous() {
		return anonymous;
	}

	public void setAnonymous(Anonymous anonymous) {
		this.anonymous = anonymous;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public static class Anonymous implements Serializable {
		private static final long serialVersionUID = 436613041184484954L;
		private long id;
		private String name;
		private String flag;

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getFlag() {
			return flag;
		}

		public void setFlag(String flag) {
			this.flag = flag;
		}

		@Override
		public String toString() {
			return "Anonymous [id=" + id + ", name=" + name + ", flag=" + flag + "]";
		}
	}

	@Override
	public java.lang.Class<? extends CoolQHttpRequest.Response<?>> getResponseClass() {
		return SetGroupAnonymousBanRequest.Response.class;

	}

	public static class Response extends CoolQHttpRequest.Response<ResponseData> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 7293082302146293231L;

	}

}
