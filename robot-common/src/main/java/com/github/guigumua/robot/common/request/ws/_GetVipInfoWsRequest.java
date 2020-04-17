package com.github.guigumua.robot.common.request.ws;

import java.util.HashMap;
import java.util.Map;

import com.github.guigumua.robot.common.request.CoolQWebSocketRequest;
import com.github.guigumua.robot.common.request._GetVipInfoRequest;

public class _GetVipInfoWsRequest extends _GetVipInfoRequest implements CoolQWebSocketRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -861065484699329306L;

	private final String action = "_get_vip_info";
	private Map<String, Object> params = new HashMap<>();

	@Override
	public Map<String, Object> getParams() {
		return this.params;
	}

	public String getAction() {
		return this.action;
	}
}
