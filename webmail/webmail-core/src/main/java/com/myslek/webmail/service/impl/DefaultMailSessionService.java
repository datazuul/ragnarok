package com.myslek.webmail.service.impl;

import java.util.Collection;

import javax.ejb.Stateless;

import com.myslek.webmail.api.MailSession;
import com.myslek.webmail.api.MessageFilter;
import com.myslek.webmail.domain.MailBox;
import com.myslek.webmail.domain.MailMessage;
import com.myslek.webmail.service.MailSessionService;

@Stateless
public class DefaultMailSessionService implements MailSessionService {

	private MailSession mailSession;

	public MailSession getMailSession() {
		return mailSession;
	}

	public void setMailSession(MailSession mailSession) {
		this.mailSession = mailSession;
	}

	public Collection<MailMessage> fetchMessages(MailBox mailBox,
			MessageFilter filter) {

		Collection<MailMessage> messages = getMailSession().fetchMessages(
				mailBox.getMailStore(), filter);

		return messages;
	}
}
