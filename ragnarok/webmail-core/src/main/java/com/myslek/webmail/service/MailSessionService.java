package com.myslek.webmail.service;

import java.util.Collection;

import javax.ejb.Local;

import com.myslek.webmail.api.MessageFilter;
import com.myslek.webmail.domain.MailBox;
import com.myslek.webmail.domain.MailMessage;

// TODO: Auto-generated Javadoc
/**
 * The Interface MailSessionService.
 */
@Local
public interface MailSessionService {
	
	/**
	 * Fetch messages.
	 * 
	 * @param mailBox the mail box
	 * @param uids the uids
	 * @param filter the filter
	 * 
	 * @return the collection< mail message>
	 */
	public Collection<MailMessage> fetchMessages(MailBox mailBox, Collection<String> uids, MessageFilter filter);
}
