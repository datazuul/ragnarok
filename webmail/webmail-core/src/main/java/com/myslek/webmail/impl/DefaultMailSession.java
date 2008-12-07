package com.myslek.webmail.impl;

import java.util.ArrayList;
import java.util.Collection;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;
import javax.mail.Transport;

import com.myslek.webmail.api.AbstractMailSession;
import com.myslek.webmail.api.MessageConverter;
import com.myslek.webmail.api.MailSessionException;
import com.myslek.webmail.api.MessageFilter;
import com.myslek.webmail.domain.MailMessage;
import com.myslek.webmail.domain.MailServer;
import com.myslek.webmail.domain.MailServerType;

public class DefaultMailSession extends AbstractMailSession {
	
	public DefaultMailSession() {
		setMessageConverter(new DefaultMessageConverter());
	}
	
	public DefaultMailSession(MessageConverter messageConverter) {
		setMessageConverter(messageConverter);
	}

	public Collection<MailMessage> fetchMessages(MailServer mailServer, MessageFilter filter)
			throws MailSessionException {
		if (mailServer == null) {
			throw new NullPointerException("MailServer argument is null!");
		}
		if (mailServer.getType() != MailServerType.IN) {
			throw new IllegalArgumentException("MailServer argument has a wrong type " +
					"[" + mailServer.getType() + "]");
		}
		Store store = null;
		Folder folder = null;
		Collection<MailMessage> mailMessages = new ArrayList<MailMessage>();
		try {
			store = getStore(mailServer);
			folder = store.getFolder(mailServer.getDefaultFolder());
			//TODO: make the folder 'read mode' configurable if possible
			folder.open(Folder.READ_ONLY);
			
			//TODO: use the FetchProfile class to fetch only messages ENVELOPE
			Message[] messages = folder.getMessages();
			
			for (Message message : messages) {
				if (filter == null || filter.accept(message)) {
					MailMessage mailMessage = 
						getMessageConverter().fromMessage(message);
					
					//TODO: Determine destination MailFolder for the message
					mailMessages.add(mailMessage);
				}
			}
		} catch (MessagingException e) {
			throw new MailSessionException(e);
		} finally {
			if (folder != null) {
				try {
					folder.close(true);
				} catch (MessagingException e) {
					//Note that the service is closed even if this method 
					//terminates abnormally by throwing a MessagingException
					//TODO: log the exception with 'warn' severity
				}
			}
			
			if (store != null) {
				try {
					store.close();
				} catch (MessagingException e) {
					//Note that the service is closed even if this method 
					//terminates abnormally by throwing a MessagingException
					//TODO: log the exception with 'warn' severity
				}
			}
		}
		return mailMessages;
	}

	public void sendMessage(MailServer mailServer, MailMessage mailMessage)
			throws MailSessionException {
		if (mailServer == null) {
			throw new NullPointerException("MailServer argument is null!");
		}
		if (mailServer.getType() != MailServerType.OUT) {
			throw new IllegalArgumentException("MailServer argument has a wrong type " +
					"[" + mailServer.getType() + "]");
		}
		Transport transport = null;
		try {
			transport = getTransport(mailServer);
			Message message = getMessageConverter().toMessage(mailMessage,
					getSession());
			message.saveChanges();
			transport.sendMessage(message, message.getAllRecipients());
		} catch (MessagingException e) {
			throw new MailSessionException(e);
		} finally {
			if (transport != null) {
				try {
					transport.close();
				} catch (MessagingException e) {
					//Note that the service is closed even if this method 
					//terminates abnormally by throwing a MessagingException
					//TODO: log the exception with 'warn' severity
				}
			}
		}
	}
}
