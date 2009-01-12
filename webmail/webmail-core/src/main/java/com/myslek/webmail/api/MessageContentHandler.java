package com.myslek.webmail.api;

import java.io.IOException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import com.myslek.webmail.domain.MailMessage;
import com.myslek.webmail.domain.MailPart;

public class MessageContentHandler extends AbstractContentHandler {

	public boolean accept(String contentType) throws MessageConversionException {
		return contentType.startsWith(MailPart.MESSAGE_TYPE_PREFIX);
	}

	public void fromPartContent(Part part, MailPart mailPart,
			ContentHandlerManager contentHandlerManager)
			throws MessageConversionException {
		try {
			Message message = (Message) part.getContent();
			
			MailPart messagePart = new MailPart();
			mailPart.addPart(messagePart);

			getAttributesHandler().fromAttributes(message, messagePart);
			contentHandlerManager.fromPartContent(message, messagePart);
		} catch (IOException e) {
			throw new MessageConversionException(e);
		} catch (MessagingException e) {
			throw new MessageConversionException(e);
		}
	}

	public void toPartContent(MailPart mailPart, Part part,
			Session session, ContentHandlerManager contentHandlerManager)
			throws MessageConversionException {
		try {
			Message message = new MimeMessage(session);
			MailMessage mailMessage = (MailMessage) mailPart;
			
			getAttributesHandler().toAttributes(mailMessage, message);
			getEnvelopeHandler().toEnvelope(mailMessage, message);
			contentHandlerManager.toPartContent(mailMessage, message, session);
			
			part.setDataHandler(message.getDataHandler());
		} catch (MessagingException e) {
			throw new MessageConversionException(e);
		}
	}
}
