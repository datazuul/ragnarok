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
package com.myslek.ragnarok.contenthandler;

import java.io.IOException;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import com.myslek.ragnarok.core.ContentHandlerManager;
import com.myslek.ragnarok.core.MessageConversionException;
import com.myslek.ragnarok.domain.MailPart;

// TODO: Auto-generated Javadoc
/**
 * The Class MultipartContentHandler.
 */
public class MultipartContentHandler extends AbstractContentHandler {

	/* (non-Javadoc)
	 * @see com.myslek.webmail.api.ContentHandler#accept(java.lang.String)
	 */
	public boolean accept(String contentType) throws MessageConversionException {
		return contentType.startsWith(MailPart.MULTIPART_TYPE_PREFIX);
	}

	/* (non-Javadoc)
	 * @see com.myslek.webmail.api.ContentHandler#fromPartContent(javax.mail.Part, com.myslek.webmail.domain.MailPart, com.myslek.webmail.api.ContentHandlerManager)
	 */
	public void fromPartContent(Part part, MailPart mailPart,
			ContentHandlerManager manager)
			throws MessageConversionException {
		try {
			Multipart multipart = (Multipart) part.getContent();
			MailPart multiPart = new MailPart();
			mailPart.addPart(multiPart);
			multiPart.setContentType(multipart.getContentType());

			for (int i = 0; i < multipart.getCount(); i++) {
				Part body = multipart.getBodyPart(i);
				MailPart bodyPart = new MailPart();
				getAttributesHandler().fromAttributes(body, bodyPart);
				multiPart.addPart(bodyPart);

				manager.fromPartContent(body, bodyPart);
			}
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
			Session session, ContentHandlerManager manager)
			throws MessageConversionException {
		try {
			Multipart multipart = new MimeMultipart();
			MailPart multiPart = mailPart.getParts().get(0);

			for (MailPart body : multiPart.getParts()) {
				BodyPart bodyPart = new MimeBodyPart();
				
				getAttributesHandler().toAttributes(body, bodyPart);
				manager.toPartContent(body, bodyPart, session);

				multipart.addBodyPart(bodyPart);
			}
			part.setContent(multipart);
		} catch (MessagingException e) {
			throw new MessageConversionException(e);
		}
	}
}
