package com.github.guigumua.robot.common.request.http;

import com.github.guigumua.robot.common.request.CoolQHttpRequest;
import com.github.guigumua.robot.common.request.GetDataRequest;

import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;

public class GetDataHttpRequest extends GetDataRequest implements CoolQHttpRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 458658451602753745L;
	private final String uri = "/data/";

	@Override
	public String uri() {
		return this.uri + getFile();
	}

	@Override
	public FullHttpRequest getRequest() {
		FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, uri());
		return request;
	}
}
