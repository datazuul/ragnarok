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
package com.myslek.webmail.impl;

import java.util.ArrayList;
import java.util.Collection;

import javax.mail.FetchProfile;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;
import javax.mail.UIDFolder;

import com.myslek.webmail.api.AbstractMailSession;
import com.myslek.webmail.api.MailSessionException;
import com.myslek.webmail.api.MessageConverter;
import com.myslek.webmail.api.MessageFilter;
import com.myslek.webmail.domain.MailBox;
import com.myslek.webmail.domain.MailMessage;
import com.sun.mail.pop3.POP3Folder;

public class Pop3MailSession extends AbstractMailSession {

	public static final String DEFAULT_FOLDER = "INBOX";

	public Pop3MailSession() {
		setMessageConverter(new DefaultMessageConverter());
	}

	public Pop3MailSession(MessageConverter messageConverter) {
		setMessageConverter(messageConverter);
	}

	public Collection<MailMessage> fetchMessages(MailBox mailBox,
			Collection<String> uids, MessageFilter filter)
			throws MailSessionException {
		Store store = null;
		POP3Folder folder = null;
		Collection<MailMessage> mailMessages = new ArrayList<MailMessage>();
		try {
			store = getStore(mailBox.getMailStore());
			folder = (POP3Folder) store.getFolder(DEFAULT_FOLDER);
			folder.open(Folder.READ_ONLY);

			FetchProfile profile = new FetchProfile();
			profile.add(UIDFolder.FetchProfileItem.UID);

			Message[] messages = folder.getMessages();
			folder.fetch(messages, profile);

			for (int i = 0; i < messages.length; i++) {
				String uid = folder.getUID(messages[i]);
				if (isNewMessage(uids, uid)) {
					Message message = folder.getMessage(i + 1);
					if (filter == null || filter.accept(message)) {
						MailMessage mailMessage = 
							getMessageConverter().fromMessage(message);
						mailMessage.setUid(uid);
						mailMessage.setFolder(mailBox.getInbox());
						mailMessages.add(mailMessage);
					}
				}
			}
		} catch (MessagingException e) {
			throw new MailSessionException(e);
		} finally {
			if (folder != null) {
				try {
					folder.close(true);
				} catch (MessagingException e) {
					// Note that the service is closed even if this method
					// terminates abnormally by throwing a MessagingException
					// TODO: log the exception with 'warn' severity
				}
			}

			if (store != null) {
				try {
					store.close();
				} catch (MessagingException e) {
					// Note that the service is closed even if this method
					// terminates abnormally by throwing a MessagingException
					// TODO: log the exception with 'warn' severity
				}
			}
		}
		return mailMessages;
	}

	private boolean isNewMessage(Collection<String> uids, String uid) throws MailSessionException {
		return !uids.contains(uid);
	}
}
