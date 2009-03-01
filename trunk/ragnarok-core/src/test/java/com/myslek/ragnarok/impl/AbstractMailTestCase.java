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
package com.myslek.ragnarok.impl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.myslek.ragnarok.api.MailSession;
import com.myslek.ragnarok.api.MailSessionFactory;
import com.myslek.ragnarok.api.MessageConverter;
import com.myslek.ragnarok.domain.Content;
import com.myslek.ragnarok.domain.MailAddress;
import com.myslek.ragnarok.domain.MailBox;
import com.myslek.ragnarok.domain.MailMessage;
import com.myslek.ragnarok.domain.MailPart;
import com.myslek.ragnarok.domain.MailServer;
import com.myslek.ragnarok.impl.DefaultMailSessionFactory;
import com.myslek.ragnarok.impl.DefaultMessageConverter;

import junit.framework.TestCase;


// TODO: Auto-generated Javadoc
/**
 * The Class AbstractMailTestCase.
 */
public abstract class AbstractMailTestCase extends TestCase {
	
	/** The mail session factory. */
	private MailSessionFactory mailSessionFactory = new DefaultMailSessionFactory();
	
	/** The message converter. */
	private MessageConverter messageConverter = new DefaultMessageConverter();

	/** The Constant FROM. */
	public static final MailAddress FROM = new MailAddress("from@example.com",
			"From");
	
	/** The Constant TO. */
	public static final MailAddress TO = new MailAddress("to@example.com", "To");
	
	/** The Constant CC1. */
	public static final MailAddress CC1 = new MailAddress("cc1@example.com",
			"Cc1");
	
	/** The Constant CC2. */
	public static final MailAddress CC2 = new MailAddress("cc2@example.com",
			"Cc2");
	
	/** The Constant BCC1. */
	public static final MailAddress BCC1 = new MailAddress("bcc1@example.com",
			"Bcc1");
	
	/** The Constant BCC2. */
	public static final MailAddress BCC2 = new MailAddress("bcc2@example.com",
			"Bcc2");

	/** The Constant SUBJECT. */
	public static final String SUBJECT = "Test MimeMessage";

	/** The Constant TEXT_PLAIN_CONTENT. */
	public static final String TEXT_PLAIN_CONTENT = "This a text/plain contant";

	/** The Constant TEXT_HTML_CONTENT. */
	public static final String TEXT_HTML_CONTENT = "<p>This a text/html content</p>";

	/** The Constant TEXT_PLAIN_TYPE. */
	public static final String TEXT_PLAIN_TYPE = "text/plain; charset=utf-8";

	/** The Constant TEXT_HTML_TYPE. */
	public static final String TEXT_HTML_TYPE = "text/html; charset=utf-8";
	
	/** The Constant MESSAGE_RFC822_TYPE. */
	public static final String MESSAGE_RFC822_TYPE = "message/rfc822";
	
	/** The Constant MULTIPART_MIXED_TYPE. */
	public static final String MULTIPART_MIXED_TYPE = "multipart/mixed";
	
	/** The Constant IMAGE_FILE. */
	public static final String IMAGE_FILE = "image.jpg";
	
	/** The Constant IMAGE_TYPE. */
	public static final String IMAGE_TYPE = "image/jpeg";
	
	/**
	 * Gets the mail session factory.
	 * 
	 * @return the mail session factory
	 */
	public MailSessionFactory getMailSessionFactory() {
		return mailSessionFactory;
	}

	/**
	 * Sets the mail session factory.
	 * 
	 * @param mailSessionFactory the new mail session factory
	 */
	public void setMailSessionFactory(MailSessionFactory mailSessionFactory) {
		this.mailSessionFactory = mailSessionFactory;
	}

	/**
	 * Gets the message converter.
	 * 
	 * @return the message converter
	 */
	public MessageConverter getMessageConverter() {
		return messageConverter;
	}

	/**
	 * Sets the message converter.
	 * 
	 * @param messageConverter the new message converter
	 */
	public void setMessageConverter(MessageConverter messageConverter) {
		this.messageConverter = messageConverter;
	}
	
	/**
	 * Creates the text plain message.
	 * 
	 * @return the message
	 * 
	 * @throws Exception the exception
	 */
	protected Message createTextPlainMessage() throws Exception {
		Message message = createMimeMessage();
		message.setContent(TEXT_PLAIN_CONTENT, TEXT_PLAIN_TYPE);
		message.saveChanges();

		return message;
	}
	
	/**
	 * Creates the text html message.
	 * 
	 * @return the message
	 * 
	 * @throws Exception the exception
	 */
	protected Message createTextHtmlMessage() throws Exception {
		Message message = createMimeMessage();
		message.setContent(TEXT_HTML_CONTENT, TEXT_HTML_TYPE);
		message.saveChanges();

		return message;
	}

