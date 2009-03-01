package com.myslek.ragnarok.service;

import java.util.Collection;

import javax.ejb.Local;

import com.myslek.ragnarok.api.MessageFilter;
import com.myslek.ragnarok.domain.MailBox;
import com.myslek.ragnarok.domain.MailMessage;

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
