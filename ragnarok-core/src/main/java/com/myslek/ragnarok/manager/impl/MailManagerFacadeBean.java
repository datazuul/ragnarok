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
package com.myslek.ragnarok.manager.impl;

import java.util.Collection;

import javax.ejb.Stateless;

import com.myslek.ragnarok.core.MessageFilter;
import com.myslek.ragnarok.domain.MailBox;
import com.myslek.ragnarok.domain.MailMessage;
import com.myslek.ragnarok.manager.MailManagerFacade;
import com.myslek.ragnarok.manager.MailSessionManager;
import com.myslek.ragnarok.manager.MailStoreManager;

@Stateless
public class MailManagerFacadeBean implements MailManagerFacade {

	private MailSessionManager mailSessionManager;

	private MailStoreManager mailStoreManager;

	public void fetchAndStoreMessages(MailBox mailBox, MessageFilter filter) {
		Collection<String> uids = getMailStoreManager().getUids(mailBox);

		Collection<MailMessage> messages = 
			getMailSessionManager().fetchMessages(mailBox, uids, filter);

		getMailStoreManager().storeMessages(messages);
	}

	public MailSessionManager getMailSessionManager() {
		return mailSessionManager;
	}

	public void setMailSessionManager(MailSessionManager mailSessionManager) {
		this.mailSessionManager = mailSessionManager;
	}

	public MailStoreManager getMailStoreManager() {
		return mailStoreManager;
	}

	public void setMailStoreManager(MailStoreManager mailStoreManager) {
		this.mailStoreManager = mailStoreManager;
	}
}
