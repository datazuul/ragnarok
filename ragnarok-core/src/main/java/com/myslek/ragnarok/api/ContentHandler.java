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
package com.myslek.ragnarok.api;

import javax.mail.Part;
import javax.mail.Session;

import com.myslek.ragnarok.domain.MailPart;

// TODO: Auto-generated Javadoc
/**
 * The Interface ContentHandler.
 */
public interface ContentHandler {
	
	/**
	 * Accept.
	 * 
	 * @param contentType the content type
	 * 
	 * @return true, if successful
	 * 
	 * @throws MessageConversionException the message conversion exception
	 */
	public boolean accept(String contentType) throws MessageConversionException;

	/**
	 * From part content.
	 * 
	 * @param part the part
	 * @param mailPart the mail part
	 * @param manager the content handler manager
	 * 
	 * @throws MessageConversionException the message conversion exception
	 */
	public void fromPartContent(Part part, MailPart mailPart,
			ContentHandlerManager manager)
			throws MessageConversionException;

	/**
	 * To part content.
	 * 
	 * @param mailPart the mail part
	 * @param part the part
	 * @param session the session
	 * @param manager the content handler manager
	 * 
	 * @throws MessageConversionException the message conversion exception
	 */
	public void toPartContent(MailPart mailPart, Part part,
			Session session, ContentHandlerManager manager)
			throws MessageConversionException;
}
