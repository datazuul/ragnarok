package com.myslek.webmail.api;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.Part;

import com.myslek.webmail.domain.MailPart;

public class TextContentHandler extends AbstractContentHandler {

	public boolean accept(String contentType) throws MessageConversionException {
		return contentType.startsWith("text/");
	}

	public void fromPartContent(Part part, MailPart mailPart,
			ContentHandlerManager contentHandlerManager) throws MessageConversionException {
		try {
			String text = (String) part.getContent();
			mailPart.setData(text.getBytes());
			mailPart.setSize(part.getSize());
		} catch (IOException e) {
			throw new MessageConversionException(e);
		} catch (MessagingException e) {
			throw new MessageConversionException(e);
		}
	}

	public void toPartContent(MailPart mailPart, Part part,
			ContentHandlerManager contentHandlerManager) throws MessageConversionException {
		try {
			String text = new String(mailPart.getData());
			part.setContent(text, mailPart.getContentType());
		} catch (MessagingException e) {
			throw new MessageConversionException(e);
		}
	}
}
