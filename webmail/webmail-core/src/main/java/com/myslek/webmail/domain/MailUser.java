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
import java.util.ArrayList;
import java.util.Collection;

public class MailUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String token;
	private String username;
	private String password;
	private Collection<MailBox> mailBoxes = new ArrayList<MailBox>();
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Collection<MailBox> getMailBoxes() {
		return mailBoxes;
	}
	
	public void setMailBoxes(Collection<MailBox> mailBoxes) {
		this.mailBoxes = mailBoxes;
	}
}
