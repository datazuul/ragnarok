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
				if (isNew(uids, uid)) {
					Message message = folder.getMessage(i + 1);
					if (filter == null || filter.accept(message)) {
						MailMessage mailMessage = 
							getMessageConverter().fromMessage(message);
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

	private boolean isNew(Collection<String> uids, String uid) throws MailSessionException {
		return !uids.contains(uid);
	}
}
