package com.github.guigumua.robot.common.request.http;

import com.github.guigumua.robot.common.request.CleanPluginLogRequest;
import com.github.guigumua.robot.common.request.CoolQHttpRequest;

import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;

public class CleanPluginLogHttpRequest extends CleanPluginLogRequest implements CoolQHttpRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7539655302332954764L;
	private final String uri = "/clean_plugin_log";

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
