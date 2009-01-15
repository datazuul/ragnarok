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

public class MailBox implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private MailUser mailUser;
	private MailServer mailStore;
	private MailServer mailTransport;
	private boolean defaultMailBox;
	private Collection<MailFolder> folders = new ArrayList<MailFolder>();
	private Collection<MailMessageFilter> messageFilters = 
		new ArrayList<MailMessageFilter>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MailUser getMailUser() {
		return mailUser;
	}

	public void setMailUser(MailUser mailUser) {
		this.mailUser = mailUser;
	}

	public MailServer getMailStore() {
		return mailStore;
	}

	public void setMailStore(MailServer mailStore) {
		this.mailStore = mailStore;
	}

	public MailServer getMailTransport() {
		return mailTransport;
	}

	public void setMailTransport(MailServer mailTransport) {
		this.mailTransport = mailTransport;
	}

	public boolean isDefaultMailBox() {
		return defaultMailBox;
	}

	public void setDefaultMailBox(boolean defaultMailBox) {
		this.defaultMailBox = defaultMailBox;
	}

	public Collection<MailFolder> getFolders() {
		return folders;
	}

	public void setFolders(Collection<MailFolder> folders) {
		this.folders = folders;
	}

	public Collection<MailMessageFilter> getMessageFilters() {
		return messageFilters;
	}

	public void setMessageFilters(Collection<MailMessageFilter> messageFilters) {
		this.messageFilters = messageFilters;
	}
	
	//TODO: implement me!
	public MailFolder getInbox() {
		return null;
	}
}
