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

import java.util.Collection;

import javax.mail.Part;
import javax.mail.Session;

import com.myslek.ragnarok.domain.MailPart;

// TODO: Auto-generated Javadoc
/**
 * The Interface ContentHandlerManager.
 */
public interface ContentHandlerManager {

	/**
	 * Adds the content handler.
	 * 
	 * @param contentType the content type
	 * @param handler the handler
	 */
	public void addContentHandler(String contentType, ContentHandler handler);

	/**
	 * Gets the content handlers.
	 * 
	 * @return the content handlers
	 */
	public Collection<ContentHandler> getContentHandlers();

	/**
	 * From part content.
	 * 
	 * @param part the part
	 * @param mailPart the mail part
	 * 
	 * @throws MessageConversionException the message conversion exception
	 */
	public void fromPartContent(Part part, MailPart mailPart)
			throws MessageConversionException;

	/**
	 * To part content.
	 * 
	 * @param mailPart the mail part
	 * @param part the part
	 * @param session the session
	 * 
	 * @throws MessageConversionException the message conversion exception
	 */
	public void toPartContent(MailPart mailPart, Part part, Session session)
			throws MessageConversionException;
}
