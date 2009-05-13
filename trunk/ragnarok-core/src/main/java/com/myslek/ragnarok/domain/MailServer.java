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

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * The Class MailServer.
 */
@Embeddable
public class MailServer implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The username. */
	private String username;

	/** The password. */
	private String password;

	/** The host. */
	private String hostname;

	/** The protocol. */
	private MailServerProtocol protocol;

	/**
	 * Gets the username.
	 * 
	 * @return the username
	 */
	@Column(name = "USERNAME", nullable = false, length=20)
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username.
	 * 
	 * @param username
	 *            the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the password.
	 * 
	 * @return the password
	 */
	@Column(name = "PASSWORD", nullable = false, length=20)
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 * 
	 * @param password
	 *            the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the host.
	 * 
	 * @return the host
	 */
	@Column(name = "HOSTNAME", length=50)
	public String getHostname() {
		return hostname;
	}

	/**
	 * Sets the host.
	 * 
	 * @param hostname
	 *            the new host
	 */
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "PROTOCOL", nullable = false, length=10)
	public MailServerProtocol getProtocol() {
		return protocol;
	}

	public void setProtocol(MailServerProtocol protocol) {
		this.protocol = protocol;
	}
}
