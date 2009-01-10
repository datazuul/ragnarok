package com.myslek.webmail.api;

import java.util.Collection;

import javax.mail.Session;

import com.myslek.webmail.domain.MailBox;
import com.myslek.webmail.domain.MailMessage;

public interface MailSession {
	
	public Session getSession();

	public void sendMessage(MailBox mailBox, MailMessage mailMessage)
			throws MailSessionException;

	public Collection<MailMessage> fetchMessages(MailBox mailBox, Collection<String> uids,
			MessageFilter filter) throws MailSessionException;

}
