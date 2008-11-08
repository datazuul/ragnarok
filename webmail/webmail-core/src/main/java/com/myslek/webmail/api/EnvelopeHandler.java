package com.myslek.webmail.api;

import javax.mail.Message;

import com.myslek.webmail.domain.MailMessage;

public interface EnvelopeHandler {
	
	public void fromEnvelope(Message message, MailMessage mailMessage) throws MessageConversionException;
	
	public void toEnvelope(MailMessage mailMessage, Message message) throws MessageConversionException;
}
