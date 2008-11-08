package com.myslek.webmail.manager;

import java.util.Collection;

import com.myslek.webmail.domain.MailMessage;

public interface MailStorageManager {
	
	public void storeMessages(Collection<MailMessage> messages);
}
