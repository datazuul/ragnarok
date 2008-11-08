package com.myslek.webmail.api;

public class MessageConversionException extends SystemException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MessageConversionException() {
		super();
	}

	public MessageConversionException(String message, Throwable cause) {
		super(message, cause);
	}

	public MessageConversionException(String message) {
		super(message);
	}

	public MessageConversionException(Throwable cause) {
		super(cause);
	}
}
