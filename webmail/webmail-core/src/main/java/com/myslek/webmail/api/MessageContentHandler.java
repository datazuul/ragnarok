package com.myslek.webmail.api;

import java.io.IOException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Part;

import com.myslek.webmail.domain.MailMessage;
import com.myslek.webmail.domain.MailPart;

public class MessageContentHandler extends AbstractContentHandler {
	
	public static final String MESSAGE_TYPE_PREFIX = "message/";

	public boolean accept(String contentType) throws MessageConversionException {
		return contentType.startsWith(MESSAGE_TYPE_PREFIX);
	}

	public void fromPartContent(Part part, MailPart mailPart,
			ContentHandlerManager contentHandlerManager)
			throws MessageConversionException {
		try {
			Message message = (Message) part.getContent();
			MailMessage mailMessage = new MailMessage();
			mailPart.addPart(mailMessage);

			getAttributesHandler().fromAttributes(message, mailMessage);
			getEnvelopeHandler().fromEnvelope(message, mailMessage);
			contentHandlerManager.fromPartContent(message, mailMessage);

		} catch (IOException e) {
			throw new MessageConversionException(e);
		} catch (MessagingException e) {
			throw new MessageConversionException(e);
		}
	}

	public void toPartContent(MailPart mailPart, Part part,
			ContentHandlerManager contentHandlerManager)
			throws MessageConversionException {
	}
}
