package com.github.guigumua.robot.common.request;

import com.alibaba.fastjson.annotation.JSONField;

public abstract class _GetVipInfoRequest implements CoolQRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1947547749852933588L;
	private long userId;
	private volatile Response response;

	@JSONField(serialize = false)
	public Response getResponse() {
		return response;
	}

	public void setResponse(CoolQHttpRequest.Response<?> response) {
		this.response = (Response) response;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Override
	public java.lang.Class<? extends CoolQHttpRequest.Response<?>> getResponseClass() {
		return _GetVipInfoRequest.Response.class;

	}

	public static class ResponseData implements CoolQHttpRequest.ResponseData {
		/**
		 * 
		 */
		private static final long serialVersionUID = 6003745311011816918L;
		private long userId;
		private String nickname;
		private int level;
		private int levelSpeed;
		private String vipLevel;
		private int vipGrowthSpeed;
		private String vipGrowthTotal;

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

		public int getLevel() {
			return level;
		}

		public void setLevel(int level) {
			this.level = level;
		}

		public int getLevelSpeed() {
			return levelSpeed;
		}

		public void setLevelSpeed(int levelSpeed) {
			this.levelSpeed = levelSpeed;
		}

		public String getVipLevel() {
			return vipLevel;
		}

		public void setVipLevel(String vipLevel) {
			this.vipLevel = vipLevel;
		}

		public int getVipGrowthSpeed() {
			return vipGrowthSpeed;
		}

		public void setVipGrowthSpeed(int vipGrowthSpeed) {
			this.vipGrowthSpeed = vipGrowthSpeed;
		}

		public String getVipGrowthTotal() {
			return vipGrowthTotal;
		}

		public void setVipGrowthTotal(String vipGrowthTotal) {
			this.vipGrowthTotal = vipGrowthTotal;
		}
	}

	public static class Response extends CoolQHttpRequest.Response<ResponseData> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 5099461193113323904L;

	}
}
