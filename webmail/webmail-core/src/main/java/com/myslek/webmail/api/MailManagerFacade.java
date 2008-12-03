package com.myslek.webmail.api;

import com.myslek.webmail.manager.MailSessionManager;
import com.myslek.webmail.manager.MailStorageManager;

/**
 * Facade interface that provides one entry point to all the messaging components 
 * (i.e. connectivity, storage)
 *
 */
public interface MailManagerFacade {
	
	MailSessionManager getMailSessionManager();
	
	MailStorageManager getMailStorageManager();
}
