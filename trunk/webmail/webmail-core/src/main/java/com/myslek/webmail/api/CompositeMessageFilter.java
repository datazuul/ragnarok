package com.myslek.webmail.api;

import javax.mail.Message;

public class CompositeMessageFilter implements MessageFilter {
	
	protected MessageFilter[] filters = new MessageFilter[0];
	
	public CompositeMessageFilter(MessageFilter[] filters) {
		this.filters = filters;
	}

	public boolean accept(Message message) {
		for (MessageFilter filter : filters) {
			if (!(filter.accept(message))) {
				return false;
			}
		}
		return true;
	}
}
