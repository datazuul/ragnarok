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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The Class MailFolder.
 */
@Entity
@Table(name="RAG_FOLDER")
public class MailFolder implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The id. */
	private Long id;
	
	private String name;
	
	private MailFolderType type;
	
	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	@Id @GeneratedValue
	@Column(name="ID")
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

	@Column(name="FOLDER_NAME", nullable=false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Enumerated(EnumType.STRING)
	@Column(name="FOLDER_TYPE", nullable = false)
	public MailFolderType getType() {
		return type;
	}

	public void setType(MailFolderType type) {
		this.type = type;
	}
}
