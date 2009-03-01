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
package com.myslek.ragnarok.api;

import java.util.Collection;

import javax.mail.Session;

import com.myslek.ragnarok.domain.MailBox;
import com.myslek.ragnarok.domain.MailMessage;

// TODO: Auto-generated Javadoc
/**
 * The Interface MailSession.
 */
public interface MailSession {
	
	/**
	 * Gets the session.
	 * 
	 * @return the session
	 */
	public Session getSession();

	/**
	 * Send message.
	 * 
	 * @param mailBox the mail box
	 * @param mailMessage the mail message
	 * 
	 * @throws MailSessionException the mail session exception
	 */
	public void sendMessage(MailBox mailBox, MailMessage mailMessage)
			throws MailSessionException;

	/**
	 * Fetch messages.
	 * 
	 * @param mailBox the mail box
	 * @param uids the uids
	 * @param filter the filter
	 * 
	 * @return the collection< mail message>
	 * 
	 * @throws MailSessionException the mail session exception
	 */
	public Collection<MailMessage> fetchMessages(MailBox mailBox, Collection<String> uids,
			MessageFilter filter) throws MailSessionException;

}
