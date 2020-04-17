package com.github.guigumua.robot.common.request;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.guigumua.robot.common.util.JsonUtil;

public interface CoolQRequest extends Serializable {

	Object getRequest();

	void setResponse(Response<?> response);

	Response<?> getResponse();

	@JSONField(serialize = false)
	Class<? extends Response<?>> getResponseClass();

	public static interface ResponseData extends Serializable {
	}

	public static class Response<T> implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1345355804704045305L;
		private T data;
		private String status;
		private int retcode;

		public T getData() {
			return data;
		}

		public void setData(T data) {
			this.data = data;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public int getRetcode() {
			return retcode;
		}

		public void setRetcode(int retcode) {
			this.retcode = retcode;
		}

		@Override
		public String toString() {
			return JsonUtil.toJSON(this);
		}
	}
}
