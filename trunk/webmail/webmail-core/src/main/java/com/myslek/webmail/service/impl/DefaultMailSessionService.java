package com.myslek.webmail.service.impl;

import java.util.Collection;

import javax.ejb.Stateless;

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
			MessageFilter filter) {

		Collection<MailMessage> messages = getMailSessionFactory()
				.createMailSession().fetchMessages(mailBox.getMailStore(),
						filter);

		return messages;
	}
}
