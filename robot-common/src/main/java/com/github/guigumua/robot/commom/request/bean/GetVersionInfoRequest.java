package com.github.guigumua.robot.commom.request.bean;

import java.io.Serializable;

import com.github.guigumua.robot.commom.request.CoolQHttpRequest;

public class GetVersionInfoRequest extends CoolQHttpRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7095536976391013931L;
	private final String uri = "/get_version_info";
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

	@SuppressWarnings("rawtypes")
	@Override
	public java.lang.Class<? extends CoolQHttpRequest.Response> getResponseClass() {
		return GetVersionInfoRequest.Response.class;

	}

	public static class Response extends CoolQHttpRequest.Response<ResponseData> {

		/**
		 * 
		 */
		private static final long serialVersionUID = -4857430786642679062L;

	}
}
