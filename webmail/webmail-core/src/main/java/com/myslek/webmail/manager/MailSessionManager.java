package com.myslek.webmail.manager;

import java.util.Collection;

import com.myslek.webmail.api.MessageFilter;
import com.myslek.webmail.domain.MailBox;
import com.myslek.webmail.domain.MailMessage;

public interface MailSessionManager {
	
	public Collection<MailMessage> fetchAndStoreMessages(MailBox mailBox, MessageFilter filter);
}
