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

import java.util.HashMap;
import java.util.Map;

import com.myslek.ragnarok.domain.MailServerProtocol;
import com.myslek.ragnarok.mail.MailSession;
import com.myslek.ragnarok.mail.MailSessionFactory;
import com.myslek.ragnarok.mail.exception.UnsupportedMailStoreProtocolException;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating DefaultMailSession objects.
 */
public class DefaultMailSessionFactory implements MailSessionFactory {

	/** The mail session types. */
	private Map<MailServerProtocol, MailSession> mailSessionTypes = new HashMap<MailServerProtocol, MailSession>();

	/**
	 * Instantiates a new default mail session factory.
	 */
	public DefaultMailSessionFactory() {
		mailSessionTypes.put(MailServerProtocol.POP3, new Pop3MailSession());
	}

	/**
	 * Instantiates a new default mail session factory.
	 * 
	 * @param mailSessionTypes
	 *            the mail session types
	 */
	public DefaultMailSessionFactory(Map<MailServerProtocol, MailSession> mailSessionTypes) {
		this.mailSessionTypes = mailSessionTypes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.myslek.webmail.api.MailSessionFactory#createMailSession(java.lang
	 * .String)
	 */
	public MailSession createMailSession(MailServerProtocol mailStoreProtocol)
			throws UnsupportedMailStoreProtocolException {
		MailSession mailSession = mailSessionTypes.get(mailStoreProtocol);
		if (mailSession == null) {
			throw new UnsupportedMailStoreProtocolException("Unsupported mail store protocol ["
					+ mailStoreProtocol + "]");
		}
		return mailSession;
	}
}
