package com.myslek.webmail.api;

import java.util.Collection;

import javax.mail.Part;

import com.myslek.webmail.domain.MailPart;

public interface ContentHandlerChain {
	
	public void addContentHandler(String contentType, ContentHandler handler);
	
	public Collection<ContentHandler> getContentHandlers();
	
	public void fromPartContent(Part part, MailPart mailPart) throws MessageConversionException;
	
	public void toPartContent(MailPart mailPart, Part part) throws MessageConversionException;
}
