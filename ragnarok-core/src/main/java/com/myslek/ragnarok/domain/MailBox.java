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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * The Class MailBox.
 */
@Entity
@Table(name = "RAG_MAILBOX")
public class MailBox implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Long id;
	
	private String token;
	
	private MailUser user;

	/** The mail store. */
	private MailServer mailStore;

	/** The mail transport. */
	private MailServer mailTransport;

	/** The default mail box. */
	private boolean defaultMailBox;

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
	
	@Column(name="TOKEN", nullable=false, length=10)
	public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name = "USER_ID")
	public MailUser getUser() {
        return user;
    }

    public void setUser(MailUser user) {
        this.user = user;
    }

    /**
	 * Gets the mail store.
	 * 
	 * @return the mail store
	 */
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "IN_SERVER_ID")
	public MailServer getMailStore() {
		return mailStore;
	}

	/**
	 * Sets the mail store.
	 * 
	 * @param mailStore
	 *            the new mail store
	 */
	public void setMailStore(MailServer mailStore) {
		this.mailStore = mailStore;
	}

	/**
	 * Gets the mail transport.
	 * 
	 * @return the mail transport
	 */
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "OUT_SERVER_ID")
	public MailServer getMailTransport() {
		return mailTransport;
	}

	/**
	 * Sets the mail transport.
	 * 
	 * @param mailTransport
	 *            the new mail transport
	 */
	public void setMailTransport(MailServer mailTransport) {
		this.mailTransport = mailTransport;
	}

	/**
	 * Checks if is default mail box.
	 * 
	 * @return true, if is default mail box
	 */
	@Column(name = "DEFAULT_MAILBOX")
	public boolean isDefaultMailBox() {
		return defaultMailBox;
	}

	/**
	 * Sets the default mail box.
	 * 
	 * @param defaultMailBox
	 *            the new default mail box
	 */
	public void setDefaultMailBox(boolean defaultMailBox) {
		this.defaultMailBox = defaultMailBox;
	}
}
