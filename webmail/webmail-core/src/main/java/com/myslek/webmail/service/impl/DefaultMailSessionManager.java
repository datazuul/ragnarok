package com.myslek.webmail.service.impl;

import java.util.Collection;

import com.myslek.webmail.api.MailSession;
import com.myslek.webmail.api.MessageFilter;
import com.myslek.webmail.domain.MailBox;
import com.myslek.webmail.domain.MailMessage;
import com.myslek.webmail.manager.MailSessionManager;
import com.myslek.webmail.manager.MailStorageManager;

public class DefaultMailSessionManager implements MailSessionManager {
	
	private MailSession mailSession;
	
	private MailStorageManager storageManager;

	public MailSession getMailSession() {
		return mailSession;
	}

	public void setMailSession(MailSession mailSession) {
		this.mailSession = mailSession;
	}

	public MailStorageManager getStorageManager() {
		return storageManager;
	}

	public void setStorageManager(MailStorageManager storageManager) {
		this.storageManager = storageManager;
	}

	public Collection<MailMessage> fetchAndStoreMessages(MailBox mailBox,
			MessageFilter filter) {
		
		Collection<MailMessage> messages = getMailSession().fetchMessages(
				mailBox.getMailStore(), filter);
		
		getStorageManager().storeMessages(messages);
		
		return messages;
	}
}
