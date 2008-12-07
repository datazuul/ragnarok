package com.myslek.webmail.service;

import java.util.Collection;

import com.myslek.webmail.api.MessageFilter;
import com.myslek.webmail.domain.MailBox;
import com.myslek.webmail.domain.MailMessage;

public interface MailSessionService {
	
	public Collection<MailMessage> fetchMessages(MailBox mailBox, MessageFilter filter);
}
