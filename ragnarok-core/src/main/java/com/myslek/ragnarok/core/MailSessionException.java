/*
 * Copyright 2009 Rafal Myslek 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0 
 *     
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License.     
 */
package com.myslek.ragnarok.core;

// TODO: Auto-generated Javadoc
/**
 * The Class MailSessionException.
 */
public class MailSessionException extends SystemException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new mail session exception.
	 */
	public MailSessionException() {
		super();
	}

	/**
	 * Instantiates a new mail session exception.
	 * 
	 * @param message the message
	 * @param cause the cause
	 */
	public MailSessionException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new mail session exception.
	 * 
	 * @param message the message
	 */
	public MailSessionException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new mail session exception.
	 * 
	 * @param cause the cause
	 */
	public MailSessionException(Throwable cause) {
		super(cause);
	}
}
