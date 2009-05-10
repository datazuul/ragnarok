package com.myslek.ragnarok.manager;

import java.util.Collection;
import java.util.List;

import javax.ejb.Local;

import com.myslek.ragnarok.domain.MailBox;
import com.myslek.ragnarok.domain.MailFolder;
import com.myslek.ragnarok.domain.MailMessage;
import com.myslek.ragnarok.domain.MailUser;
import com.myslek.ragnarok.persistence.ResultParams;

@Local
public interface MailStoreManager {
	
	/**
	 * Store messages.
	 * 
	 * @param messages the messages
	 */
	void storeMessages(Collection<MailMessage> messages);
	
	/**
	 * Gets the uids.
	 * 
	 * @param mailBox the mail box
	 * 
	 * @return the uids
	 */
	Collection<String> getUids(MailBox mailBox, MailFolder mailFolder);
	
	List<MailMessage> getMessages(MailUser user, String mailBoxToken, MailFolder folder,
            ResultParams params);
}
