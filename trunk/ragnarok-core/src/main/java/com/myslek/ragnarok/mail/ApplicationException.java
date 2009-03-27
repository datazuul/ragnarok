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
package com.myslek.ragnarok.mail;

// TODO: Auto-generated Javadoc
/**
 * The Class ApplicationException.
 */
public class ApplicationException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new application exception.
	 */
	public ApplicationException() {
		super();
	}

	/**
	 * Instantiates a new application exception.
	 * 
	 * @param message the message
	 * @param cause the cause
	 */
	public ApplicationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new application exception.
	 * 
	 * @param message the message
	 */
	public ApplicationException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new application exception.
	 * 
	 * @param cause the cause
	 */
	public ApplicationException(Throwable cause) {
		super(cause);
	}
}
