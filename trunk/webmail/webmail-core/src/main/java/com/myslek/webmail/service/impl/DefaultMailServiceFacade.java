package com.myslek.webmail.service.impl;

import java.util.Collection;

import javax.ejb.Stateless;

import com.myslek.webmail.api.MessageFilter;
import com.myslek.webmail.domain.MailBox;
import com.myslek.webmail.domain.MailMessage;
import com.myslek.webmail.service.MailServiceFacade;
import com.myslek.webmail.service.MailSessionService;
import com.myslek.webmail.service.MailStoreService;

@Stateless
public class DefaultMailServiceFacade implements MailServiceFacade {

	private MailSessionService mailSessionService;

	private MailStoreService mailStoreService;

	public void fetchAndStoreMessages(MailBox mailBox, MessageFilter filter) {
		Collection<String> uids = getMailStoreService().getUids(mailBox);

		Collection<MailMessage> messages = 
			getMailSessionService().fetchMessages(mailBox, uids, filter);

		getMailStoreService().storeMessages(messages);
	}

	public MailSessionService getMailSessionService() {
		return mailSessionService;
	}

	public MailStoreService getMailStoreService() {
		return mailStoreService;
	}

	public void setMailSessionService(MailSessionService mailSessionService) {
		this.mailSessionService = mailSessionService;
	}

	public void setMailStoreService(MailStoreService mailStoreService) {
		this.mailStoreService = mailStoreService;
	}
}
