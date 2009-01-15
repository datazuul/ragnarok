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

public class MailHeader implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private MailPart mailPart;
	private String name;
	private String value;
	
	public MailHeader() {
		
	}
	
	public MailHeader(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public MailPart getMailPart() {
		return mailPart;
	}

	public void setMailPart(MailPart mailPart) {
		this.mailPart = mailPart;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
