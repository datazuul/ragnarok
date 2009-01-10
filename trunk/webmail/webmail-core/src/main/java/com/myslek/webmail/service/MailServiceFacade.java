package com.myslek.webmail.service;

import javax.ejb.Local;

import com.myslek.webmail.api.MessageFilter;
import com.myslek.webmail.domain.MailBox;


/**
 * Facade interface that provides one entry point to all the messaging components 
 * (i.e. connectivity, storage)
 *
 */
@Local
public interface MailServiceFacade {
	
	void fetchAndStoreMessages(MailBox mailBox, MessageFilter filter);
}
