package com.myslek.webmail.service.impl;

import java.util.Collection;

import javax.ejb.Stateless;

import com.myslek.webmail.api.MailSession;
import com.myslek.webmail.api.MailSessionFactory;
import com.myslek.webmail.api.MessageFilter;
import com.myslek.webmail.domain.MailBox;
import com.myslek.webmail.domain.MailMessage;
import com.myslek.webmail.service.MailSessionService;

@Stateless
public class DefaultMailSessionService implements MailSessionService {

	private MailSessionFactory mailSessionFactory;

	public MailSessionFactory getMailSessionFactory() {
		return mailSessionFactory;
	}

	public void setMailSessionFactory(MailSessionFactory mailSessionFactory) {
		this.mailSessionFactory = mailSessionFactory;
	}

	public Collection<MailMessage> fetchMessages(MailBox mailBox,
			Collection<String> uids, MessageFilter filter) {
		MailSession mailSession = getMailSessionFactory().createMailSession(
				mailBox);

		Collection<MailMessage> messages = mailSession.fetchMessages(mailBox,
				uids, filter);
		return messages;
	}
}
