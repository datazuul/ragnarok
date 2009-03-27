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
package com.myslek.ragnarok.mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;

import com.myslek.ragnarok.domain.MailBox;
import com.myslek.ragnarok.domain.MailMessage;
import com.myslek.ragnarok.domain.MailServer;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractMailSession.
 */
public abstract class AbstractMailSession implements MailSession {

	/** The message converter. */
	private MessageConverter messageConverter;

	/**
	 * Gets the message converter.
	 * 
	 * @return the message converter
	 */
	public MessageConverter getMessageConverter() {
		return messageConverter;
	}

	/**
	 * Sets the message converter.
	 * 
	 * @param messageConverter
	 *            the new message converter
	 */
	public void setMessageConverter(MessageConverter messageConverter) {
		this.messageConverter = messageConverter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.myslek.webmail.api.MailSession#getSession()
	 */
	public Session getSession() throws MailSessionException {
		Properties props = getMailProperties();
		return Session.getDefaultInstance(props, null);
	}

	/**
	 * Gets the store.
	 * 
	 * @param mailServer
	 *            the mail server
	 * 
	 * @return the store
	 * 
	 * @throws MailSessionException
	 *             the mail session exception
	 */
	protected Store getStore(MailServer mailServer) throws MailSessionException {
		try {
			Store store = getSession().getStore(mailServer.getProtocol().toString());
			store.connect(mailServer.getHostname(), mailServer.getUsername(),
					decipherPassword(mailServer.getPassword()));

			return store;
		} catch (MessagingException e) {
			throw new MailSessionException(e);
		}
	}

	/**
	 * Gets the transport.
	 * 
	 * @param mailServer
	 *            the mail server
	 * 
	 * @return the transport
	 * 
	 * @throws MailSessionException
	 *             the mail session exception
	 */
	protected Transport getTransport(MailServer mailServer) throws MailSessionException {
		try {
			Transport transport = getSession().getTransport(mailServer.getProtocol().toString());
			transport.connect(mailServer.getHostname(), mailServer.getUsername(),
					decipherPassword(mailServer.getPassword()));
			return transport;
		} catch (MessagingException e) {
			throw new MailSessionException(e);
		}
	}

	/**
	 * Decipher password.
	 * 
	 * @param password
	 *            the password
	 * 
	 * @return the string
	 */
	protected String decipherPassword(String password) {
		return password;
	}

	/**
	 * Gets the mail properties.
	 * 
	 * @return the mail properties
	 */
	protected Properties getMailProperties() {
		return new Properties();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.myslek.webmail.api.MailSession#sendMessage(com.myslek.webmail.domain
	 * .MailBox, com.myslek.webmail.domain.MailMessage)
	 */
	public void sendMessage(MailBox mailBox, MailMessage mailMessage) throws MailSessionException {
		Transport transport = null;
		try {
			transport = getTransport(mailBox.getMailTransport());
			Message message = getMessageConverter().toMessage(mailMessage, getSession());
			// message.saveChanges();
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
