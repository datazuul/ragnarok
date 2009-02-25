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

import javax.mail.Message;
import javax.mail.MessagingException;

import com.myslek.webmail.api.EnvelopeHandler;
import com.myslek.webmail.api.MessageConversionException;
import com.myslek.webmail.domain.MailMessage;

// TODO: Auto-generated Javadoc
/**
 * The Class DefaultEnvelopeHandler.
 */
public class DefaultEnvelopeHandler implements EnvelopeHandler {

	/* (non-Javadoc)
	 * @see com.myslek.webmail.api.EnvelopeHandler#fromEnvelope(javax.mail.Message, com.myslek.webmail.domain.MailMessage)
	 */
	public void fromEnvelope(Message message, MailMessage mailMessage)
			throws MessageConversionException {
		try {
			mailMessage.setSubject(message.getSubject());
			//TODO: 1. copy all message recipients
			//TODO: 2. copy all message headers (message.getAllHeaders())
		} catch (MessagingException e) {
			throw new MessageConversionException(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.myslek.webmail.api.EnvelopeHandler#toEnvelope(com.myslek.webmail.domain.MailMessage, javax.mail.Message)
	 */
	public void toEnvelope(MailMessage mailMessage, Message message)
			throws MessageConversionException {
		try {
			message.setSubject(mailMessage.getSubject());
		} catch (MessagingException e) {
			throw new MessageConversionException(e);
		}
	}

}
