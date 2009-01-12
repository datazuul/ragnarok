package com.myslek.webmail.impl;

import javax.mail.Message;
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
		Message message = new MimeMessage(session);
		
		getAttributesHandler().toAttributes(mailMessage, message);
		getEnvelopeHandler().toEnvelope(mailMessage, message);
		getContentHandlerManager().toPartContent(mailMessage, message, session);

		return message;
	}
}
