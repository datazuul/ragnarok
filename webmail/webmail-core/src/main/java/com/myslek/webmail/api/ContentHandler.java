package com.myslek.webmail.api;

import javax.mail.Part;

import com.myslek.webmail.domain.MailPart;

public interface ContentHandler {

	//TODO: remove this method, as the ContentHandlerChain should be responsible 
	//for the discovery of a relevant ContentHandler
	public boolean accept(String contentType) throws MessageConversionException;

	public void fromPartContent(Part part, MailPart mailPart, ContentHandlerChain chain) throws MessageConversionException;
	
	public void toPartContent(MailPart mailPart, Part part, ContentHandlerChain chain) throws MessageConversionException;
}
