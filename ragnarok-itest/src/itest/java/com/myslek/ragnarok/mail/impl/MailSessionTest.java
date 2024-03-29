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
package com.myslek.ragnarok.mail.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.mail.internet.InternetAddress;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.myslek.ragnarok.domain.MailBox;
import com.myslek.ragnarok.domain.MailMessage;
import com.myslek.ragnarok.domain.MailServer;
import com.myslek.ragnarok.domain.MailServerProtocol;
import com.myslek.ragnarok.mail.MailSession;
import com.myslek.ragnarok.mail.MailSessionFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class MailSessionTestCase.
 */
public class MailSessionTest extends TestCase {

	/** The mail session factory. */
	private MailSessionFactory mailSessionFactory = new DefaultMailSessionFactory();

	/** The mailbox. */
	private static Properties mailbox = new Properties();

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

	private static final long SLEEP_TIME = 3000L;

	static {
		try {
			InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(
					"mailbox.properties");
			mailbox.load(input);
		} catch (IOException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	/**
	 * Test send message.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public void testSendMessage() throws Exception {
		MailBox mailBox = createMailBox(mailbox.getProperty("mailbox.host"), mailbox
				.getProperty("mailbox.0.to.user"), mailbox.getProperty("mailbox.0.to.password"));
		MailSession mailSession = createMailSession();

		InternetAddress from = new InternetAddress(mailbox.getProperty("mailbox.address"), mailbox
				.getProperty("mailbox.personal"));
		InternetAddress to = new InternetAddress(mailbox.getProperty("mailbox.0.to.address"), mailbox
				.getProperty("mailbox.0.to.address"));
		MailMessage mailMessage = createTextPlainMailMessage(from, to, "testSendMessage");

		mailSession.sendMessage(mailBox, mailMessage);
		Thread.sleep(SLEEP_TIME);
		List<MailMessage> messages = mailSession.fetchMessages(mailBox, Collections.EMPTY_LIST,
				null, true);
		Assert.assertEquals("Expected messages size: 1", 1, messages.size());
		MailMessage message = messages.get(0);
		Assert.assertNotNull("Message must not be null", message);
		Assert.assertEquals("Expected from: " + mailbox.getProperty("mailbox.address"), mailbox
				.getProperty("mailbox.address"), message.getFromList().get(0).getAddress());
		Assert.assertNotNull("Message uid must not be null", message.getUid());
	}

	/**
	 * Test fetch messages.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public void testFetchMessages() throws Exception {
		MailBox mailBox = createMailBox(mailbox.getProperty("mailbox.host"), mailbox
				.getProperty("mailbox.1.to.user"), mailbox.getProperty("mailbox.1.to.password"));
		MailSession mailSession = createMailSession();
		InternetAddress from = new InternetAddress(mailbox.getProperty("mailbox.address"), mailbox
				.getProperty("mailbox.personal"));
		InternetAddress to = new InternetAddress(mailbox.getProperty("mailbox.1.to.address"), mailbox
				.getProperty("mailbox.1.to.address"));

		final int MESSAGE_COUNT = 10;

		for (int i = 0; i < MESSAGE_COUNT; i++) {
			mailSession.sendMessage(mailBox, createTextPlainMailMessage(from, to,
					"testFetchMessages"));
			Thread.sleep(SLEEP_TIME);
		}

		List<MailMessage> messages = mailSession.fetchMessages(mailBox, Collections.EMPTY_LIST,
				null, true);
		Assert.assertEquals("Expected messages size: " + MESSAGE_COUNT, MESSAGE_COUNT, messages
				.size());
	}

	/**
	 * Test fetch new messages.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public void testFetchNewMessages() throws Exception {
		MailBox mailBox = createMailBox(mailbox.getProperty("mailbox.host"), mailbox
				.getProperty("mailbox.2.to.user"), mailbox.getProperty("mailbox.2.to.password"));
		MailSession mailSession = createMailSession();

		InternetAddress from = new InternetAddress(mailbox.getProperty("mailbox.address"), mailbox
				.getProperty("mailbox.personal"));
		InternetAddress to = new InternetAddress(mailbox.getProperty("mailbox.2.to.address"), mailbox
				.getProperty("mailbox.2.to.address"));

		final int MESSAGE_COUNT = 10;

		for (int i = 0; i < MESSAGE_COUNT; i++) {
			mailSession.sendMessage(mailBox, createTextPlainMailMessage(from, to,
					"testFetchNewMessages"));
			Thread.sleep(SLEEP_TIME);
		}

		List<MailMessage> messages = mailSession.fetchMessages(mailBox, Collections.EMPTY_LIST,
				null, false);
		Assert.assertEquals("Expected messages size: " + MESSAGE_COUNT, MESSAGE_COUNT, messages
				.size());

		List<String> uids = new ArrayList<String>();
		for (MailMessage message : messages) {
			uids.add(message.getUid());
		}

		final String subject = "testFetchNewMessages - new message";
		mailSession.sendMessage(mailBox, createTextPlainMailMessage(from, to, subject));
		Thread.sleep(SLEEP_TIME);
		List<MailMessage> newMessages = mailSession.fetchMessages(mailBox, uids, null, false);
		Assert.assertEquals("Expected new messages size: 1", 1, newMessages.size());
		MailMessage newMessage = newMessages.get(0);
		Assert.assertEquals("Expected new message subject [" + subject + "]", subject, newMessage
				.getSubject());

		// cleanup mailbox
		mailSession.expungeMessages(mailBox, null);
	}

	/**
	 * Creates the mail box.
	 * 
	 * @return the mail box
	 * 
	 * @throws Exception
	 *             the exception
	 */
	protected MailBox createMailBox() throws Exception {
		MailBox mailBox = new MailBox();

		// smtp server
		MailServer smtp = new MailServer();
		smtp.setHostname(mailbox.getProperty("mailbox.host"));
		smtp.setUsername(mailbox.getProperty("mailbox.user"));
		smtp.setPassword(mailbox.getProperty("mailbox.password"));
		smtp.setProtocol(MailServerProtocol.SMTP);

		// pop3 server
		MailServer pop3 = new MailServer();
		pop3.setHostname(mailbox.getProperty("mailbox.host"));
		pop3.setUsername(mailbox.getProperty("mailbox.user"));
		pop3.setPassword(mailbox.getProperty("mailbox.password"));
		pop3.setProtocol(MailServerProtocol.POP3);

		mailBox.setMailTransport(smtp);
		mailBox.setMailStore(pop3);

		return mailBox;
	}

	/**
	 * Creates the mail box.
	 * 
	 * @param host
	 *            the host
	 * @param user
	 *            the user
	 * @param password
	 *            the password
	 * 
	 * @return the mail box
	 * 
	 * @throws Exception
	 *             the exception
	 */
	protected MailBox createMailBox(String host, String user, String password) throws Exception {
		MailBox mailBox = new MailBox();

		// smtp server
		MailServer smtp = new MailServer();
		smtp.setHostname(mailbox.getProperty("mailbox.host"));
		smtp.setUsername(mailbox.getProperty("mailbox.user"));
		smtp.setPassword(mailbox.getProperty("mailbox.password"));
		smtp.setProtocol(MailServerProtocol.SMTP);

		// pop3 server
		MailServer pop3 = new MailServer();
		pop3.setHostname(host);
		pop3.setUsername(user);
		pop3.setPassword(password);
		pop3.setProtocol(MailServerProtocol.POP3);

		mailBox.setMailTransport(smtp);
		mailBox.setMailStore(pop3);

		return mailBox;
	}

	/**
	 * Creates the mail message.
	 * 
	 * @return the mail message
	 * 
	 * @throws Exception
	 *             the exception
	 */
	protected MailMessage createMailMessage() throws Exception {
		MailMessage mailMessage = new MailMessage();
		mailMessage.setContentType(TEXT_PLAIN_TYPE);
		mailMessage.addFrom(new InternetAddress(mailbox.getProperty("mailbox.address"), mailbox
				.getProperty("mailbox.personal")));

		mailMessage.addTo(new InternetAddress(mailbox.getProperty("mailbox.address"), mailbox
				.getProperty("mailbox.personal")));

		mailMessage.setSubject(mailbox.getProperty("mailbox.subject"));
		mailMessage.setText(TEXT_PLAIN_CONTENT);

		return mailMessage;
	}

	/**
	 * Creates the text plain mail message.
	 * 
	 * @param from
	 *            the from
	 * @param to
	 *            the to
	 * @param subject
	 *            the subject
	 * 
	 * @return the mail message
	 * 
	 * @throws Exception
	 *             the exception
	 */
	protected MailMessage createTextPlainMailMessage(InternetAddress from, InternetAddress to,
			String subject) throws Exception {
		MailMessage mailMessage = new MailMessage();
		mailMessage.setContentType(TEXT_PLAIN_TYPE);
		mailMessage.addFrom(from);

		mailMessage.addTo(to);
		mailMessage.setSubject(subject);

		mailMessage.setText(TEXT_PLAIN_CONTENT);

		return mailMessage;
	}

	/**
	 * Creates the mail session.
	 * 
	 * @return the mail session
	 * 
	 * @throws Exception
	 *             the exception
	 */
	protected MailSession createMailSession() throws Exception {
		MailBox mailBox = createMailBox();
		MailSession session = getMailSessionFactory().createMailSession(
				mailBox.getMailStore().getProtocol());
		return session;
	}

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
	 * @param mailSessionFactory
	 *            the new mail session factory
	 */
	public void setMailSessionFactory(MailSessionFactory mailSessionFactory) {
		this.mailSessionFactory = mailSessionFactory;
	}
}
