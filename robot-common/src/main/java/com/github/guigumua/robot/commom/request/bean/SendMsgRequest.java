package com.github.guigumua.robot.commom.request.bean;

import java.io.Serializable;

import com.github.guigumua.robot.commom.request.CoolQHttpRequest;

import io.netty.handler.codec.http.FullHttpRequest;

public class SendMsgRequest extends CoolQHttpRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7802930206295362259L;
	private final String SEND_PRIVATE_MSG = "/send_private_msg";
	private final String SEND_GROUP_MSG = "/send_group_msg";
	private final String SEND_DISCUSS_MSG = "/send_discuss_msg";
	private final String SEND_MSG = "/send_msg";
	public static final String PRIVATE_TYPE = "private";
	public static final String GROUP_TYPE = "group";
	public static final String DISCUSS_TYPE = "discuss";
	private String messageType;
	private long userId;
	private long groupId;
	private long discussId;
	private String message;
	private boolean autoEscape;
	private volatile Response response;

	public Response getResponse() {
		return response;
	}

	public void setResponse(CoolQHttpRequest.Response<?> response) {
		this.response = (Response) response;
	}

	@Override
	public FullHttpRequest get() {
		FullHttpRequest r = super.get();
		return r.setUri(uri());
	}

	public String uri() {
		if (PRIVATE_TYPE.equals(messageType)) {
			return SEND_PRIVATE_MSG;
		} else if (GROUP_TYPE.equals(messageType)) {
			return SEND_GROUP_MSG;
		} else if (DISCUSS_TYPE.equals(messageType)) {
			return SEND_DISCUSS_MSG;
		} else {
			return SEND_MSG;
		}
	}

	public SendMsgRequest() {
		super();
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public long getDiscussId() {
		return discussId;
	}

	public void setDiscussId(long discussId) {
		this.discussId = discussId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isAutoEscape() {
		return autoEscape;
	}

	public void setAutoEscape(boolean autoEscape) {
		this.autoEscape = autoEscape;
	}

	public static class ResponseData implements CoolQHttpRequest.ResponseData {
		/**
		 * 
		 */
		private static final long serialVersionUID = -760630851327866137L;
		private int messageId;

		public int getMessageId() {
			return messageId;
		}

		public void setMessageId(int messageId) {
			this.messageId = messageId;
		}

	}

	@SuppressWarnings("rawtypes")
	@Override
	public java.lang.Class<? extends CoolQHttpRequest.Response> getResponseClass() {
		return SendMsgRequest.Response.class;

	}

	public static class Response extends CoolQHttpRequest.Response<ResponseData> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 5524102213422902291L;

	}
}
