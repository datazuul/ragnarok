package com.myslek.webmail.api;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;

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
	
	protected Transport getTransport(MailServer mailServer) throws MailSessionException {
		try {
			Transport transport = getSession().getTransport(mailServer.getProtocol());
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
}
