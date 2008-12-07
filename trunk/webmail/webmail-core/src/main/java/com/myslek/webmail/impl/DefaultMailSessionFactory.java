package com.myslek.webmail.impl;

import com.myslek.webmail.api.MailSession;
import com.myslek.webmail.api.MailSessionFactory;

public class DefaultMailSessionFactory implements MailSessionFactory {

	private MailSession sharableMailSession = new DefaultMailSession();

	@Override
	public MailSession createMailSession() {
		return sharableMailSession;
	}

}
