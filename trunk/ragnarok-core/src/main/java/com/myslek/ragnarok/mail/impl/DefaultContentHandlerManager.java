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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.Session;

import com.myslek.ragnarok.domain.MailPart;
import com.myslek.ragnarok.mail.ContentHandler;
import com.myslek.ragnarok.mail.ContentHandlerManager;
import com.myslek.ragnarok.mail.contenthandler.BlobContentHandler;
import com.myslek.ragnarok.mail.contenthandler.MessageContentHandler;
import com.myslek.ragnarok.mail.contenthandler.MultipartContentHandler;
import com.myslek.ragnarok.mail.contenthandler.TextContentHandler;
import com.myslek.ragnarok.mail.exception.MessageConversionException;

// TODO: Auto-generated Javadoc
/**
 * The Class DefaultContentHandlerManager.
 */
public class DefaultContentHandlerManager implements ContentHandlerManager {
	
	/** The handlers. */
	private final Map<String, ContentHandler> handlers = new HashMap<String, ContentHandler>();
	
	/** The Constant TEXT_TYPE_HANDLER. */
	public static final String TEXT_TYPE_HANDLER = "TEXT";
	
	/** The Constant MESSAGE_TYPE_HANDLER. */
	public static final String MESSAGE_TYPE_HANDLER = "MESSAGE";
	
	/** The Constant MULTIPART_TYPE_HANDLER. */
	public static final String MULTIPART_TYPE_HANDLER = "MULTIPART";
	
	/** The Constant BLOB_TYPE_HANDLER. */
	public static final String BLOB_TYPE_HANDLER = "BLOB";
	
	/**
	 * Instantiates a new default content handler manager.
	 */
	public DefaultContentHandlerManager() {
		defaultContentHandlers();
	}
	
	private void defaultContentHandlers() {
		handlers.put(TEXT_TYPE_HANDLER, new TextContentHandler());
		handlers.put(MESSAGE_TYPE_HANDLER, new MessageContentHandler());
		handlers.put(MULTIPART_TYPE_HANDLER, new MultipartContentHandler());
		handlers.put(BLOB_TYPE_HANDLER, new BlobContentHandler());
	}
	
	/* (non-Javadoc)
	 * @see com.myslek.webmail.api.ContentHandlerManager#addContentHandler(java.lang.String, com.myslek.webmail.api.ContentHandler)
	 */
	public void addContentHandler(String contentType, ContentHandler handler) {
		if (handler == null) {
			throw new IllegalArgumentException("ContentHadnler argument is null!");
		}
		handlers.put(contentType, handler);
	}

	/* (non-Javadoc)
	 * @see com.myslek.webmail.api.ContentHandlerManager#fromPartContent(javax.mail.Part, com.myslek.webmail.domain.MailPart)
	 */
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
	
	/* (non-Javadoc)
	 * @see com.myslek.webmail.api.ContentHandlerManager#toPartContent(com.myslek.webmail.domain.MailPart, javax.mail.Part, javax.mail.Session)
	 */
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

	/* (non-Javadoc)
	 * @see com.myslek.webmail.api.ContentHandlerManager#getContentHandlers()
	 */
	public Collection<ContentHandler> getContentHandlers() {
		return handlers.values();
	}
	
	/**
	 * Find content handler.
	 * 
	 * @param contentType the content type
	 * 
	 * @return the content handler
	 */
	protected ContentHandler findContentHandler(String contentType) {
		return null;
	}
}
