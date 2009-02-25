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

import java.util.Collection;

import javax.mail.Session;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.myslek.webmail.api.MailSession;
import com.myslek.webmail.api.MailSessionException;
import com.myslek.webmail.api.MailSessionFactory;
import com.myslek.webmail.api.MessageFilter;
import com.myslek.webmail.domain.MailBox;
import com.myslek.webmail.domain.MailMessage;

// TODO: Auto-generated Javadoc
/**
 * The Class MailSessionFactoryTest.
 */
public class MailSessionFactoryTest extends TestCase {

	/** The mail session factory. */
	private MailSessionFactory mailSessionFactory;

	/** The Constant POP3_STORE_PROTOCOL. */
	public static final String POP3_STORE_PROTOCOL = "pop3";

	/**
	 * Test create mail session.
	 * 
	 * @throws Exception the exception
	 */
	public void testCreateMailSession() throws Exception {
		mailSessionFactory = new DefaultMailSessionFactory();
		MailSession mailSession = mailSessionFactory
				.createMailSession(POP3_STORE_PROTOCOL);
		Assert.assertNotNull("MailSession must not be null", mailSession);
	}
}

class MockMailSession implements MailSession {

	public Collection<MailMessage> fetchMessages(MailBox mailBox,
			Collection<String> uids, MessageFilter filter)
			throws MailSessionException {
		return null;
	}

	public Session getSession() {
		return null;
	}

	public void sendMessage(MailBox mailBox, MailMessage mailMessage)
			throws MailSessionException {
	}
}
