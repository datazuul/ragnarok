package com.myslek.ragnarok.manager;

import java.util.Collection;

import javax.ejb.Local;

import com.myslek.ragnarok.domain.MailBox;
import com.myslek.ragnarok.domain.MailMessage;

@Local
public interface MailStoreManager {
	
	/**
	 * Store messages.
	 * 
	 * @param messages the messages
	 */
	public void storeMessages(Collection<MailMessage> messages);
	
	/**
	 * Gets the uids.
	 * 
	 * @param mailBox the mail box
	 * 
	 * @return the uids
	 */
	public Collection<String> getUids(MailBox mailBox);
}
