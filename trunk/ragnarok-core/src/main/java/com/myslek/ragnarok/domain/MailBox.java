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
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.myslek.ragnarok.mail.contenthandler.MailboxConfigurationException;

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

	/** The mail store. */
	private MailServer mailStore;

	/** The mail transport. */
	private MailServer mailTransport;

	/** The default mail box. */
	private boolean defaultMailBox;

	/** The folders. */
	private Collection<MailFolder> folders = new ArrayList<MailFolder>();

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

	/**
	 * Gets the folders.
	 * 
	 * @return the folders
	 */
	public Collection<MailFolder> getFolders() {
		return folders;
	}

	/**
	 * Sets the folders.
	 * 
	 * @param folders
	 *            the new folders
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "MAILBOX_ID")
	public void setFolders(Collection<MailFolder> folders) {
		this.folders = folders;
	}

	public void addFolder(MailFolder folder) {
		this.folders.add(folder);
	}

	/**
	 * Gets the inbox.
	 * 
	 * @return the inbox
	 */
	@Transient
	public MailFolder getInbox() {
		for (MailFolder folder : folders) {
			if (folder.isInbox()) {
				return folder;
			}
		}
		throw new MailboxConfigurationException("Unable to find 'inbox' folder for mailbox ["
				+ mailStore.getProtocol() + "://" + mailStore.getUsername() + "@"
				+ mailStore.getHostname() + "]");
	}
}
