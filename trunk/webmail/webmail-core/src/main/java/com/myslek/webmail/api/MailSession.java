package com.myslek.webmail.api;

import java.util.Collection;

import com.myslek.webmail.domain.MailMessage;
import com.myslek.webmail.domain.MailServer;

public interface MailSession {

	public void sendMessage(MailServer mailServer, MailMessage mailMessage)
			throws MailSessionException;

	public Collection<MailMessage> fetchMessages(MailServer mailServer,
			MessageFilter filter) throws MailSessionException;

}
