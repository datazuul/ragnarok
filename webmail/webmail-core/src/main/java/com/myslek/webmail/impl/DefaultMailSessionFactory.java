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

import java.util.HashMap;
import java.util.Map;

import com.myslek.webmail.api.MailSession;
import com.myslek.webmail.api.MailSessionFactory;
import com.myslek.webmail.domain.MailBox;

public class DefaultMailSessionFactory implements MailSessionFactory {
	
	private Map<String, MailSession> storeToSession = new HashMap<String, MailSession>();
	
	public static final String POP3_STORE = "pop3";
	public static final String IMAP_STORE = "imap";
	
	public DefaultMailSessionFactory() {
		storeToSession.put(POP3_STORE, new Pop3MailSession());
	}

	public MailSession createMailSession(MailBox mailBox) {
		MailSession session = storeToSession.get(mailBox.getMailStore().getProtocol());
		if (session == null) {
			//throw exception -> unsupported mail store
		}
		return session;
	}
}
