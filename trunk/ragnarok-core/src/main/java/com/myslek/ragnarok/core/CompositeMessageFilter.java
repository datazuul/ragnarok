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
package com.myslek.ragnarok.core;

import javax.mail.Message;

// TODO: Auto-generated Javadoc
/**
 * The Class CompositeMessageFilter.
 */
public class CompositeMessageFilter implements MessageFilter {
	
	/** The filters. */
	protected MessageFilter[] filters = new MessageFilter[0];
	
	/**
	 * Instantiates a new composite message filter.
	 * 
	 * @param filters the filters
	 */
	public CompositeMessageFilter(MessageFilter[] filters) {
		this.filters = filters;
	}

	/* (non-Javadoc)
	 * @see com.myslek.webmail.api.MessageFilter#accept(javax.mail.Message)
	 */
	public boolean accept(Message message) {
		for (MessageFilter filter : filters) {
			if (!(filter.accept(message))) {
				return false;
			}
		}
		return true;
	}
}
