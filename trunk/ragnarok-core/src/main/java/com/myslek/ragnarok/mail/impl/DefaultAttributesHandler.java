/*
 * Copyright 2009 Rafal Myslek 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0 
 *     
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License.     
 */
package com.myslek.ragnarok.mail.impl;

import java.util.Enumeration;

import javax.mail.Header;
import javax.mail.MessagingException;
import javax.mail.Part;

import com.myslek.ragnarok.domain.MailHeader;
import com.myslek.ragnarok.domain.MailPart;
import com.myslek.ragnarok.mail.AttributesHandler;
import com.myslek.ragnarok.mail.MessageConversionException;

// TODO: Auto-generated Javadoc
/**
 * The Class DefaultAttributesHandler.
 */
public class DefaultAttributesHandler implements AttributesHandler {

	/* (non-Javadoc)
	 * @see com.myslek.webmail.api.AttributesHandler#fromAttributes(javax.mail.Part, com.myslek.webmail.domain.MailPart)
	 */
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

	/* (non-Javadoc)
	 * @see com.myslek.webmail.api.AttributesHandler#toAttributes(com.myslek.webmail.domain.MailPart, javax.mail.Part)
	 */
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

	/**
	 * From headers.
	 * 
	 * @param part the part
	 * @param mailPart the mail part
	 * 
	 * @throws MessageConversionException the message conversion exception
	 */
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

	/**
	 * To headers.
	 * 
	 * @param mailPart the mail part
	 * @param part the part
	 * 
	 * @throws MessageConversionException the message conversion exception
	 */
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
