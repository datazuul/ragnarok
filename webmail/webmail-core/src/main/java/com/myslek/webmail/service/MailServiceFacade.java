package com.myslek.webmail.service;

import javax.ejb.Local;


/**
 * Facade interface that provides one entry point to all the messaging components 
 * (i.e. connectivity, storage)
 *
 */
@Local
public interface MailServiceFacade {
	
	MailSessionService getMailSessionService();
	
	MailStoreService getMailStoreService();
}
