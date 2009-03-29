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
package com.myslek.ragnarok.mail.contenthandler;

import java.io.IOException;
import java.io.InputStream;

import javax.activation.DataHandler;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.util.ByteArrayDataSource;

import com.myslek.ragnarok.domain.MailPart;
import com.myslek.ragnarok.mail.ContentHandlerManager;
import com.myslek.ragnarok.mail.exception.MessageConversionException;
import com.myslek.ragnarok.util.IOUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class BlobContentHandler.
 */
public class BlobContentHandler extends AbstractContentHandler {

	/* (non-Javadoc)
	 * @see com.myslek.webmail.api.ContentHandler#accept(java.lang.String)
	 */
	public boolean accept(String contentType) throws MessageConversionException {
		return contentType.startsWith(MailPart.IMAGE_TYPE_PREFIX)
				|| contentType.startsWith(MailPart.VIDEO_TYPE_PREFIX)
				|| contentType.startsWith(MailPart.APPLICATION_TYPE_PREFIX)
				|| contentType.startsWith(MailPart.AUDIO_TYPE_PREFIX);
	}

	/* (non-Javadoc)
	 * @see com.myslek.webmail.api.ContentHandler#fromPartContent(javax.mail.Part, com.myslek.webmail.domain.MailPart, com.myslek.webmail.api.ContentHandlerManager)
	 */
	public void fromPartContent(Part part, MailPart mailPart,
			ContentHandlerManager manager)
			throws MessageConversionException {
		InputStream input = null;
		try {
			input = part.getInputStream();
			byte[] data = IOUtils.getBytes(input);
			mailPart.setData(data);
			mailPart.setFileName(part.getFileName());
		} catch (IOException e) {
			throw new MessageConversionException(e);
		} catch (MessagingException e) {
			throw new MessageConversionException(e);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					throw new MessageConversionException(e);
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.myslek.webmail.api.ContentHandler#toPartContent(com.myslek.webmail.domain.MailPart, javax.mail.Part, javax.mail.Session, com.myslek.webmail.api.ContentHandlerManager)
	 */
	public void toPartContent(MailPart mailPart, Part part,
			Session session, ContentHandlerManager manager)
			throws MessageConversionException {
		try {
			ByteArrayDataSource dataSource = new ByteArrayDataSource(
					mailPart.getData(), mailPart.getContentType());
			part.setDataHandler(new DataHandler(dataSource));
			part.setFileName(mailPart.getFileName());
		} catch (MessagingException e) {
			throw new MessageConversionException(e);
		}
	}
}