package com.myslek.webmail.impl;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import com.myslek.webmail.api.ContentHandlerManager;
import com.myslek.webmail.api.EnvelopeHandler;
import com.myslek.webmail.api.MessageConversionException;
import com.myslek.webmail.api.MessageConverter;
import com.myslek.webmail.domain.MailMessage;

public class DefaultMessageConverter implements MessageConverter {

	private EnvelopeHandler envelopeConverter;

	private ContentHandlerManager contentHandlerManager;

	public EnvelopeHandler getEnvelopeConverter() {
		return envelopeConverter;
	}

	public void setEnvelopeConverter(EnvelopeHandler envelopeConverter) {
		this.envelopeConverter = envelopeConverter;
	}

	public ContentHandlerManager getContentHandlerManager() {
		return contentHandlerManager;
	}

	public void setContentHandlerManager(
			ContentHandlerManager contentHandlerManager) {
		this.contentHandlerManager = contentHandlerManager;
	}

	public MailMessage fromMessage(Message message)
			throws MessageConversionException {
		try {
			MailMessage mailMessage = new MailMessage();
			mailMessage.setContentType(message.getContentType());

			getEnvelopeConverter().fromEnvelope(message, mailMessage);

			// Process message content.
			getContentHandlerManager().fromPartContent(message, mailMessage);

			return mailMessage;
		} catch (MessagingException e) {
			throw new MessageConversionException(e);
		}
	}

	public Message toMessage(MailMessage mailMessage, Session session)
			throws MessageConversionException {
		Message message = new MimeMessage(session);

		// TODO: set message contentType, etc...

		getContentHandlerManager().toPartContent(mailMessage, message);

		return message;
	}
}
