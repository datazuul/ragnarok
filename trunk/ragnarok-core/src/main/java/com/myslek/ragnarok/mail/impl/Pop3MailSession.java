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
package com.myslek.ragnarok.mail.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;
import javax.mail.UIDFolder;

import com.myslek.ragnarok.domain.MailBox;
import com.myslek.ragnarok.domain.MailFolder;
import com.myslek.ragnarok.domain.MailMessage;
import com.myslek.ragnarok.mail.AbstractMailSession;
import com.myslek.ragnarok.mail.MailSessionException;
import com.myslek.ragnarok.mail.MessageConverter;
import com.myslek.ragnarok.mail.MessageFilter;
import com.sun.mail.pop3.POP3Folder;

// TODO: Auto-generated Javadoc
/**
 * The Class Pop3MailSession.
 */
public class Pop3MailSession extends AbstractMailSession {

	/** The Constant DEFAULT_FOLDER. */
	public static final String DEFAULT_FOLDER = "INBOX";

	/**
	 * Instantiates a new pop3 mail session.
	 */
	public Pop3MailSession() {
		setMessageConverter(new DefaultMessageConverter());
	}

	/**
	 * Instantiates a new pop3 mail session.
	 * 
	 * @param messageConverter
	 *            the message converter
	 */
	public Pop3MailSession(MessageConverter messageConverter) {
		setMessageConverter(messageConverter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.myslek.webmail.api.MailSession#fetchMessages(com.myslek.webmail.domain
	 * .MailBox, java.util.Collection, com.myslek.webmail.api.MessageFilter)
	 */
	public List<MailMessage> fetchMessages(MailBox mailBox, Collection<String> uids,
			MessageFilter filter, boolean expunge) throws MailSessionException {
		Store store = null;
		POP3Folder folder = null;
		List<MailMessage> mailMessages = new ArrayList<MailMessage>();
		try {
			store = getStore(mailBox.getMailStore());
			if (!(store.getFolder(DEFAULT_FOLDER) instanceof POP3Folder)) {
				throw new MailSessionException(
						"POP3Folder is not supported by your javamail provider.");
			}
			folder = (POP3Folder) store.getFolder(DEFAULT_FOLDER);
			folder.open(Folder.READ_WRITE);

			Message[] messages = prefetchMessages(folder);

			for (int i = 0; i < messages.length; i++) {
				String uid = folder.getUID(messages[i]);
				if (isNewMessage(uids, uid)) {
					Message message = folder.getMessage(i + 1);
					if (filter == null || filter.accept(message)) {
						mailMessages.add(convertMessage(message, uid, mailBox.getInbox()));

						if (expunge) {
							message.setFlag(Flags.Flag.DELETED, true);
						}
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

	private Message[] prefetchMessages(Folder folder) throws MailSessionException {
		try {
			FetchProfile profile = new FetchProfile();
			profile.add(UIDFolder.FetchProfileItem.UID);

			Message[] messages = folder.getMessages();
			folder.fetch(messages, profile);

			return messages;
		} catch (MessagingException e) {
			throw new MailSessionException(e);
		}
	}

	/**
	 * Convert message.
	 * 
	 * @param message
	 *            the message
	 * @param uid
	 *            the uid
	 * @param inbox
	 *            the inbox
	 * 
	 * @return the mail message
	 * 
	 * @throws MailSessionException
	 *             the mail session exception
	 */
	private MailMessage convertMessage(Message message, String uid, MailFolder inbox)
			throws MailSessionException {
		MailMessage mailMessage = getMessageConverter().fromMessage(message);
		mailMessage.setUid(uid);
		mailMessage.setFolder(inbox);

		return mailMessage;
	}

	public void expungeMessages(MailBox mailBox, Collection<String> uids)
			throws MailSessionException {
		Store store = null;
		POP3Folder folder = null;
		try {
			store = getStore(mailBox.getMailStore());
			if (!(store.getFolder(DEFAULT_FOLDER) instanceof POP3Folder)) {
				throw new MailSessionException(
						"POP3Folder is not supported by your javamail provider.");
			}
			folder = (POP3Folder) store.getFolder(DEFAULT_FOLDER);
			folder.open(Folder.READ_WRITE);

			Message[] messages = prefetchMessages(folder);
			
			for (int i = 0; i < messages.length; i++) {
				String uid = folder.getUID(messages[i]);
				if (uids == null || uids.isEmpty() || uids.contains(uid)) {
					Message message = folder.getMessage(i + 1);
					message.setFlag(Flags.Flag.DELETED, true);
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
	}

	/**
	 * Checks if is new message.
	 * 
	 * @param uids
	 *            the uids
	 * @param uid
	 *            the uid
	 * 
	 * @return true, if is new message
	 * 
	 * @throws MailSessionException
	 *             the mail session exception
	 */
	private boolean isNewMessage(Collection<String> uids, String uid) throws MailSessionException {
		return !uids.contains(uid);
	}
}
