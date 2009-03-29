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

import javax.mail.Part;

import com.myslek.ragnarok.domain.MailPart;
import com.myslek.ragnarok.mail.exception.MessageConversionException;

// TODO: Auto-generated Javadoc
/**
 * The Interface AttributesHandler.
 */
public interface AttributesHandler {
	
	/**
	 * From attributes.
	 * 
	 * @param part the part
	 * @param mailPart the mail part
	 * 
	 * @throws MessageConversionException the message conversion exception
	 */
	public void fromAttributes(Part part, MailPart mailPart) throws MessageConversionException;
	
	/**
	 * To attributes.
	 * 
	 * @param mailPart the mail part
	 * @param part the part
	 * 
	 * @throws MessageConversionException the message conversion exception
	 */
	public void toAttributes(MailPart mailPart, Part part) throws MessageConversionException;
}
