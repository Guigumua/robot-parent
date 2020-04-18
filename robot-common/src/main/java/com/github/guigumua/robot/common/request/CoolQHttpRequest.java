package com.github.guigumua.robot.common.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.guigumua.robot.common.util.JsonUtil;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

public interface CoolQHttpRequest extends CoolQRequest {

	/**
	 * 
	 */

	String uri();

	@JSONField(serialize = false)
	@Override
	default FullHttpRequest getRequest() {
		FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, uri());
		ByteBuf content = request.content();
		content.writeBytes(JsonUtil.toJSON(this).getBytes(CharsetUtil.UTF_8));
		HttpHeaders headers = request.headers();
		headers.add(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON);
		headers.add(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
		return request;
	}

}
