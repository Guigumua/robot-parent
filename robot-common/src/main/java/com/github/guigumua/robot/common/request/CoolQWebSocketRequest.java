package com.github.guigumua.robot.common.request;

import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.guigumua.robot.common.util.CoolQWebSocketRequestSerializeFilter;
import com.github.guigumua.robot.common.util.JsonUtil;

import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public interface CoolQWebSocketRequest extends CoolQRequest {

	String getAction();

	@JSONField(ordinal = Integer.MAX_VALUE)
	default Map<String, Object> getParams() {
		return null;
	}

	@JSONField(serialize = false)
	@Override
	default TextWebSocketFrame getRequest() {
		return new TextWebSocketFrame(JsonUtil.toJSON(this, new CoolQWebSocketRequestSerializeFilter()));
	}

}
