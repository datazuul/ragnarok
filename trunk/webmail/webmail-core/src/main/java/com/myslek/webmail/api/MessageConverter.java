package com.myslek.webmail.api;

import javax.mail.Message;
import javax.mail.Session;

import com.myslek.webmail.domain.MailMessage;

public interface MessageConverter {
	
	public MailMessage fromMessage(Message message) throws MessageConversionException;
	
	public Message toMessage(MailMessage mailMessage, Session session) throws MessageConversionException;
}
