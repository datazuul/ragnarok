package com.myslek.webmail.api;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.Part;

import com.myslek.webmail.domain.Content;
import com.myslek.webmail.domain.MailPart;

public class TextContentHandler extends AbstractContentHandler {

	public static final String TEXT_TYPE_PREFIX = "text/";

	public boolean accept(String contentType) throws MessageConversionException {
		return contentType.startsWith(TEXT_TYPE_PREFIX);
	}

	public void fromPartContent(Part part, MailPart mailPart,
			ContentHandlerManager contentHandlerManager)
			throws MessageConversionException {
		try {
			String text = (String) part.getContent();
			Content content = new Content();
			content.setText(text);
			mailPart.setContent(content);
			mailPart.setSize(part.getSize());
		} catch (IOException e) {
			throw new MessageConversionException(e);
		} catch (MessagingException e) {
			throw new MessageConversionException(e);
		}
	}

	public void toPartContent(MailPart mailPart, Part part,
			ContentHandlerManager contentHandlerManager)
			throws MessageConversionException {
		try {
			part.setContent((String) mailPart.getContent().getText(), mailPart
					.getContentType());
		} catch (MessagingException e) {
			throw new MessageConversionException(e);
		}
	}
}
