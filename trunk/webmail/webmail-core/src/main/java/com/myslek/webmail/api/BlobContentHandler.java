package com.myslek.webmail.api;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.activation.DataHandler;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.util.ByteArrayDataSource;

import com.myslek.webmail.domain.Content;
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
		InputStream in = null;
		try {
			in = part.getInputStream();
			Content content = new Content();
			content.setData(getBytes(in));
			mailPart.setContent(content);
			mailPart.setFileName(part.getFileName());
		} catch (IOException e) {
			throw new MessageConversionException(e);
		} catch (MessagingException e) {
			throw new MessageConversionException(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					throw new MessageConversionException(e);
				}
			}
		}
	}

	public void toPartContent(MailPart mailPart, Part part,
			Session session, ContentHandlerManager contentHandlerManager)
			throws MessageConversionException {
		try {
			ByteArrayDataSource ds = new ByteArrayDataSource(
					mailPart.getContent().getData(), mailPart.getContentType());
			part.setDataHandler(new DataHandler(ds));
			part.setFileName(mailPart.getFileName());
		} catch (MessagingException e) {
			throw new MessageConversionException(e);
		}
	}
	
	protected byte[] getBytes(InputStream in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[4 * 1024];
		int len;
		while ((len = in.read(buffer)) != -1) {
			out.write(buffer, 0, len);
		}
		
		return out.toByteArray();
	}
}