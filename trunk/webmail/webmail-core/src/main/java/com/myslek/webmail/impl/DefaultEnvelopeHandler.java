package com.myslek.webmail.impl;

import javax.mail.Message;
import javax.mail.MessagingException;

import com.myslek.webmail.api.EnvelopeHandler;
import com.myslek.webmail.api.MessageConversionException;
import com.myslek.webmail.domain.MailMessage;

public class DefaultEnvelopeHandler implements EnvelopeHandler {

	public void fromEnvelope(Message message, MailMessage mailMessage)
			throws MessageConversionException {
		try {
			mailMessage.setSubject(message.getSubject());
			//TODO: 1. copy all message recipients
			//TODO: 2. copy all message headers (message.getAllHeaders())
		} catch (MessagingException e) {
			throw new MessageConversionException(e);
		}
	}

	public void toEnvelope(MailMessage mailMessage, Message message)
			throws MessageConversionException {
		try {
			message.setSubject(mailMessage.getSubject());
		} catch (MessagingException e) {
			throw new MessageConversionException(e);
		}
	}

}
