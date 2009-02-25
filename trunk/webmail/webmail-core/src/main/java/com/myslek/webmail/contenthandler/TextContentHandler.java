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
package com.myslek.webmail.contenthandler;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.Session;

import com.myslek.webmail.api.ContentHandlerManager;
import com.myslek.webmail.api.MessageConversionException;
import com.myslek.webmail.domain.Content;
import com.myslek.webmail.domain.MailPart;

// TODO: Auto-generated Javadoc
/**
 * The Class TextContentHandler.
 */
public class TextContentHandler extends AbstractContentHandler {

	/* (non-Javadoc)
	 * @see com.myslek.webmail.api.ContentHandler#accept(java.lang.String)
	 */
	public boolean accept(String contentType) throws MessageConversionException {
		return contentType.startsWith(MailPart.TEXT_TYPE_PREFIX);
	}

	/* (non-Javadoc)
	 * @see com.myslek.webmail.api.ContentHandler#fromPartContent(javax.mail.Part, com.myslek.webmail.domain.MailPart, com.myslek.webmail.api.ContentHandlerManager)
	 */
	public void fromPartContent(Part part, MailPart mailPart,
			ContentHandlerManager contentHandlerManager)
			throws MessageConversionException {
		try {
			String text = (String) part.getContent();
			Content content = new Content();
			content.setText(text);
			mailPart.setContent(content);
		} catch (IOException e) {
			throw new MessageConversionException(e);
		} catch (MessagingException e) {
			throw new MessageConversionException(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.myslek.webmail.api.ContentHandler#toPartContent(com.myslek.webmail.domain.MailPart, javax.mail.Part, javax.mail.Session, com.myslek.webmail.api.ContentHandlerManager)
	 */
	public void toPartContent(MailPart mailPart, Part part,
			Session session, ContentHandlerManager contentHandlerManager)
			throws MessageConversionException {
		try {
			part.setContent((String) mailPart.getContent().getText(), mailPart
					.getContentType());
		} catch (MessagingException e) {
			throw new MessageConversionException(e);
		}
	}
}
