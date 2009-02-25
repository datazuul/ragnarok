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

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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

import junit.framework.Assert;
import junit.framework.TestCase;

import com.myslek.webmail.api.MailSession;
import com.myslek.webmail.api.MailSessionFactory;
import com.myslek.webmail.api.MessageConverter;
import com.myslek.webmail.domain.Content;
import com.myslek.webmail.domain.MailAddress;
import com.myslek.webmail.domain.MailBox;
import com.myslek.webmail.domain.MailMessage;
import com.myslek.webmail.domain.MailPart;
import com.myslek.webmail.domain.MailServer;
import com.myslek.webmail.util.IOUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class MessageConverterTest.
 */
public class MessageConverterTestCase extends TestCase {

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

	//============================= TESTS =============================

	/**
	 * Test convert from text plain message.
	 * 
	 * @throws Exception the exception
	 */
	public void testConvertFromTextPlainMessage() throws Exception {
		Message message = createTextPlainMessage();
		MailMessage mailMessage = getMessageConverter().fromMessage(message);

		Assert.assertNotNull(mailMessage);
		Assert.assertEquals(message.getContentType(), mailMessage.getContentType());
		Assert.assertEquals(message.getSubject(), mailMessage.getSubject());
		Assert.assertEquals(SUBJECT, mailMessage.getSubject());
		Assert.assertEquals((String) message.getContent(), (String) mailMessage.getContent().getText());
		Assert.assertEquals(TEXT_PLAIN_CONTENT, (String) mailMessage.getContent().getText());
	}

	/**
	 * Test convert from multipart message.
	 * 
	 * @throws Exception the exception
	 */
	public void testConvertFromMultipartMessage() throws Exception {
		Message message = createMultipartMessage();
		MailMessage mailMessage = getMessageConverter().fromMessage(message);

		Assert.assertNotNull(mailMessage);
		Assert.assertEquals("ContentType is both messages must be the same", message.getContentType(), 
				mailMessage.getContentType());
		Assert.assertEquals(SUBJECT, mailMessage.getSubject());
		Assert.assertNotNull("MailMessage parts must not be null", mailMessage.getParts());
		Assert.assertEquals("Expected MailMessage parts size: 1", 1, mailMessage.getParts().size());

		MailPart multiPart = mailMessage.getParts().get(0);
		Assert.assertNotNull("MailMessage multiPart must not be null", multiPart);
		Assert.assertNotNull("MailMessage multiPart parent must not be null", multiPart.getParent());
		Assert.assertSame("MailMessage multiPart parent must be mailMessage object",
				mailMessage, multiPart.getParent());
		Assert.assertEquals("Expected MailMessage multiPart parts size: 2", 2, multiPart.getParts().size());
		
		MailPart part1 = multiPart.getParts().get(0);
		Assert.assertNotNull("First part of the MailMessage multiPart must not be null", part1);
		Assert.assertEquals("Expected contentType of 1st part of the MailMessage multiPart is: "
						+ TEXT_PLAIN_TYPE, TEXT_PLAIN_TYPE, part1.getContentType());
		Assert.assertEquals("Expected content of the 1st part of the MailMessage multiPart is: "
						+ TEXT_PLAIN_CONTENT, TEXT_PLAIN_CONTENT, (String) part1.getContent().getText());
		MailPart part2 = multiPart.getParts().get(1);
		Assert.assertNotNull("Second part of the MailMessage multiPart must not be null", part2);
		Assert.assertEquals("Expected contentType of the 2nd part of the MailMessage multiPart is: "
						+ TEXT_HTML_TYPE, TEXT_HTML_TYPE, part2.getContentType());
		Assert.assertEquals("Expected content of the 2nd part of the MailMessage multiPart is: "
						+ TEXT_HTML_CONTENT, TEXT_HTML_CONTENT, (String) part2.getContent().getText());
	}

	/**
	 * Test convert from simple forward message.
	 * 
	 * @throws Exception the exception
	 */
	public void testConvertFromSimpleForwardMessage() throws Exception {
		Message message = createSimpleForwardMessage();
		MailMessage mailMessage = getMessageConverter().fromMessage(message);

		Assert.assertNotNull("MailMessage must not be null", mailMessage);
		Assert.assertTrue("Expected MailMessage contentType is: multipart/mixed", 
				mailMessage.isMimeType("multipart/mixed"));
		Assert.assertEquals("Expected MailMessage parts size is: 1", 1, mailMessage.getParts().size());
		
		MailPart multiPart = mailMessage.getParts().get(0);
		Assert.assertNotNull("MultiPart object must not be null", multiPart);
		
		MailPart part1 = multiPart.getParts().get(0);
		Assert.assertNotNull("Part1 object must not be null", part1);
		Assert.assertTrue("Expected contentType of part1 object is: text/plain", part1.isMimeType("text/plain"));
		Assert.assertEquals("Expected content of part1 object is: " + TEXT_PLAIN_CONTENT, TEXT_PLAIN_CONTENT, 
				part1.getContent().getText());
		
		MailPart part2 = multiPart.getParts().get(1);
		Assert.assertNotNull("Part2 object must not be null", part2);
		Assert.assertTrue("Expected contentType of part2 object is: message/rfc822", 
				part2.isMimeType("message/rfc822"));
		
		MailPart part2_1 = part2.getParts().get(0);
		Assert.assertNotNull("Part2_1 object must not be null", part2_1);
		Assert.assertTrue("Expected type of part2_1 is MailMessage", part2_1 instanceof MailMessage);
		MailMessage forward = (MailMessage) part2_1;
		Assert.assertNotNull("Forward subject must not be null", forward.getSubject());
		Assert.assertEquals("Expected forward subject: " + SUBJECT, SUBJECT, forward.getSubject());
		Assert.assertTrue("Expected contentType of the part2_1 object is: text/html", 
				part2_1.isMimeType("text/html"));
		Assert.assertEquals("Expected content of part2_1 object is: " + TEXT_HTML_CONTENT, TEXT_HTML_CONTENT, 
				part2_1.getContent().getText());
	}
	