	/**
	 * Creates the multipart message.
	 * 
	 * @return the message
	 * 
	 * @throws Exception the exception
	 */
	protected Message createMultipartMessage() throws Exception {
		Message message = createMimeMessage();
		BodyPart part1 = new MimeBodyPart();
		part1.setContent(TEXT_PLAIN_CONTENT, TEXT_PLAIN_TYPE);

		BodyPart part2 = new MimeBodyPart();
		part2.setContent(TEXT_HTML_CONTENT, TEXT_HTML_TYPE);

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(part1);
		multipart.addBodyPart(part2);

		message.setContent(multipart);
		message.saveChanges();

		return message;
	}

	/**
	 * Creates the simple forward message.
	 * 
	 * @return the message
	 * 
	 * @throws Exception the exception
	 */
	protected Message createSimpleForwardMessage() throws Exception {
		Message forward = createMimeMessage();
		BodyPart part1 = new MimeBodyPart();
		part1.setContent(TEXT_PLAIN_CONTENT, TEXT_PLAIN_TYPE);

		Message message = createTextHtmlMessage();
		BodyPart part2 = new MimeBodyPart();
		part2.setContent(message, MESSAGE_RFC822_TYPE);

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(part1);
		multipart.addBodyPart(part2);

		forward.setContent(multipart);
		forward.saveChanges();

		return forward;
	}
	
	/**
	 * Creates the message with image attachment.
	 * 
	 * @return the message
	 * 
	 * @throws Exception the exception
	 */
	protected Message createMessageWithImageAttachment() throws Exception {
		Message message = createMimeMessage();
		BodyPart part1 = new MimeBodyPart();
		part1.setContent(TEXT_PLAIN_CONTENT, TEXT_PLAIN_TYPE);
		
		BodyPart part2 = new MimeBodyPart();
		ByteArrayDataSource ds = new ByteArrayDataSource(getImageBytes(), IMAGE_TYPE);
		part2.setDataHandler(new DataHandler(ds));
		part2.setFileName(IMAGE_FILE);
		
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(part1);
		multipart.addBodyPart(part2);
		
		message.setContent(multipart);
		message.saveChanges();
		
		return message;
	}
	
	/**
	 * Creates the text plain mail message.
	 * 
	 * @return the mail message
	 * 
	 * @throws Exception the exception
	 */
	protected MailMessage createTextPlainMailMessage() throws Exception {
		MailMessage mailMessage = createMailMessage();
		Content content = new Content();
		content.setText(TEXT_PLAIN_CONTENT);
		mailMessage.setContent(content);
		mailMessage.setContentType(TEXT_PLAIN_TYPE);
		
		return mailMessage;
	}
	
	/**
	 * Creates the text html mail message.
	 * 
	 * @return the mail message
	 * 
	 * @throws Exception the exception
	 */
	protected MailMessage createTextHtmlMailMessage() throws Exception {
		MailMessage mailMessage = createMailMessage();
		Content content = new Content();
		content.setText(TEXT_HTML_CONTENT);
		mailMessage.setContent(content);
		mailMessage.setContentType(TEXT_HTML_TYPE);
		
		return mailMessage;
	}
	
	/**
	 * Creates the multipart mail message.
	 * 
	 * @return the mail message
	 * 
	 * @throws Exception the exception
	 */
	protected MailMessage createMultipartMailMessage() throws Exception {
		MailMessage mailMessage = createMailMessage();
		mailMessage.setContentType(MULTIPART_MIXED_TYPE);
		
		MailPart part1 = new MailPart();
		Content content1 = new Content();
		content1.setText(TEXT_PLAIN_CONTENT);
		part1.setContent(content1);
		part1.setContentType(TEXT_PLAIN_TYPE);
		
		MailPart part2 = new MailPart();
		Content content2 = new Content();
		content2.setText(TEXT_HTML_CONTENT);
		part2.setContent(content2);
		part2.setContentType(TEXT_HTML_TYPE);
		
		MailPart multiPart = new MailPart();
		multiPart.setContentType(MULTIPART_MIXED_TYPE);
		multiPart.addPart(part1);
		multiPart.addPart(part2);
		
		mailMessage.addPart(multiPart);
		
		
		return mailMessage;
	}
	
	/**
	 * Creates the simple forward mail message.
	 * 
	 * @return the mail message
	 * 
	 * @throws Exception the exception
	 */
	protected MailMessage createSimpleForwardMailMessage() throws Exception {
		MailMessage mailMessage = createMailMessage();
		mailMessage.setContentType(MULTIPART_MIXED_TYPE);
		
		MailPart part1 = new MailPart();
		Content content1 = new Content();
		content1.setText(TEXT_PLAIN_CONTENT);
		part1.setContent(content1);
		part1.setContentType(TEXT_PLAIN_TYPE);
		
		MailPart part2 = new MailPart();
		part2.setContentType(MESSAGE_RFC822_TYPE);
		part2.addPart(createTextHtmlMailMessage());
		
		MailPart multiPart = new MailPart();
		multiPart.setContentType(MULTIPART_MIXED_TYPE);
		multiPart.addPart(part1);
		multiPart.addPart(part2);
		
		mailMessage.addPart(multiPart);
		
		return mailMessage;
	}
	
