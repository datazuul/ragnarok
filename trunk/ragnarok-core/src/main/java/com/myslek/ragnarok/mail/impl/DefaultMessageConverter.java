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

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import com.myslek.ragnarok.domain.MailMessage;
import com.myslek.ragnarok.mail.AttributesHandler;
import com.myslek.ragnarok.mail.ContentHandlerManager;
import com.myslek.ragnarok.mail.EnvelopeHandler;
import com.myslek.ragnarok.mail.MessageConversionException;
import com.myslek.ragnarok.mail.MessageConverter;

// TODO: Auto-generated Javadoc
/**
 * The Class DefaultMessageConverter.
 */
public class DefaultMessageConverter implements MessageConverter {

	/** The attributes handler. */
	private AttributesHandler attributesHandler;
	
	/** The envelope handler. */
	private EnvelopeHandler envelopeHandler;
	
	/** The content handler manager. */
	private ContentHandlerManager contentHandlerManager;

	/**
	 * Instantiates a new default message converter.
	 */
	public DefaultMessageConverter() {
		this.attributesHandler = new DefaultAttributesHandler();
		this.envelopeHandler = new DefaultEnvelopeHandler();
		this.contentHandlerManager = new DefaultContentHandlerManager();
	}

	/**
	 * Gets the envelope handler.
	 * 
	 * @return the envelope handler
	 */
	public EnvelopeHandler getEnvelopeHandler() {
		return envelopeHandler;
	}

	/**
	 * Sets the envelope handler.
	 * 
	 * @param envelopeHandler the new envelope handler
	 */
	public void setEnvelopeHandler(EnvelopeHandler envelopeHandler) {
		this.envelopeHandler = envelopeHandler;
	}

	/**
	 * Gets the content handler manager.
	 * 
	 * @return the content handler manager
	 */
	public ContentHandlerManager getContentHandlerManager() {
		return contentHandlerManager;
	}

	/**
	 * Sets the content handler manager.
	 * 
	 * @param contentHandlerManager the new content handler manager
	 */
	public void setContentHandlerManager(
			ContentHandlerManager contentHandlerManager) {
		this.contentHandlerManager = contentHandlerManager;
	}

	/**
	 * Gets the attributes handler.
	 * 
	 * @return the attributes handler
	 */
	public AttributesHandler getAttributesHandler() {
		return attributesHandler;
	}

	/**
	 * Sets the attributes handler.
	 * 
	 * @param attributesHandler the new attributes handler
	 */
	public void setAttributesHandler(AttributesHandler attributesHandler) {
		this.attributesHandler = attributesHandler;
	}

	/* (non-Javadoc)
	 * @see com.myslek.webmail.api.MessageConverter#fromMessage(javax.mail.Message)
	 */
	public MailMessage fromMessage(Message message)
			throws MessageConversionException {
		MailMessage mailMessage = new MailMessage();
		
		getAttributesHandler().fromAttributes(message, mailMessage);
		getEnvelopeHandler().fromEnvelope(message, mailMessage);
		getContentHandlerManager().fromPartContent(message, mailMessage);

		return mailMessage;
	}

	/* (non-Javadoc)
	 * @see com.myslek.webmail.api.MessageConverter#toMessage(com.myslek.webmail.domain.MailMessage, javax.mail.Session)
	 */
	public Message toMessage(MailMessage mailMessage, Session session)
			throws MessageConversionException {
		Message message;
		
		try {
			message = new MimeMessage(session);
			getAttributesHandler().toAttributes(mailMessage, message);
			getEnvelopeHandler().toEnvelope(mailMessage, message);
			getContentHandlerManager().toPartContent(mailMessage, message,
					session);
			
			message.saveChanges();
			
			return message;
		} catch (MessagingException e) {
			throw new MessageConversionException(e);
		}
	}
}
