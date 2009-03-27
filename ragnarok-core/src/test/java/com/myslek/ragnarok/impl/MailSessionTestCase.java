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

import java.util.List;

import javax.mail.Message;

import junit.framework.Assert;

import org.jvnet.mock_javamail.Mailbox;

import com.myslek.ragnarok.domain.MailBox;
import com.myslek.ragnarok.domain.MailMessage;
import com.myslek.ragnarok.mail.MailSession;

// TODO: Auto-generated Javadoc
/**
 * The Class MailSessionTest.
 */
public class MailSessionTestCase extends AbstractMailTestCase {

	
	/**
	 * Test send message.
	 * 
	 * @throws Exception the exception
	 */
	public void testSendMessage() throws Exception {
		MailBox mailBox = createMailBox();
		MailSession mailSession = createMailSession();
		MailMessage mailMessage = createTextPlainMailMessage();
		
		
		mailSession.sendMessage(mailBox, mailMessage);
		List<Message> inbox = Mailbox.get(TO.getAddress());
		Assert.assertNotNull("Inbox must not be null", inbox);
		Assert.assertEquals("Expected inbox size: 1", 1, inbox.size());
	}
}
