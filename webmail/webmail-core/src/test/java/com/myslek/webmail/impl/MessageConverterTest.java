package com.myslek.webmail.impl;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import junit.framework.TestCase;

import com.myslek.webmail.api.MailSession;
import com.myslek.webmail.api.MailSessionFactory;
import com.myslek.webmail.api.MessageConverter;
import com.myslek.webmail.domain.MailAddress;
import com.myslek.webmail.domain.MailBox;
import com.myslek.webmail.domain.MailMessage;
import com.myslek.webmail.domain.MailServer;

public class MessageConverterTest extends TestCase {

	private MailSessionFactory mailSessionFactory = new DefaultMailSessionFactory();
	private MessageConverter messageConverter = new DefaultMessageConverter();
	
	public static final MailAddress FROM = new MailAddress("from@example.com", "From");
	public static final MailAddress TO = new MailAddress("to@example.com", "To");
	public static final MailAddress CC1 = new MailAddress("cc1@example.com", "Cc1");
	public static final MailAddress CC2 = new MailAddress("cc2@example.com", "Cc2");
	public static final MailAddress BCC1 = new MailAddress("bcc1@example.com", "Bcc1");
	public static final MailAddress BCC2 = new MailAddress("bcc2@example.com", "Bcc2");
	
	public static final String SUBJECT = "Test MimeMessage";
	

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
	
	//start test methods

	public void testConvertFromTextPlainMessage() throws Exception {
		Message message = createTextPlainMessage();
		MailMessage mailMessage = getMessageConverter().fromMessage(message);
		
		assertNotNull(mailMessage);
		assertEquals(message.getContentType(), mailMessage.getContentType());
		assertEquals(message.getSubject(), mailMessage.getSubject());
		assertEquals(SUBJECT, mailMessage.getSubject());
		assertEquals((String) message.getContent(), (String) mailMessage.getContent());
		assertEquals("This is a text/plain content", (String) mailMessage.getContent());
	}
	
	//end test methods

	protected Message createTextPlainMessage() throws Exception {
		Message message = createMimeMessage();
		String content = "This is a text/plain content";
		String contentType = "text/plain";
		message.setContent(content, contentType);
		message.saveChanges();
		
		return message;
	}

	protected Message createMimeMessage() throws Exception {
		MailBox mailBox = createMailBox();
		MailSession session = getMailSessionFactory().createMailSession(mailBox);
		Message message = new MimeMessage(session.getSession());
		
		Address from = new InternetAddress(FROM.getAddress(), FROM.getPersonal());
		Address to = new InternetAddress(TO.getAddress(), TO.getPersonal());
		Address cc1 = new InternetAddress(CC1.getAddress(), CC1.getPersonal());
		Address cc2 = new InternetAddress(CC2.getAddress(), CC2.getPersonal());
		Address bcc1 = new InternetAddress(BCC1.getAddress(), BCC1.getPersonal());
		Address bcc2 = new InternetAddress(BCC2.getAddress(), BCC2.getPersonal());
		
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
