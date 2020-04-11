package com.github.guigumua.robot.commom.request;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.guigumua.robot.commom.util.JsonUtil;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

public abstract class CoolQHttpRequest {
	@JSONField(serialize = false)
	protected FullHttpRequest request;
	@JSONField(serialize = false)
	protected final String uri = "";
	@JSONField(serialize = false)
	protected volatile Response<?> response = null;
	@SuppressWarnings("rawtypes")
	@JSONField(serialize = false)
	public Class<? extends Response> getResponseClass(){
		return CoolQHttpRequest.Response.class;
	}
	private static final String ASYNC_SUFFIX = "_async";

	protected CoolQHttpRequest() {
		this.request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, this.uri());
	}

	protected CoolQHttpRequest(HttpMethod method) {
		this.request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, method, this.uri());
	}

	public FullHttpRequest get() {
		ByteBuf content = request.content();
		content.writeBytes(this.toJsonString().getBytes(CharsetUtil.UTF_8));
		HttpHeaders headers = request.headers();
		headers.add(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON);
		headers.add(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
		return request;
	}

	@JSONField(serialize = false)
	public FullHttpRequest getAsync() {
		return this.get().setUri(request.uri() + ASYNC_SUFFIX);
	}

	public Response<?> getResponse() {
		return response;
	}

	public void setResponse(Response<?> response) {
		this.response = response;
	}

	public String toJsonString() {
		return JsonUtil.toJSON(this);
	}

	public String uri() {
		return this.uri;
	}

	@Override
	public String toString() {
		return JsonUtil.toJSON(this);
	}

	public static interface ResponseData extends Serializable {

	}

	public static class Response<T> implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1345355804704045305L;
		private T data;
		private String status;
		private int retcode;

		public T getData() {
			return data;
		}

		public void setData(T data) {
			this.data = data;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public int getRetcode() {
			return retcode;
		}

		public void setRetcode(int retcode) {
			this.retcode = retcode;
		}

		@Override
		public String toString() {
			return JsonUtil.toJSON(this);
		}
	}
}
