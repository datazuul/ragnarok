package com.myslek.webmail.service;

import java.util.Collection;

import com.myslek.webmail.domain.MailMessage;

public interface MailStoreService {
	
	public void storeMessages(Collection<MailMessage> messages);
}
