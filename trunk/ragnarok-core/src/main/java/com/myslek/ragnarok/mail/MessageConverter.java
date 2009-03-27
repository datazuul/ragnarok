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
package com.myslek.ragnarok.mail;

import javax.mail.Message;
import javax.mail.Session;

import com.myslek.ragnarok.domain.MailMessage;

// TODO: Auto-generated Javadoc
/**
 * The Interface MessageConverter.
 */
public interface MessageConverter {
	
	/**
	 * From message.
	 * 
	 * @param message the message
	 * 
	 * @return the mail message
	 * 
	 * @throws MessageConversionException the message conversion exception
	 */
	public MailMessage fromMessage(Message message) throws MessageConversionException;
	
	/**
	 * To message.
	 * 
	 * @param mailMessage the mail message
	 * @param session the session
	 * 
	 * @return the message
	 * 
	 * @throws MessageConversionException the message conversion exception
	 */
	public Message toMessage(MailMessage mailMessage, Session session) throws MessageConversionException;
}
