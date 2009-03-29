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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;

import com.myslek.ragnarok.domain.MailMessage;
import com.myslek.ragnarok.mail.EnvelopeHandler;
import com.myslek.ragnarok.mail.exception.MessageConversionException;

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
			InternetAddress[] from = (InternetAddress[]) message.getFrom();
			mailMessage.setFrom(Arrays.asList(from));

			// Copy all recipients
			fromRecipients(RecipientType.TO, message, mailMessage);
			fromRecipients(RecipientType.CC, message, mailMessage);
			fromRecipients(RecipientType.BCC, message, mailMessage);

			// Copy subject
			mailMessage.setSubject(message.getSubject());

			// Copy sent date
			mailMessage.setSentDate(message.getSentDate());

			// Copy received date
			mailMessage.setReceivedDate(message.getReceivedDate());
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
			List<InternetAddress> mailFrom = mailMessage.getFrom();
			message.addFrom((InternetAddress[]) mailFrom.toArray(new InternetAddress[mailFrom
					.size()]));

			// Copy all recipients
			toRecipients(RecipientType.TO, mailMessage, message);
			toRecipients(RecipientType.CC, mailMessage, message);
			toRecipients(RecipientType.BCC, mailMessage, message);

			// Copy subject
			message.setSubject(mailMessage.getSubject());

			// TODO: copy sent date

			// TODO: copy receive date
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
	protected void fromRecipients(RecipientType type, Message message, MailMessage mailMessage)
			throws MessageConversionException {
		try {
			InternetAddress[] recipients = (InternetAddress[]) message.getRecipients(type);
			if (recipients != null && recipients.length > 0) {
				if (type == RecipientType.TO) {
					mailMessage.setTo(Arrays.asList(recipients));
				} else if (type == RecipientType.CC) {
					mailMessage.setCc(Arrays.asList(recipients));
				} else if (type == RecipientType.BCC) {
					mailMessage.setBcc(Arrays.asList(recipients));
				}
			}
		} catch (MessagingException e) {
			throw new MessageConversionException(e);
		}
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
	protected void toRecipients(RecipientType type, MailMessage mailMessage, Message message)
			throws MessageConversionException {
		try {
			List<InternetAddress> recipients = Collections.EMPTY_LIST;
			if (type == RecipientType.TO) {
				recipients = mailMessage.getTo();
			} else if (type == RecipientType.CC) {
				recipients = mailMessage.getCc();
			} else if (type == RecipientType.BCC) {
				recipients = mailMessage.getBcc();
			}
			if (recipients != null && !recipients.isEmpty()) {
				message.setRecipients(type, (InternetAddress[]) recipients
						.toArray(new InternetAddress[recipients.size()]));
			}
		} catch (MessagingException e) {
			throw new MessageConversionException(e);
		}
	}

}
