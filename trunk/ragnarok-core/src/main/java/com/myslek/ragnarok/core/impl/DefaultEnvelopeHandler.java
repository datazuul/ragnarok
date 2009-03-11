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
package com.myslek.ragnarok.core.impl;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;

import com.myslek.ragnarok.core.EnvelopeHandler;
import com.myslek.ragnarok.core.MessageConversionException;
import com.myslek.ragnarok.domain.MailAddress;
import com.myslek.ragnarok.domain.MailMessage;

// TODO: Auto-generated Javadoc
/**
 * The Class DefaultEnvelopeHandler.
 */
public class DefaultEnvelopeHandler implements EnvelopeHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.myslek.webmail.api.EnvelopeHandler#fromEnvelope(javax.mail.Message,
	 * com.myslek.webmail.domain.MailMessage)
	 */
	public void fromEnvelope(Message message, MailMessage mailMessage)
			throws MessageConversionException {
		try {
			//TODO: copy all from addresses
			// Copy from
			InternetAddress[] from = (InternetAddress[]) message.getFrom();
			MailAddress mailFrom = new MailAddress(from[0].getAddress(),
					from[0].getPersonal());
			mailMessage.setFrom(mailFrom);

			// Copy all recipients
			fromRecipients(message, mailMessage);

			// Copy subject
			mailMessage.setSubject(message.getSubject());
		} catch (MessagingException e) {
			throw new MessageConversionException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.myslek.webmail.api.EnvelopeHandler#toEnvelope(com.myslek.webmail.
	 * domain.MailMessage, javax.mail.Message)
	 */
	public void toEnvelope(MailMessage mailMessage, Message message)
			throws MessageConversionException {
		try {
			// Copy from
			MailAddress mailFrom = mailMessage.getFrom();
			Address from = new InternetAddress(mailFrom.getAddress(), mailFrom
					.getPersonal());
			message.setFrom(from);

			// Copy all recipients
			toRecipients(RecipientType.TO, mailMessage, message);
			toRecipients(RecipientType.CC, mailMessage, message);
			toRecipients(RecipientType.BCC, mailMessage, message);

			// Copy subject
			message.setSubject(mailMessage.getSubject());
		} catch (UnsupportedEncodingException e) {
			throw new MessageConversionException(e);
		} catch (MessagingException e) {
			throw new MessageConversionException(e);
		}
	}

	/**
	 * From recipients.
	 * 
	 * @param message
	 *            the message
	 * @param mailMessage
	 *            the mail message
	 * 
	 * @throws MessageConversionException
	 *             the message conversion exception
	 */
	protected void fromRecipients(Message message, MailMessage mailMessage)
			throws MessageConversionException {
		// TODO: implement me
	}

	/**
	 * To recipients.
	 * 
	 * @param mailMessage
	 *            the mail message
	 * @param message
	 *            the message
	 * @param type
	 *            the type
	 * 
	 * @throws MessageConversionException
	 *             the message conversion exception
	 */
	protected void toRecipients(RecipientType type, MailMessage mailMessage,
			Message message) throws MessageConversionException {
		List<MailAddress> recipients = Collections.EMPTY_LIST;
		if (type == RecipientType.TO) {
			recipients = mailMessage.getTo();
		} else if (type == RecipientType.CC) {
			recipients = mailMessage.getCc();
		} else if (type == RecipientType.BCC) {
			recipients = mailMessage.getBcc();
		}

		for (MailAddress address : recipients) {
			try {
				Address addr = new InternetAddress(address.getAddress(),
						address.getPersonal());
				message.addRecipient(type, addr);
			} catch (UnsupportedEncodingException e) {
				throw new MessageConversionException(e);
			} catch (MessagingException e) {
				throw new MessageConversionException(e);
			}
		}
	}

}
