package com.myslek.webmail.api;

import java.io.IOException;
import java.io.InputStream;

import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.Session;

import com.myslek.webmail.domain.MailPart;

public class BlobContentHandler extends AbstractContentHandler {

	public boolean accept(String contentType) throws MessageConversionException {
		return contentType.startsWith(MailPart.IMAGE_TYPE_PREFIX)
				|| contentType.startsWith(MailPart.VIDEO_TYPE_PREFIX)
				|| contentType.startsWith(MailPart.APPLICATION_TYPE_PREFIX)
				|| contentType.startsWith(MailPart.AUDIO_TYPE_PREFIX);
	}

	public void fromPartContent(Part part, MailPart mailPart,
			ContentHandlerManager contentHandlerManager)
			throws MessageConversionException {
		InputStream is = null;
		try {
			is = part.getInputStream();
		} catch (IOException e) {
			throw new MessageConversionException(e);
		} catch (MessagingException e) {
			throw new MessageConversionException(e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					throw new MessageConversionException(e);
				}
			}
		}
	}

	public void toPartContent(MailPart mailPart, Part part,
			Session session, ContentHandlerManager contentHandlerManager)
			throws MessageConversionException {
	}
}