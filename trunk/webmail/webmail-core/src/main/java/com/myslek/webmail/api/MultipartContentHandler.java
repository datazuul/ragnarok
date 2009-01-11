package com.myslek.webmail.api;

import java.io.IOException;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import com.myslek.webmail.domain.MailPart;

public class MultipartContentHandler extends AbstractContentHandler {
	
	public static final String MULTIPART_TYPE_PREFIX = "multipart/";

	public boolean accept(String contentType) throws MessageConversionException {
		return contentType.startsWith(MULTIPART_TYPE_PREFIX);
	}

	public void fromPartContent(Part part, MailPart mailPart,
			ContentHandlerManager contentHandlerManager)
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

				// pass the body part to a specialized 'ContentHandler' object
				contentHandlerManager.fromPartContent(body, bodyPart);
			}
		} catch (IOException e) {
			throw new MessageConversionException(e);
		} catch (MessagingException e) {
			throw new MessageConversionException(e);
		}
	}

	public void toPartContent(MailPart mailPart, Part part,
			ContentHandlerManager contentHandlerManager)
			throws MessageConversionException {
		try {
			Multipart multipart = new MimeMultipart();

			for (MailPart body : mailPart.getParts()) {
				BodyPart bodyPart = new MimeBodyPart();
				contentHandlerManager.toPartContent(body, bodyPart);

				multipart.addBodyPart(bodyPart);
			}
			part.setContent(multipart);
		} catch (MessagingException e) {
			throw new MessageConversionException(e);
		}
	}
}
