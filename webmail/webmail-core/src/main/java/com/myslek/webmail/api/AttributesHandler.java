package com.myslek.webmail.api;

import javax.mail.Part;

import com.myslek.webmail.domain.MailPart;

public interface AttributesHandler {
	
	public void fromAttributes(Part part, MailPart mailPart) throws MessageConversionException;
	
	public void toAttributes(MailPart mailPart, Part part) throws MessageConversionException;
}
