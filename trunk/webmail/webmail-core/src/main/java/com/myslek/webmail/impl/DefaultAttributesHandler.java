package com.myslek.webmail.impl;

import java.util.Enumeration;

import javax.mail.Header;
import javax.mail.MessagingException;
import javax.mail.Part;

import com.myslek.webmail.api.AttributesHandler;
import com.myslek.webmail.api.MessageConversionException;
import com.myslek.webmail.domain.MailHeader;
import com.myslek.webmail.domain.MailPart;

public class DefaultAttributesHandler implements AttributesHandler {

	public void fromAttributes(Part part, MailPart mailPart)
			throws MessageConversionException {
		try {
			fromHeaders(part, mailPart);
			mailPart.setContentType(part.getContentType());
			mailPart.setDescription(part.getDescription());
			mailPart.setDisposition(part.getDisposition());
			mailPart.setSize(part.getSize());
		} catch (MessagingException e) {
			throw new MessageConversionException(e);
		}
		
	}

	public void toAttributes(MailPart mailPart, Part part)
			throws MessageConversionException {
		try {
			toHeaders(mailPart, part);
			part.setDescription(mailPart.getDescription());
			part.setDisposition(mailPart.getDisposition());
		} catch (MessagingException e) {
			throw new MessageConversionException(e);
		}
	}

	protected void fromHeaders(Part part, MailPart mailPart)
			throws MessageConversionException {
		try {
			for (Enumeration<Header> enumeration = part.getAllHeaders(); enumeration
					.hasMoreElements();) {
				Header header = enumeration.nextElement();
				MailHeader mailHeader = new MailHeader();
				mailHeader.setName(header.getName());
				mailHeader.setValue(header.getValue());
				mailPart.addHeader(mailHeader);
			}
		} catch (MessagingException e) {
			throw new MessageConversionException(e);
		}
	}

	protected void toHeaders(MailPart mailPart, Part part)
			throws MessageConversionException {
		try {
			for (MailHeader header : mailPart.getHeaders()) {
				part.setHeader(header.getName(), header.getValue());
			}
		} catch (MessagingException e) {
			throw new MessageConversionException(e);
		}
	}
}
