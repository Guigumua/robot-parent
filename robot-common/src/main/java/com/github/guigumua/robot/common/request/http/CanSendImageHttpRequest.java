package com.github.guigumua.robot.common.request.http;

import com.github.guigumua.robot.common.request.CanSendImageRequest;
import com.github.guigumua.robot.common.request.CoolQHttpRequest;

import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;

public class CanSendImageHttpRequest extends CanSendImageRequest implements CoolQHttpRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4006997286127788587L;
	private final String uri = "/can_send_image";

	@Override
	public String uri() {
		return this.uri;
	}

	@Override
	public FullHttpRequest getRequest() {
		FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, uri());
		return request;
	}
}
