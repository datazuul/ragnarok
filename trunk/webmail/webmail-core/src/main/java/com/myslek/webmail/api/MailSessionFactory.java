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
package com.myslek.webmail.api;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating MailSession objects.
 */
public interface MailSessionFactory {

	/**
	 * Creates a new MailSession object.
	 * 
	 * @param mailStoreProtocol the mail store protocol
	 * 
	 * @return the mail session
	 * 
	 * @throws UnsupportedMailStoreProtocolException the unsupported mail store protocol exception
	 */
	MailSession createMailSession(String mailStoreProtocol)
			throws UnsupportedMailStoreProtocolException;
}
