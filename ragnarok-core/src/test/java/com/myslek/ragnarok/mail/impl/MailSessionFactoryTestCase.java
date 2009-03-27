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

import junit.framework.Assert;
import junit.framework.TestCase;

import com.myslek.ragnarok.domain.MailServerProtocol;
import com.myslek.ragnarok.mail.MailSession;
import com.myslek.ragnarok.mail.MailSessionFactory;
import com.myslek.ragnarok.mail.impl.DefaultMailSessionFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class MailSessionFactoryTest.
 */
public class MailSessionFactoryTestCase extends TestCase {

	/** The mail session factory. */
	private MailSessionFactory mailSessionFactory;

	/** The Constant POP3_STORE_PROTOCOL. */
	public static final String POP3_STORE_PROTOCOL = "pop3";

	/**
	 * Test create mail session.
	 * 
	 * @throws Exception the exception
	 */
	public void testCreatePop3MailSession() throws Exception {
		mailSessionFactory = new DefaultMailSessionFactory();
		MailSession mailSession = mailSessionFactory
				.createMailSession(MailServerProtocol.POP3);
		Assert.assertNotNull("MailSession must not be null", mailSession);
	}
}