package com.myslek.webmail.service;

import java.util.Collection;

import javax.ejb.Local;

import com.myslek.webmail.api.MessageFilter;
import com.myslek.webmail.domain.MailBox;
import com.myslek.webmail.domain.MailMessage;

@Local
public interface MailSessionService {
	
	public Collection<MailMessage> fetchMessages(MailBox mailBox, Collection<String> uids, MessageFilter filter);
}
