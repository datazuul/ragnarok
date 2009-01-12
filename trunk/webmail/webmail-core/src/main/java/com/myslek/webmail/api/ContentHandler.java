package com.myslek.webmail.api;

import javax.mail.Part;
import javax.mail.Session;

import com.myslek.webmail.domain.MailPart;

public interface ContentHandler {
	
	public boolean accept(String contentType) throws MessageConversionException;

	public void fromPartContent(Part part, MailPart mailPart,
			ContentHandlerManager contentHandlerManager)
			throws MessageConversionException;

	public void toPartContent(MailPart mailPart, Part part,
			Session session, ContentHandlerManager contentHandlerManager)
			throws MessageConversionException;
}
