package com.github.guigumua.robot.common.exception;

public class RespondTimeOutException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4457359539710201832L;
	private final String message;

	public RespondTimeOutException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}
	
}
