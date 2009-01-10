package com.myslek.webmail.service.impl;

import java.util.Collection;

import javax.ejb.Stateless;

import com.myslek.webmail.domain.MailBox;
import com.myslek.webmail.domain.MailMessage;
import com.myslek.webmail.service.MailStoreService;

@Stateless
public class DefaultMailStoreService implements MailStoreService {

	public void storeMessages(Collection<MailMessage> messages) {
		
	}

	public Collection<String> getUids(MailBox mailBox) {
		return null;
	}
}