	/**
	 * Test convert from message with image attachment.
	 * 
	 * @throws Exception the exception
	 */
	public void testConvertFromMessageWithImageAttachment() throws Exception {
		Message message = createMessageWithImageAttachment();
		MailMessage mailMessage = getMessageConverter().fromMessage(message);
		
		Assert.assertNotNull("MailMessage must not be null", mailMessage);
		Assert.assertTrue("MailMessage contentType should be multipart/mixed", 
				mailMessage.isMimeType("multipart/mixed"));
		
		MailPart multiPart = mailMessage.getParts().get(0);
		Assert.assertNotNull("MultiPart must not be null", multiPart);
		Assert.assertEquals("MultiPart size should be: 2", 2, multiPart.getParts().size());
		
		MailPart part1 = multiPart.getParts().get(0);
		Assert.assertNotNull("Part1 must not be null", part1);
		Assert.assertTrue("Part1 contentType should be: text/plain", part1.isMimeType("text/plain"));
		Assert.assertEquals("Part1 content should be: " + TEXT_PLAIN_CONTENT, 
				TEXT_PLAIN_CONTENT, part1.getContent().getText());
		
		MailPart part2 = multiPart.getParts().get(1);
		Assert.assertNotNull("Part2 must not be null", part2);
		Assert.assertTrue("Part2 contentType should be: image/jpeg", part2.isMimeType("image/jpeg"));
		Assert.assertEquals("Part2 filename should be: image.jpeg", IMAGE_FILE, part2.getFileName());
		Assert.assertEquals("Part2 content.getData().length == getImageBytes().length", 
				getImageBytes().length, part2.getContent().getData().length);
	}
	
	/**
	 * Test convert to plain text message.
	 * 
	 * @throws Exception the exception
	 */
	public void testConvertToPlainTextMessage() throws Exception {
		MailMessage mailMessage = createTextPlainMailMessage();
		MailSession mailSession = createMailSession();
		Message message = getMessageConverter().toMessage(mailMessage, mailSession.getSession());
		
		Assert.assertNotNull("Message must not be null", message);
		Assert.assertTrue("Message contentType should be: text/plain", message.isMimeType("text/plain"));
		Assert.assertTrue("Message content should a string", message.getContent() instanceof String);
		Assert.assertEquals("Message content should be: " + TEXT_PLAIN_CONTENT, 
				TEXT_PLAIN_CONTENT, (String) message.getContent());
		Assert.assertNotNull("Message subject must not be null", message.getSubject());
		Assert.assertEquals("Message subject should be: " + SUBJECT, SUBJECT, message.getSubject());
	}
	
	/**
	 * Test convert to multipart message.
	 * 
	 * @throws Exception the exception
	 */
	public void testConvertToMultipartMessage() throws Exception {
		MailMessage mailMessage = createMultipartMailMessage();
		MailSession mailSession = createMailSession();
		Message message = getMessageConverter().toMessage(mailMessage, mailSession.getSession());
		
		Assert.assertNotNull("Message must not be null", message);
		Assert.assertTrue("Message contentType should be: " + MULTIPART_MIXED_TYPE, 
				message.isMimeType(MULTIPART_MIXED_TYPE));
		Assert.assertTrue("Message content should be Multipart", message.getContent() instanceof Multipart);
		
		Multipart multipart = (Multipart) message.getContent();
		Assert.assertNotNull("Multipart must not be null", multipart);
		Assert.assertEquals("Expected Multipart size: 2", 2, multipart.getCount());
		
		BodyPart part1 = multipart.getBodyPart(0);
		Assert.assertNotNull("Part1 must not be null", part1);
		Assert.assertTrue("Part1 contentType should be: " + TEXT_PLAIN_TYPE, 
				part1.isMimeType(TEXT_PLAIN_TYPE));
		Assert.assertEquals("Part1 content should be:" + TEXT_PLAIN_CONTENT, 
				TEXT_PLAIN_CONTENT, (String) part1.getContent());
		
		BodyPart part2 = multipart.getBodyPart(1);
		Assert.assertNotNull("Part2 must not be null", part1);
		Assert.assertTrue("Part2 contentType should be: " + TEXT_HTML_TYPE, 
				part2.isMimeType(TEXT_HTML_TYPE));
		Assert.assertEquals("Part2 content should be:" + TEXT_HTML_CONTENT, 
				TEXT_HTML_CONTENT, (String) part2.getContent());
	}
	