	/**
	 * Creates the mail message with image attachment.
	 * 
	 * @return the mail message
	 * 
	 * @throws Exception the exception
	 */
	protected MailMessage createMailMessageWithImageAttachment() throws Exception {
		MailMessage mailMessage = createMailMessage();
		mailMessage.setContentType(MULTIPART_MIXED_TYPE);
		
		MailPart part1 = new MailPart();
		Content content1 = new Content();
		content1.setText(TEXT_PLAIN_CONTENT);
		part1.setContent(content1);
		part1.setContentType(TEXT_PLAIN_TYPE);
		
		MailPart part2 = new MailPart();
		Content content2 = new Content();
		part2.setContentType(IMAGE_TYPE);
		part2.setFileName(IMAGE_FILE);
		part2.setDisposition(Part.ATTACHMENT);
		content2.setData(getImageBytes());
		part2.setContent(content2);
		
		MailPart multiPart = new MailPart();
		multiPart.setContentType(MULTIPART_MIXED_TYPE);
		multiPart.addPart(part1);
		multiPart.addPart(part2);
		
		mailMessage.addPart(multiPart);
		
		return mailMessage;
	}

	/**
	 * Creates the mime message.
	 * 
	 * @return the message
	 * 
	 * @throws Exception the exception
	 */
	protected Message createMimeMessage() throws Exception {
		MailSession mailSession = createMailSession();
		Message message = new MimeMessage(mailSession.getSession());

		Address from = new InternetAddress(FROM.getAddress(), FROM
				.getPersonal());
		Address to = new InternetAddress(TO.getAddress(), TO.getPersonal());
		Address cc1 = new InternetAddress(CC1.getAddress(), CC1.getPersonal());
		Address cc2 = new InternetAddress(CC2.getAddress(), CC2.getPersonal());
		Address bcc1 = new InternetAddress(BCC1.getAddress(), BCC1
				.getPersonal());
		Address bcc2 = new InternetAddress(BCC2.getAddress(), BCC2
				.getPersonal());

		message.setFrom(from);
		message.addRecipient(RecipientType.TO, to);
		message.addRecipient(RecipientType.CC, cc1);
		message.addRecipient(RecipientType.CC, cc2);
		message.addRecipient(RecipientType.BCC, bcc1);
		message.addRecipient(RecipientType.BCC, bcc2);

		message.setSubject(SUBJECT);

		return message;

	}
	
	/**
	 * Creates the mail message.
	 * 
	 * @return the mail message
	 * 
	 * @throws Exception the exception
	 */
	protected MailMessage createMailMessage() throws Exception {
		MailMessage mailMessage = new MailMessage();
		mailMessage.setFrom(FROM);
		mailMessage.addRecipient(com.myslek.ragnarok.domain.RecipientType.TO, TO);
		mailMessage.addRecipient(com.myslek.ragnarok.domain.RecipientType.CC, CC1);
		mailMessage.addRecipient(com.myslek.ragnarok.domain.RecipientType.CC, CC2);
		mailMessage.addRecipient(com.myslek.ragnarok.domain.RecipientType.BCC, BCC1);
		mailMessage.addRecipient(com.myslek.ragnarok.domain.RecipientType.BCC, BCC2);
		
		mailMessage.setSubject(SUBJECT);
		
		return mailMessage;
	}

	/**
	 * Creates the mail box.
	 * 
	 * @return the mail box
	 * 
	 * @throws Exception the exception
	 */
	protected MailBox createMailBox() throws Exception {
		MailBox mailBox = new MailBox();
		
		MailServer mailStore = new MailServer();
		mailStore.setProtocol("pop3");
		mailStore.setHost("pop3.example.com");
		mailStore.setUsername("pop3_user");
		mailStore.setPassword("pop3_user");
		mailStore.setDefaultFolder("inbox");
		mailBox.setMailStore(mailStore);
		
		MailServer mailTransport = new MailServer();
		mailTransport.setProtocol("smtp");
		mailTransport.setHost("smtp.example.com");
		mailTransport.setUsername("smtp_user");
		mailTransport.setPassword("smtp_user");
		mailBox.setMailTransport(mailTransport);

		return mailBox;
	}
	
	/**
	 * Creates the mail session.
	 * 
	 * @return the mail session
	 * 
	 * @throws Exception the exception
	 */
	protected MailSession createMailSession() throws Exception {
		MailBox mailBox = createMailBox();
		String mailStoreProtocol = mailBox.getMailStore().getProtocol();
		MailSession session = getMailSessionFactory()
				.createMailSession(mailStoreProtocol);
		return session;
	}
	
	/**
	 * Gets the image bytes.
	 * 
	 * @return the image bytes
	 * 
	 * @throws Exception the exception
	 */
	protected byte[] getImageBytes() throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		InputStream in = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(IMAGE_FILE);
		byte[] buffer = new byte[4 * 1024];
		int len;
		while ((len = in.read(buffer)) != -1) {
			out.write(buffer, 0, len);
		}
		return out.toByteArray();
	}
}
