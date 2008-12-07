package com.myslek.webmail.impl;

import com.myslek.webmail.api.MailSession;
import com.myslek.webmail.api.MailSessionFactory;

//TODO: cache the mail session instance
public class DefaultMailSessionFactory implements MailSessionFactory {

	@Override
	public MailSession createMailSession() {
		return new DefaultMailSession();
	}

}
