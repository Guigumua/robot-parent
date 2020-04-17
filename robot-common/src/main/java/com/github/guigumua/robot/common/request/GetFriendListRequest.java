package com.github.guigumua.robot.common.request;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public abstract class GetFriendListRequest implements CoolQRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4236133724850537413L;

	private volatile Response response;

	@JSONField(serialize = false)
	public Response getResponse() {
		return response;
	}

	public void setResponse(CoolQHttpRequest.Response<?> response) {
		this.response = (Response) response;
	}

	public static class ResponseData implements Serializable, CoolQHttpRequest.ResponseData {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1235317947003393066L;
		private long userId;
		private String nickname;
		private String remark;

		public long getUserId() {
			return userId;
		}

		public void setUserId(long userId) {
			this.userId = userId;
		}

		public String getNickname() {
			return nickname;
		}

		public void setNickname(String nickname) {
			this.nickname = nickname;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

	}

	@Override
	public java.lang.Class<? extends CoolQHttpRequest.Response<?>> getResponseClass() {
		return GetFriendListRequest.Response.class;

	}

	public static class Response extends CoolQHttpRequest.Response<List<ResponseData>> {

		/**
		 * 
		 */
		private static final long serialVersionUID = -6628283588342069303L;

	}

}
