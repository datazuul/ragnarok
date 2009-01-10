package com.myslek.webmail.api;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;

import com.myslek.webmail.domain.MailBox;
import com.myslek.webmail.domain.MailMessage;
import com.myslek.webmail.domain.MailServer;

public abstract class AbstractMailSession implements MailSession {

	private MessageConverter messageConverter;

	public MessageConverter getMessageConverter() {
		return messageConverter;
	}

	public void setMessageConverter(MessageConverter messageConverter) {
		this.messageConverter = messageConverter;
	}

	public Session getSession() throws MailSessionException {
		Properties props = getMailProperties();
		return Session.getDefaultInstance(props, null);
	}

	protected Store getStore(MailServer mailServer) throws MailSessionException {
		try {
			Store store = getSession().getStore(mailServer.getProtocol());
			store.connect(mailServer.getHost(), mailServer.getUsername(),
					decipherPassword(mailServer.getPassword()));

			return store;
		} catch (MessagingException e) {
			throw new MailSessionException(e);
		}
	}

	protected Transport getTransport(MailServer mailServer)
			throws MailSessionException {
		try {
			Transport transport = getSession().getTransport(
					mailServer.getProtocol());
			transport.connect(mailServer.getHost(), mailServer.getUsername(),
					decipherPassword(mailServer.getPassword()));
			return transport;
		} catch (MessagingException e) {
			throw new MailSessionException(e);
		}
	}

	protected String decipherPassword(String password) {
		return password;
	}

	protected Properties getMailProperties() {
		return new Properties();
	}

	public void sendMessage(MailBox mailBox, MailMessage mailMessage)
			throws MailSessionException {
		Transport transport = null;
		try {
			transport = getTransport(mailBox.getMailTransport());
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
					// Note that the service is closed even if this method
					// terminates abnormally by throwing a MessagingException
					// TODO: log the exception with 'warn' severity
				}
			}
		}
	}
}
