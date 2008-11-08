package com.myslek.webmail.api;

public class MailSessionException extends SystemException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MailSessionException() {
		super();
	}

	public MailSessionException(String message, Throwable cause) {
		super(message, cause);
	}

	public MailSessionException(String message) {
		super(message);
	}

	public MailSessionException(Throwable cause) {
		super(cause);
	}
}
