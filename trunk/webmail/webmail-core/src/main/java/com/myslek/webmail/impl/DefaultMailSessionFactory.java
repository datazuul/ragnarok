package com.myslek.webmail.impl;

import java.util.HashMap;
import java.util.Map;

import com.myslek.webmail.api.MailSession;
import com.myslek.webmail.api.MailSessionFactory;
import com.myslek.webmail.domain.MailBox;

public class DefaultMailSessionFactory implements MailSessionFactory {
	
	private Map<String, MailSession> storeToSession = new HashMap<String, MailSession>();
	
	public static final String POP3_STORE = "pop3";
	public static final String IMAP_STORE = "imap";
	
	public DefaultMailSessionFactory() {
		storeToSession.put(POP3_STORE, new Pop3MailSession());
	}

	public MailSession createMailSession(MailBox mailBox) {
		MailSession session = storeToSession.get(mailBox.getMailStore().getProtocol());
		if (session == null) {
			//throw exception -> unsupported mail store
		}
		return session;
	}
}
