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

public class MailAddress implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String address;
	private String personal;
	private MailAddressType type;
	
	public MailAddress() {
		
	}
	
	public MailAddress(String address, String personal) {
		this.address = address;
		this.personal = personal;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getPersonal() {
		return personal;
	}
	
	public void setPersonal(String personal) {
		this.personal = personal;
	}
	
	public MailAddressType getType() {
		return type;
	}
	
	public void setType(MailAddressType type) {
		this.type = type;
	}
}
