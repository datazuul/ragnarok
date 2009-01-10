package com.myslek.webmail.service;

import java.util.Collection;

import javax.ejb.Local;

import com.myslek.webmail.domain.MailBox;
import com.myslek.webmail.domain.MailMessage;

@Local
public interface MailStoreService {
	
	public void storeMessages(Collection<MailMessage> messages);
	
	public Collection<String> getUids(MailBox mailBox);
}
