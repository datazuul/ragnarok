package com.myslek.webmail.service.impl;

import java.util.Collection;

import javax.ejb.Stateless;

import com.myslek.webmail.domain.MailBox;
import com.myslek.webmail.domain.MailMessage;
import com.myslek.webmail.service.MailServiceFacade;
import com.myslek.webmail.service.MailSessionService;
import com.myslek.webmail.service.MailStoreService;

@Stateless
public class DefaultMailServiceFacade implements MailServiceFacade {

	private MailSessionService mailSessionService;

	private MailStoreService mailStoreService;

	@Override
	public void fetchAndStoreMessages(MailBox mailBox) {
		Collection<MailMessage> messages = getMailSessionService()
				.fetchMessages(mailBox, null);

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
