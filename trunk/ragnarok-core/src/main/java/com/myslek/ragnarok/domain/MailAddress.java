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
 * The Class MailAddress.
 */
public class MailAddress implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The id. */
	private Long id;
	
	/** The address. */
	private String address;
	
	/** The personal. */
	private String personal;
	
	/** The type. */
	private MailAddressType type;
	
	/**
	 * Instantiates a new mail address.
	 */
	public MailAddress() {
		
	}
	
	/**
	 * Instantiates a new mail address.
	 * 
	 * @param address the address
	 * @param personal the personal
	 */
	public MailAddress(String address, String personal) {
		this.address = address;
		this.personal = personal;
	}
	
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
	 * Gets the address.
	 * 
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * Sets the address.
	 * 
	 * @param address the new address
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 * Gets the personal.
	 * 
	 * @return the personal
	 */
	public String getPersonal() {
		return personal;
	}
	
	/**
	 * Sets the personal.
	 * 
	 * @param personal the new personal
	 */
	public void setPersonal(String personal) {
		this.personal = personal;
	}
	
	/**
	 * Gets the type.
	 * 
	 * @return the type
	 */
	public MailAddressType getType() {
		return type;
	}
	
	/**
	 * Sets the type.
	 * 
	 * @param type the new type
	 */
	public void setType(MailAddressType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		StringBuffer ret = new StringBuffer();
		if (personal != null && personal.trim().length() > 0) {
			ret.append(personal + " ");
		}
		ret.append("<" + address + ">");
		return ret.toString();
	}
}
