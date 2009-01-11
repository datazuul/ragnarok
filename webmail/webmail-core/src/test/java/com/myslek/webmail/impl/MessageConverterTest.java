package com.myslek.webmail.impl;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

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

	// start test methods

	public void testConvertFromTextPlainMessage() throws Exception {
		Message message = createTextPlainMessage();
		//convert message
		MailMessage mailMessage = getMessageConverter().fromMessage(message);

		Assert.assertNotNull(mailMessage);
		Assert.assertEquals(message.getContentType(), mailMessage
				.getContentType());
		Assert.assertEquals(message.getSubject(), mailMessage.getSubject());
		Assert.assertEquals(SUBJECT, mailMessage.getSubject());
		Assert.assertEquals((String) message.getContent(), (String) mailMessage
				.getContent().getText());
		Assert.assertEquals(TEXT_PLAIN_CONTENT, (String) mailMessage
				.getContent().getText());
	}

	public void testConvertFromMultipartMessage() throws Exception {
		Message message = createMultipartMessage();
		//convert message
		MailMessage mailMessage = getMessageConverter().fromMessage(message);

		Assert.assertNotNull(mailMessage);
		Assert.assertEquals("ContentType is both messages must be the same",
				message.getContentType(), mailMessage.getContentType());
		Assert.assertEquals(SUBJECT, mailMessage.getSubject());
		Assert.assertNotNull("MailMessage parts must not be null", mailMessage
				.getParts());
		Assert.assertEquals("Expected MailMessage parts size: 1", 1,
				mailMessage.getParts().size());

		MailPart multiPart = mailMessage.getParts().get(0);
		Assert.assertNotNull("MailMessage multiPart must not be null",
				multiPart);
		Assert.assertNotNull("MailMessage multiPart parent must not be null",
				multiPart.getParent());
		Assert.assertSame(
				"MailMessage multiPart parent must be mailMessage object",
				mailMessage, multiPart.getParent());
		Assert.assertEquals("Expected MailMessage multiPart parts size: 2", 2,
				multiPart.getParts().size());
		MailPart part1 = multiPart.getParts().get(0);
		Assert.assertNotNull(
				"First part of the MailMessage multiPart must not be null",
				part1);
		Assert.assertEquals(
				"Expected contentType of 1st part of the MailMessage multiPart is: "
						+ TEXT_PLAIN_TYPE, TEXT_PLAIN_TYPE, part1
						.getContentType());
		Assert.assertEquals(
				"Expected content of the 1st part of the MailMessage multiPart is: "
						+ TEXT_PLAIN_CONTENT, TEXT_PLAIN_CONTENT,
				(String) part1.getContent().getText());
		MailPart part2 = multiPart.getParts().get(1);
		Assert.assertNotNull(
				"Second part of the MailMessage multiPart must not be null",
				part2);
		Assert.assertEquals(
				"Expected contentType of the 2nd part of the MailMessage multiPart is: "
						+ TEXT_HTML_TYPE, TEXT_HTML_TYPE, part2
						.getContentType());
		Assert.assertEquals(
				"Expected content of the 2nd part of the MailMessage multiPart is: "
						+ TEXT_HTML_CONTENT, TEXT_HTML_CONTENT, (String) part2
						.getContent().getText());
	}

	// end test methods

	protected Message createTextPlainMessage() throws Exception {
		Message message = createMimeMessage();
		message.setContent(TEXT_PLAIN_CONTENT, TEXT_PLAIN_TYPE);
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
}
