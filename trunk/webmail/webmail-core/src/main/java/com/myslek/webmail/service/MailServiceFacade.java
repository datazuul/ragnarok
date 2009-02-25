package com.myslek.webmail.service;

import javax.ejb.Local;

import com.myslek.webmail.api.MessageFilter;
import com.myslek.webmail.domain.MailBox;


// TODO: Auto-generated Javadoc
/**
 * Facade interface that provides one entry point to all the messaging components
 * (i.e. connectivity, storage)
 */
@Local
public interface MailServiceFacade {
	
	/**
	 * Fetch and store messages.
	 * 
	 * @param mailBox the mail box
	 * @param filter the filter
	 */
	void fetchAndStoreMessages(MailBox mailBox, MessageFilter filter);
}
