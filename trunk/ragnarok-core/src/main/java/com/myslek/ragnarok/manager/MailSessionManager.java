package com.myslek.ragnarok.manager;

import java.util.Collection;

import javax.ejb.Local;

import com.myslek.ragnarok.core.MessageFilter;
import com.myslek.ragnarok.domain.MailBox;
import com.myslek.ragnarok.domain.MailMessage;

@Local
public interface MailSessionManager {
	
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
