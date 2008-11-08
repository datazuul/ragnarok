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
	
	private MailStorageManager storageService;

	public MailSession getMailConnector() {
		return mailSession;
	}

	public void setMailConnector(MailSession mailSession) {
		this.mailSession = mailSession;
	}

	public MailStorageManager getStorageService() {
		return storageService;
	}

	public void setStorageService(MailStorageManager storageService) {
		this.storageService = storageService;
	}

	public Collection<MailMessage> fetchAndStoreMessages(MailBox mailBox,
			MessageFilter filter) {
		
		Collection<MailMessage> messages = mailSession.fetchMessages(
				mailBox.getMailStore(), filter);
		
		storageService.storeMessages(messages);
		
		return messages;
	}
}
