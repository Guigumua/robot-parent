package com.github.guigumua.robot.common.request.http;

import com.github.guigumua.robot.common.request.CanSendRecordRequest;
import com.github.guigumua.robot.common.request.CoolQHttpRequest;

import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;

public class CanSendRecordHttpRequest extends CanSendRecordRequest implements CoolQHttpRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6459661813962372588L;
	private final String uri = "/can_send_record";

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