	/**
	 * Test convert to simple forward message.
	 * 
	 * @throws Exception the exception
	 */
	public void testConvertToSimpleForwardMessage() throws Exception {
		MailMessage mailMessage = createSimpleForwardMailMessage();
		MailSession mailSession = createMailSession();
		Message message = getMessageConverter().toMessage(mailMessage, mailSession.getSession());
		
		Assert.assertNotNull("Message must not be null", message);
		Assert.assertTrue("Message contentType should be: " + MULTIPART_MIXED_TYPE, 
				message.isMimeType(MULTIPART_MIXED_TYPE));
		Assert.assertTrue("Message content should be Multipart", message.getContent() instanceof Multipart);
		
		Multipart multipart = (Multipart) message.getContent();
		Assert.assertNotNull("Multipart must not be null", multipart);
		Assert.assertEquals("Expected Multipart size: 2", 2, multipart.getCount());
		
		BodyPart part1 = multipart.getBodyPart(0);
		Assert.assertNotNull("Part1 must not be null", part1);
		Assert.assertTrue("Part1 contentType should be: " + TEXT_PLAIN_TYPE, 
				part1.isMimeType(TEXT_PLAIN_TYPE));
		Assert.assertEquals("Part1 content should be:" + TEXT_PLAIN_CONTENT, 
				TEXT_PLAIN_CONTENT, (String) part1.getContent());
		
		BodyPart part2 = multipart.getBodyPart(1);
		Assert.assertNotNull("Part2 must not be null", part1);
		Assert.assertTrue("Part2 contentType should be: " + MESSAGE_RFC822_TYPE, 
				part2.isMimeType(MESSAGE_RFC822_TYPE));	
		Assert.assertTrue("Part2 content should be of type javax.mail.Message", 
				part2.getContent() instanceof Message);
		Message forward = (Message) part2.getContent();
		Assert.assertNotNull("Forward must not be null", forward);
		Assert.assertTrue("Forward contentType should be: " + TEXT_HTML_TYPE, 
				forward.isMimeType(TEXT_HTML_TYPE));
		Assert.assertEquals("Forward content should be: " + TEXT_HTML_CONTENT, 
				TEXT_HTML_CONTENT, (String) forward.getContent());
	}
	
	/**
	 * Test convert to message with image attachment.
	 * 
	 * @throws Exception the exception
	 */
	public void testConvertToMessageWithImageAttachment() throws Exception {
		MailMessage mailMessage = createMailMessageWithImageAttachment();
		MailSession mailSession = createMailSession();
		Message message = getMessageConverter().toMessage(mailMessage, mailSession.getSession());
		
		Assert.assertNotNull("Message must not be null", message);
		Assert.assertTrue("Message contentType should be: " + MULTIPART_MIXED_TYPE, 
				message.isMimeType(MULTIPART_MIXED_TYPE));
		Assert.assertTrue("Message content should be Multipart", message.getContent() instanceof Multipart);
		
		Multipart multipart = (Multipart) message.getContent();
		Assert.assertNotNull("Multipart must not be null", multipart);
		Assert.assertEquals("Expected Multipart size: 2", 2, multipart.getCount());
		
		BodyPart part1 = multipart.getBodyPart(0);
		Assert.assertNotNull("Part1 must not be null", part1);
		Assert.assertTrue("Part1 contentType should be: " + TEXT_PLAIN_TYPE, 
				part1.isMimeType(TEXT_PLAIN_TYPE));
		Assert.assertEquals("Part1 content should be:" + TEXT_PLAIN_CONTENT, 
				TEXT_PLAIN_CONTENT, (String) part1.getContent());
		
		BodyPart part2 = multipart.getBodyPart(1);
		Assert.assertNotNull("Part2 must not be null", part1);
		Assert.assertTrue("Part2 contentType should be: " + IMAGE_TYPE, 
				part2.isMimeType(IMAGE_TYPE));	
		Assert.assertEquals("Part2 bytes length == getImageBytes().length", 
				getImageBytes().length, IOUtils.getBytes(part2.getInputStream()).length);
	}

	//=================================================================

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
		List<MailAddress> to = new ArrayList<MailAddress>();
		to.add(TO);
		mailMessage.setTo(to);
		List<MailAddress> cc = new ArrayList<MailAddress>();
		cc.add(CC1);
		cc.add(CC2);
		mailMessage.setCc(cc);
		List<MailAddress> bcc = new ArrayList<MailAddress>();
		bcc.add(BCC1);
		bcc.add(BCC2);
		mailMessage.setBcc(bcc);
		
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
		mailBox.setMailStore(mailStore);

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
