/*
 * Copyright 2009 Rafal Myslek 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0 
 *     
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License.     
 */
package com.myslek.webmail.service.impl;

import java.util.Collection;

import javax.ejb.Stateless;

import com.myslek.webmail.api.MessageFilter;
import com.myslek.webmail.domain.MailBox;
import com.myslek.webmail.domain.MailMessage;
import com.myslek.webmail.service.MailServiceFacade;
import com.myslek.webmail.service.MailSessionService;
import com.myslek.webmail.service.MailStoreService;

// TODO: Auto-generated Javadoc
/**
 * The Class DefaultMailServiceFacade.
 */
@Stateless
public class DefaultMailServiceFacade implements MailServiceFacade {

	/** The mail session service. */
	private MailSessionService mailSessionService;

	/** The mail store service. */
	private MailStoreService mailStoreService;

	/* (non-Javadoc)
	 * @see com.myslek.webmail.service.MailServiceFacade#fetchAndStoreMessages(com.myslek.webmail.domain.MailBox, com.myslek.webmail.api.MessageFilter)
	 */
	public void fetchAndStoreMessages(MailBox mailBox, MessageFilter filter) {
		Collection<String> uids = getMailStoreService().getUids(mailBox);

		Collection<MailMessage> messages = 
			getMailSessionService().fetchMessages(mailBox, uids, filter);

		getMailStoreService().storeMessages(messages);
	}

	/**
	 * Gets the mail session service.
	 * 
	 * @return the mail session service
	 */
	public MailSessionService getMailSessionService() {
		return mailSessionService;
	}

	/**
	 * Gets the mail store service.
	 * 
	 * @return the mail store service
	 */
	public MailStoreService getMailStoreService() {
		return mailStoreService;
	}

	/**
	 * Sets the mail session service.
	 * 
	 * @param mailSessionService the new mail session service
	 */
	public void setMailSessionService(MailSessionService mailSessionService) {
		this.mailSessionService = mailSessionService;
	}

	/**
	 * Sets the mail store service.
	 * 
	 * @param mailStoreService the new mail store service
	 */
	public void setMailStoreService(MailStoreService mailStoreService) {
		this.mailStoreService = mailStoreService;
	}
}
