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
	private Collection<MailMessageFilter> messageFilters = new ArrayList<MailMessageFilter>();

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

	public Collection<MailMessageFilter> getMessageFilters() {
		return messageFilters;
	}

	public void setMessageFilters(Collection<MailMessageFilter> messageFilters) {
		this.messageFilters = messageFilters;
	}
}
