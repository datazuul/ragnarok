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
package com.myslek.ragnarok.domain;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class MailFolder.
 */
public class MailFolder implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The id. */
	private Long id;
	
	/** The token. */
	private String token;
	
	/** The mail box. */
	private MailBox mailBox;
	
	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * Sets the id.
	 * 
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Gets the token.
	 * 
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * Sets the token.
	 * 
	 * @param token the new token
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * Gets the mail box.
	 * 
	 * @return the mail box
	 */
	public MailBox getMailBox() {
		return mailBox;
	}
	
	/**
	 * Sets the mail box.
	 * 
	 * @param mailBox the new mail box
	 */
	public void setMailBox(MailBox mailBox) {
		this.mailBox = mailBox;
	}
}
