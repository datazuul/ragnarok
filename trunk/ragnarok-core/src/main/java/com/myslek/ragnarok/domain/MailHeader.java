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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The Class MailHeader.
 */
@Entity
@Table(name = "RAG_HEADER")
public class MailHeader implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Long id;

	/** The mail part. */
	private MailPart mailPart;

	/** The name. */

	private String name;

	/** The value. */
	private String value;

	/**
	 * Instantiates a new mail header.
	 */
	public MailHeader() {

	}

	/**
	 * Instantiates a new mail header.
	 * 
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 */
	public MailHeader(String name, String value) {
		this.name = name;
		this.value = value;
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	@Id
	@GeneratedValue
	@Column(name = "ID")
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the mail part.
	 * 
	 * @return the mail part
	 */
	@ManyToOne
	@JoinColumn(name = "PART_ID")
	public MailPart getMailPart() {
		return mailPart;
	}

	/**
	 * Sets the mail part.
	 * 
	 * @param mailPart
	 *            the new mail part
	 */
	public void setMailPart(MailPart mailPart) {
		this.mailPart = mailPart;
	}

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	@Column(name = "HEADER_NAME", nullable = false, length = 50)
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 * 
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the value.
	 * 
	 * @return the value
	 */
	@Column(name = "HEADER_VALUE", length = 50)
	public String getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 * 
	 * @param value
	 *            the new value
	 */
	public void setValue(String value) {
		this.value = value;
	}
}
