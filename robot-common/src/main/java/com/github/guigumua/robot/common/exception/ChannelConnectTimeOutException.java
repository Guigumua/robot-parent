package com.github.guigumua.robot.common.exception;

public class ChannelConnectTimeOutException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6321891861845251217L;
	private final String message;

	public ChannelConnectTimeOutException(String msg) {
		this.message = msg;
	}
	public String getMessage() {
		return message;
	}

}
