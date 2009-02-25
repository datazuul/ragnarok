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
package com.myslek.webmail.domain;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class MailServer.
 */
public class MailServer implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The id. */
	private Long id;
	
	/** The mail box. */
	private MailBox mailBox;
	
	/** The username. */
	private String username;
	
	/** The password. */
	private String password;
	
	/** The host. */
	private String host;
	
	/** The protocol. */
	private String protocol;
	
	/** The default folder. */
	private String defaultFolder;
	
	/** The type. */
	private MailServerType type;
	
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
	
	/**
	 * Gets the username.
	 * 
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Sets the username.
	 * 
	 * @param username the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Gets the password.
	 * 
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Sets the password.
	 * 
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Gets the host.
	 * 
	 * @return the host
	 */
	public String getHost() {
		return host;
	}
	
	/**
	 * Sets the host.
	 * 
	 * @param host the new host
	 */
	public void setHost(String host) {
		this.host = host;
	}
	
	/**
	 * Gets the protocol.
	 * 
	 * @return the protocol
	 */
	public String getProtocol() {
		return protocol;
	}
	
	/**
	 * Sets the protocol.
	 * 
	 * @param protocol the new protocol
	 */
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	/**
	 * Gets the default folder.
	 * 
	 * @return the default folder
	 */
	public String getDefaultFolder() {
		return defaultFolder;
	}
	
	/**
	 * Sets the default folder.
	 * 
	 * @param defaultFolder the new default folder
	 */
	public void setDefaultFolder(String defaultFolder) {
		this.defaultFolder = defaultFolder;
	}
	
	/**
	 * Gets the type.
	 * 
	 * @return the type
	 */
	public MailServerType getType() {
		return type;
	}
	
	/**
	 * Sets the type.
	 * 
	 * @param type the new type
	 */
	public void setType(MailServerType type) {
		this.type = type;
	}
}
