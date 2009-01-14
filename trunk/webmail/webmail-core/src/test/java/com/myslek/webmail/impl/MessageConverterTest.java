package com.myslek.webmail.impl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
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
import com.myslek.webmail.domain.MailAddress;
import com.myslek.webmail.domain.MailBox;
import com.myslek.webmail.domain.MailMessage;
import com.myslek.webmail.domain.MailPart;
import com.myslek.webmail.domain.MailServer;

public class MessageConverterTest extends TestCase {

	private MailSessionFactory mailSessionFactory = new DefaultMailSessionFactory();
	private MessageConverter messageConverter = new DefaultMessageConverter();

	public static final MailAddress FROM = new MailAddress("from@example.com",
			"From");
	public static final MailAddress TO = new MailAddress("to@example.com", "To");
	public static final MailAddress CC1 = new MailAddress("cc1@example.com",
			"Cc1");
	public static final MailAddress CC2 = new MailAddress("cc2@example.com",
			"Cc2");
	public static final MailAddress BCC1 = new MailAddress("bcc1@example.com",
			"Bcc1");
	public static final MailAddress BCC2 = new MailAddress("bcc2@example.com",
			"Bcc2");

	public static final String SUBJECT = "Test MimeMessage";

	public static final String TEXT_PLAIN_CONTENT = "This a text/plain contant";

	public static final String TEXT_HTML_CONTENT = "<p>This a text/html content</p>";

	public static final String TEXT_PLAIN_TYPE = "text/plain; charset=utf-8";

	public static final String TEXT_HTML_TYPE = "text/html; charset=utf-8";
	
	public static final String MESSAGE_RFC822_TYPE = "message/rfc822";
	
	public static final String IMAGE_FILE = "image.jpg";
	public static final String IMAGE_TYPE = "image/jpeg";

	public MailSessionFactory getMailSessionFactory() {
		return mailSessionFactory;
	}

	public void setMailSessionFactory(MailSessionFactory mailSessionFactory) {
		this.mailSessionFactory = mailSessionFactory;
	}

	public MessageConverter getMessageConverter() {
		return messageConverter;
	}

	public void setMessageConverter(MessageConverter messageConverter) {
		this.messageConverter = messageConverter;
	}

	//============================= TESTS =============================

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

	//=================================================================

	protected Message createTextPlainMessage() throws Exception {
		Message message = createMimeMessage();
		message.setContent(TEXT_PLAIN_CONTENT, TEXT_PLAIN_TYPE);
		message.saveChanges();

		return message;
	}
	
	protected Message createTextHtmlMessage() throws Exception {
		Message message = createMimeMessage();
		message.setContent(TEXT_HTML_CONTENT, TEXT_HTML_TYPE);
		message.saveChanges();

		return message;
	}

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

	protected Message createMimeMessage() throws Exception {
		MailBox mailBox = createMailBox();
		MailSession session = getMailSessionFactory()
				.createMailSession(mailBox);
		Message message = new MimeMessage(session.getSession());

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

	protected MailBox createMailBox() throws Exception {
		MailBox mailBox = new MailBox();
		MailServer mailStore = new MailServer();
		mailStore.setProtocol("pop3");
		mailBox.setMailStore(mailStore);

		return mailBox;
	}
	
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
