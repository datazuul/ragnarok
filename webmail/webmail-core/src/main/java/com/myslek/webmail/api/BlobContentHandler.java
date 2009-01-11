package com.myslek.webmail.api;

import javax.mail.Part;

import com.myslek.webmail.domain.MailPart;

public class BlobContentHandler extends AbstractContentHandler {
	
	public static final String IMAGE_TYPE_PREFIX = "image/";
	public static final String VIDEO_TYPE_PREFIX = "video/";
	public static final String APPLICATION_TYPE_PREFIX = "application/";
	public static final String AUDIO_TYPE_PREFIX = "audio/";

	public boolean accept(String contentType) throws MessageConversionException {
		return contentType.startsWith(IMAGE_TYPE_PREFIX)
				|| contentType.startsWith(VIDEO_TYPE_PREFIX)
				|| contentType.startsWith(APPLICATION_TYPE_PREFIX)
				|| contentType.startsWith(AUDIO_TYPE_PREFIX);
	}

	public void fromPartContent(Part part, MailPart mailPart,
			ContentHandlerManager contentHandlerManager)
			throws MessageConversionException {
	}

	public void toPartContent(MailPart mailPart, Part part,
			ContentHandlerManager contentHandlerManager)
			throws MessageConversionException {
	}
}
