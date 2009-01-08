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
