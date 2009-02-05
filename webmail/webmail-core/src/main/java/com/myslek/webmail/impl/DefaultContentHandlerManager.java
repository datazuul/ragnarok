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
package com.myslek.webmail.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.Session;

import com.myslek.webmail.api.ContentHandler;
import com.myslek.webmail.api.ContentHandlerManager;
import com.myslek.webmail.api.MessageConversionException;
import com.myslek.webmail.contenthandler.BlobContentHandler;
import com.myslek.webmail.contenthandler.MessageContentHandler;
import com.myslek.webmail.contenthandler.MultipartContentHandler;
import com.myslek.webmail.contenthandler.TextContentHandler;
import com.myslek.webmail.domain.MailPart;

public class DefaultContentHandlerManager implements ContentHandlerManager {
	
	private Map<String, ContentHandler> handlers = new HashMap<String, ContentHandler>();
	
	public static final String TEXT_TYPE_HANDLER = "TEXT";
	public static final String MESSAGE_TYPE_HANDLER = "MESSAGE";
	public static final String MULTIPART_TYPE_HANDLER = "MULTIPART";
	public static final String BLOB_TYPE_HANDLER = "BLOB";
	
	public DefaultContentHandlerManager() {
		addContentHandler(TEXT_TYPE_HANDLER, new TextContentHandler());
		addContentHandler(MESSAGE_TYPE_HANDLER, new MessageContentHandler());
		addContentHandler(MULTIPART_TYPE_HANDLER, new MultipartContentHandler());
		addContentHandler(BLOB_TYPE_HANDLER, new BlobContentHandler());
	}
	
	public void addContentHandler(String contentType, ContentHandler handler) {
		if (handler == null) {
			throw new NullPointerException("ContentHadnler argument is null!");
		}
		handlers.put(contentType, handler);
	}

	public void fromPartContent(Part part, MailPart mailPart)
			throws MessageConversionException {
		try {
			String contentType = part.getContentType();
			for (ContentHandler handler : handlers.values()) {
				if (handler.accept(contentType)) {
					handler.fromPartContent(part, mailPart, this);
					return;
				}
			}
			throw new MessageConversionException("Unable to find 'ContentHandler' " +
					"for 'javax.mail.Part' content type [" + contentType + "]");
		} catch (MessagingException e) {
			throw new MessageConversionException(e);
		}
	}
	
	public void toPartContent(MailPart mailPart, Part part, Session session)
			throws MessageConversionException {
		String contentType = mailPart.getContentType();
		for (ContentHandler handler : handlers.values()) {
			if (handler.accept(contentType)) {
				handler.toPartContent(mailPart, part, session, this);
				return;
			}
		}
		throw new MessageConversionException("Unable to find 'ContentHandler' " +
				"for 'com.myslek.webmail.domain.MailPart' content type [" + contentType + "]");
	}

	public Collection<ContentHandler> getContentHandlers() {
		return handlers.values();
	}
	
	protected ContentHandler findContentHandler(String contentType) {
		return null;
	}
}
