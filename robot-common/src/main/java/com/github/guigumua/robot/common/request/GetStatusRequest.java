package com.github.guigumua.robot.common.request;

import com.alibaba.fastjson.annotation.JSONField;

public abstract class GetStatusRequest implements CoolQRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2459950186343766234L;

	private volatile Response response;

	@JSONField(serialize = false)
	public Response getResponse() {
		return response;
	}

	public void setResponse(CoolQHttpRequest.Response<?> response) {
		this.response = (Response) response;
	}

	public static class ResponseData implements CoolQHttpRequest.ResponseData {
		/**
		 * 
		 */
		private static final long serialVersionUID = -664199394163251599L;
		private boolean appInitialized;
		private boolean appEnabled;
		private Object pluginsGood;
		private boolean appGood;
		private boolean online;
		private boolean good;

		public boolean isAppInitialized() {
			return appInitialized;
		}

		public void setAppInitialized(boolean appInitialized) {
			this.appInitialized = appInitialized;
		}

		public boolean isAppEnabled() {
			return appEnabled;
		}

		public void setAppEnabled(boolean appEnabled) {
			this.appEnabled = appEnabled;
		}

		public Object getPluginsGood() {
			return pluginsGood;
		}

		public void setPluginsGood(Object pluginsGood) {
			this.pluginsGood = pluginsGood;
		}

		public boolean isAppGood() {
			return appGood;
		}

		public void setAppGood(boolean appGood) {
			this.appGood = appGood;
		}

		public boolean isOnline() {
			return online;
		}

		public void setOnline(boolean online) {
			this.online = online;
		}

		public boolean isGood() {
			return good;
		}

		public void setGood(boolean good) {
			this.good = good;
		}

	}

	@Override
	public java.lang.Class<? extends CoolQHttpRequest.Response<?>> getResponseClass() {
		return GetStatusRequest.Response.class;

	}

	public static class Response extends CoolQHttpRequest.Response<ResponseData> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 2059580975596341728L;

	}
}
