package com.myslek.webmail.api;

import javax.mail.Part;
import javax.mail.Session;

import com.myslek.webmail.domain.MailPart;

public interface ContentHandler {
	
	public static final String TEXT_TYPE_PREFIX = "text/";
	public static final String MESSAGE_TYPE_PREFIX = "message/";
	public static final String MULTIPART_TYPE_PREFIX = "multipart/";
	public static final String IMAGE_TYPE_PREFIX = "image/";
	public static final String VIDEO_TYPE_PREFIX = "video/";
	public static final String APPLICATION_TYPE_PREFIX = "application/";
	public static final String AUDIO_TYPE_PREFIX = "audio/";

	// TODO: remove this method, as the ContentHandlerManager should be
	// responsible
	// for the discovery of a relevant ContentHandler
	public boolean accept(String contentType) throws MessageConversionException;

	public void fromPartContent(Part part, MailPart mailPart,
			ContentHandlerManager contentHandlerManager)
			throws MessageConversionException;

	public void toPartContent(MailPart mailPart, Part part,
			Session session, ContentHandlerManager contentHandlerManager)
			throws MessageConversionException;
}
