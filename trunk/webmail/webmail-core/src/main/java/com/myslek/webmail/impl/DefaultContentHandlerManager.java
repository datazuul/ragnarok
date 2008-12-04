package com.myslek.webmail.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.Part;

import com.myslek.webmail.api.ContentHandler;
import com.myslek.webmail.api.ContentHandlerManager;
import com.myslek.webmail.api.MessageContentHandler;
import com.myslek.webmail.api.MessageConversionException;
import com.myslek.webmail.api.MultipartContentHandler;
import com.myslek.webmail.api.TextContentHandler;
import com.myslek.webmail.domain.MailPart;

public class DefaultContentHandlerManager implements ContentHandlerManager {
	
	private Map<String, ContentHandler> handlers = new HashMap<String, ContentHandler>();
	
	public static final String TEXT = "text/*";
	public static final String MESSAGE = "message/*";
	public static final String MULTIPART = "multipart/*";
	
	public DefaultContentHandlerManager() {
		addContentHandler(TEXT, new TextContentHandler());
		addContentHandler(MESSAGE, new MessageContentHandler());
		addContentHandler(MULTIPART, new MultipartContentHandler());
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
				}
			}
			throw new MessageConversionException("Unable to find 'ContentHandler' " +
					"for 'javax.mail.Part' content type [" + contentType + "]");
		} catch (MessagingException e) {
			throw new MessageConversionException(e);
		}
	}
	
	public void toPartContent(MailPart mailPart, Part part)
			throws MessageConversionException {
		String contentType = mailPart.getContentType();
		for (ContentHandler handler : handlers.values()) {
			if (handler.accept(contentType)) {
				handler.toPartContent(mailPart, part, this);
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
