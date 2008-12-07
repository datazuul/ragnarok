package com.myslek.webmail.service;


/**
 * Facade interface that provides one entry point to all the messaging components 
 * (i.e. connectivity, storage)
 *
 */
public interface MailServiceFacade {
	
	MailSessionService getMailSessionService();
	
	MailStoreService getMailStoreService();
}
