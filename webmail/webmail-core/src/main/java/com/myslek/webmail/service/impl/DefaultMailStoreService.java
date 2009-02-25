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
package com.myslek.webmail.service.impl;

import java.util.Collection;

import javax.ejb.Stateless;

import com.myslek.webmail.domain.MailBox;
import com.myslek.webmail.domain.MailMessage;
import com.myslek.webmail.service.MailStoreService;

// TODO: Auto-generated Javadoc
/**
 * The Class DefaultMailStoreService.
 */
@Stateless
public class DefaultMailStoreService implements MailStoreService {

	/* (non-Javadoc)
	 * @see com.myslek.webmail.service.MailStoreService#storeMessages(java.util.Collection)
	 */
	public void storeMessages(Collection<MailMessage> messages) {
		
	}

	/* (non-Javadoc)
	 * @see com.myslek.webmail.service.MailStoreService#getUids(com.myslek.webmail.domain.MailBox)
	 */
	public Collection<String> getUids(MailBox mailBox) {
		return null;
	}
}