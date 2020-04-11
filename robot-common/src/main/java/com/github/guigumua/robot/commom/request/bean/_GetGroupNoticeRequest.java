package com.github.guigumua.robot.commom.request.bean;

import java.io.Serializable;
import java.util.List;

import com.github.guigumua.robot.commom.request.CoolQHttpRequest;

public class _GetGroupNoticeRequest extends CoolQHttpRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 939382124040989267L;
	private long groupId;
	private final String uri = "/_get_group_notice";
	private volatile Response response;

	public Response getResponse() {
		return response;
	}

	public void setResponse(CoolQHttpRequest.Response<?> response) {
		this.response = (Response) response;
	}

	@Override
	public String uri() {
		return this.uri;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public static class ResponseData implements CoolQHttpRequest.ResponseData {
		/**
		 * 
		 */
		private static final long serialVersionUID = -162049137716544080L;
		private int cn;
		private String fid;
		private int fn;
		private int isAllConfirm;
		private int isRead;
		private Object msg;
		private long pubt;
		private int readNum;
		private Object settings;
		private int type;
		private long u;
		private int v;

		public int getCn() {
			return cn;
		}

		public void setCn(int cn) {
			this.cn = cn;
		}

		public String getFid() {
			return fid;
		}

		public void setFid(String fid) {
			this.fid = fid;
		}

		public int getFn() {
			return fn;
		}

		public void setFn(int fn) {
			this.fn = fn;
		}

		public int getIsAllConfirm() {
			return isAllConfirm;
		}

		public void setIsAllConfirm(int isAllConfirm) {
			this.isAllConfirm = isAllConfirm;
		}

		public int getIsRead() {
			return isRead;
		}

		public void setIsRead(int isRead) {
			this.isRead = isRead;
		}

		public Object getMsg() {
			return msg;
		}

		public void setMsg(Object msg) {
			this.msg = msg;
		}

		public long getPubt() {
			return pubt;
		}

		public void setPubt(long pubt) {
			this.pubt = pubt;
		}

		public int getReadNum() {
			return readNum;
		}

		public void setReadNum(int readNum) {
			this.readNum = readNum;
		}

		public Object getSettings() {
			return settings;
		}

		public void setSettings(Object settings) {
			this.settings = settings;
		}

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}

		public long getU() {
			return u;
		}

		public void setU(long u) {
			this.u = u;
		}

		public int getV() {
			return v;
		}

		public void setV(int v) {
			this.v = v;
		}

	}

	@SuppressWarnings("rawtypes")
	@Override
	public java.lang.Class<? extends CoolQHttpRequest.Response> getResponseClass() {
		return _GetGroupNoticeRequest.Response.class;

	}

	public static class Response extends CoolQHttpRequest.Response<List<ResponseData>> {

		/**
		 * 
		 */
		private static final long serialVersionUID = -3793229794592187065L;

	}

}
