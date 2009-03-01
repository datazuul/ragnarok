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

// TODO: Auto-generated Javadoc
/**
 * The Class MailBox.
 */
public class MailBox implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Long id;
	
	/** The mail user. */
	private MailUser mailUser;
	
	/** The mail store. */
	private MailServer mailStore;
	
	/** The mail transport. */
	private MailServer mailTransport;
	
	/** The default mail box. */
	private boolean defaultMailBox;
	
	/** The folders. */
	private Collection<MailFolder> folders = new ArrayList<MailFolder>();
	
	/** The message filters. */
	private Collection<MailMessageFilter> messageFilters = 
		new ArrayList<MailMessageFilter>();

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
	 * Gets the mail user.
	 * 
	 * @return the mail user
	 */
	public MailUser getMailUser() {
		return mailUser;
	}

	/**
	 * Sets the mail user.
	 * 
	 * @param mailUser the new mail user
	 */
	public void setMailUser(MailUser mailUser) {
		this.mailUser = mailUser;
	}

	/**
	 * Gets the mail store.
	 * 
	 * @return the mail store
	 */
	public MailServer getMailStore() {
		return mailStore;
	}

	/**
	 * Sets the mail store.
	 * 
	 * @param mailStore the new mail store
	 */
	public void setMailStore(MailServer mailStore) {
		this.mailStore = mailStore;
	}

	/**
	 * Gets the mail transport.
	 * 
	 * @return the mail transport
	 */
	public MailServer getMailTransport() {
		return mailTransport;
	}

	/**
	 * Sets the mail transport.
	 * 
	 * @param mailTransport the new mail transport
	 */
	public void setMailTransport(MailServer mailTransport) {
		this.mailTransport = mailTransport;
	}

	/**
	 * Checks if is default mail box.
	 * 
	 * @return true, if is default mail box
	 */
	public boolean isDefaultMailBox() {
		return defaultMailBox;
	}

	/**
	 * Sets the default mail box.
	 * 
	 * @param defaultMailBox the new default mail box
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
	 * @param folders the new folders
	 */
	public void setFolders(Collection<MailFolder> folders) {
		this.folders = folders;
	}

	/**
	 * Gets the message filters.
	 * 
	 * @return the message filters
	 */
	public Collection<MailMessageFilter> getMessageFilters() {
		return messageFilters;
	}

	/**
	 * Sets the message filters.
	 * 
	 * @param messageFilters the new message filters
	 */
	public void setMessageFilters(Collection<MailMessageFilter> messageFilters) {
		this.messageFilters = messageFilters;
	}
	
	//TODO: implement me!
	/**
	 * Gets the inbox.
	 * 
	 * @return the inbox
	 */
	public MailFolder getInbox() {
		return null;
	}
}
