package com.github.guigumua.robot.common.request;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public abstract class GetVersionInfoRequest implements CoolQRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7095536976391013931L;

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
		private static final long serialVersionUID = -1697551781348689489L;
		private String coolqDirectory;
		private String coolqEdition;
		private String pluginVersion;
		private int pluginBuildNumber;
		private String pluginBuildConfiguration;

		public String getCoolqDirectory() {
			return coolqDirectory;
		}

		public void setCoolqDirectory(String coolqDirectory) {
			this.coolqDirectory = coolqDirectory;
		}

		public String getCoolqEdition() {
			return coolqEdition;
		}

		public void setCoolqEdition(String coolqEdition) {
			this.coolqEdition = coolqEdition;
		}

		public String getPluginVersion() {
			return pluginVersion;
		}

		public void setPluginVersion(String pluginVersion) {
			this.pluginVersion = pluginVersion;
		}

		public int getPluginBuildNumber() {
			return pluginBuildNumber;
		}

		public void setPluginBuildNumber(int pluginBuildNumber) {
			this.pluginBuildNumber = pluginBuildNumber;
		}

		public String getPluginBuildConfiguration() {
			return pluginBuildConfiguration;
		}

		public void setPluginBuildConfiguration(String pluginBuildConfiguration) {
			this.pluginBuildConfiguration = pluginBuildConfiguration;
		}

	}

	@Override
	public java.lang.Class<? extends CoolQHttpRequest.Response<?>> getResponseClass() {
		return GetVersionInfoRequest.Response.class;

	}

	public static class Response extends CoolQHttpRequest.Response<ResponseData> {

		/**
		 * 
		 */
		private static final long serialVersionUID = -4857430786642679062L;

	}
}
