package com.github.guigumua.robot.common.request.ws;

import com.github.guigumua.robot.common.request.CoolQWebSocketRequest;
import com.github.guigumua.robot.common.request.GetRecordRequest;

public class GetRecordWsRequest extends GetRecordRequest implements CoolQWebSocketRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -897817699411206469L;
	private final String action = "get_record";

	public String getAction() {
		return this.action;
	}

}
