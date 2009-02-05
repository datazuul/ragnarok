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

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import com.myslek.webmail.api.AttributesHandler;
import com.myslek.webmail.api.ContentHandlerManager;
import com.myslek.webmail.api.EnvelopeHandler;
import com.myslek.webmail.api.MessageConversionException;
import com.myslek.webmail.api.MessageConverter;
import com.myslek.webmail.domain.MailMessage;

public class DefaultMessageConverter implements MessageConverter {

	private AttributesHandler attributesHandler;
	private EnvelopeHandler envelopeHandler;
	private ContentHandlerManager contentHandlerManager;

	public DefaultMessageConverter() {
		this.attributesHandler = new DefaultAttributesHandler();
		this.envelopeHandler = new DefaultEnvelopeHandler();
		this.contentHandlerManager = new DefaultContentHandlerManager();
	}

	public EnvelopeHandler getEnvelopeHandler() {
		return envelopeHandler;
	}

	public void setEnvelopeHandler(EnvelopeHandler envelopeHandler) {
		this.envelopeHandler = envelopeHandler;
	}

	public ContentHandlerManager getContentHandlerManager() {
		return contentHandlerManager;
	}

	public void setContentHandlerManager(
			ContentHandlerManager contentHandlerManager) {
		this.contentHandlerManager = contentHandlerManager;
	}

	public AttributesHandler getAttributesHandler() {
		return attributesHandler;
	}

	public void setAttributesHandler(AttributesHandler attributesHandler) {
		this.attributesHandler = attributesHandler;
	}

	public MailMessage fromMessage(Message message)
			throws MessageConversionException {
		MailMessage mailMessage = new MailMessage();
		
		getAttributesHandler().fromAttributes(message, mailMessage);
		getEnvelopeHandler().fromEnvelope(message, mailMessage);
		getContentHandlerManager().fromPartContent(message, mailMessage);

		return mailMessage;
	}

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
