package com.myslek.ragnarok.manager;

import javax.ejb.Local;

import com.myslek.ragnarok.api.MessageFilter;
import com.myslek.ragnarok.domain.MailBox;


// TODO: Auto-generated Javadoc
/**
 * Facade interface that provides one entry point to all the messaging components
 * (i.e. connectivity, storage)
 */
@Local
public interface MailManagerFacade {
	
	/**
	 * Fetch and store messages.
	 * 
	 * @param mailBox the mail box
	 * @param filter the filter
	 */
	void fetchAndStoreMessages(MailBox mailBox, MessageFilter filter);
}
