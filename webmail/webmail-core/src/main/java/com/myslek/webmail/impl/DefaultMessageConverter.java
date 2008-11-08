package com.myslek.webmail.impl;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import com.myslek.webmail.api.ContentHandlerChain;
import com.myslek.webmail.api.EnvelopeHandler;
import com.myslek.webmail.api.MessageConversionException;
import com.myslek.webmail.api.MessageConverter;
import com.myslek.webmail.domain.MailMessage;

public class DefaultMessageConverter implements MessageConverter {
	
	private EnvelopeHandler envelopeConverter;
	
	private ContentHandlerChain contentHandlerChain;

	public EnvelopeHandler getEnvelopeConverter() {
		return envelopeConverter;
	}

	public void setEnvelopeConverter(EnvelopeHandler envelopeConverter) {
		this.envelopeConverter = envelopeConverter;
	}

	public ContentHandlerChain getContentHandlerChain() {
		return contentHandlerChain;
	}

	public void setContentHandlerChain(
			ContentHandlerChain contentHandlerChain) {
		this.contentHandlerChain = contentHandlerChain;
	}

	public MailMessage fromMessage(Message message)
	throws MessageConversionException {
		try {
			MailMessage mailMessage = new MailMessage();
			mailMessage.setContentType(message.getContentType());
			
			getEnvelopeConverter().fromEnvelope(message, mailMessage);

			//Process message content.
			getContentHandlerChain().fromPartContent(message, mailMessage);

			return mailMessage;
		} catch (MessagingException e) {
			throw new MessageConversionException(e);
		}
	}

	public Message toMessage(MailMessage mailMessage, Session session)
			throws MessageConversionException {
		Message message = new MimeMessage(session);
		
		//TODO: set message contentType, etc...
		
		getContentHandlerChain().toPartContent(mailMessage, message);
		
		return message;
	}
}
