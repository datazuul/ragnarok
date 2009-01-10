package com.myslek.webmail.api;

import com.myslek.webmail.domain.MailBox;

public interface MailSessionFactory {
	
	MailSession createMailSession(MailBox mailBox);
}
