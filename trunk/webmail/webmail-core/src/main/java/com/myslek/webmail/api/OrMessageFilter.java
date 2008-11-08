package com.myslek.webmail.api;

import javax.mail.Message;


public class OrMessageFilter extends CompositeMessageFilter {

	public OrMessageFilter(MessageFilter[] filters) {
		super(filters);
	}

	@Override
	public boolean accept(Message message) {
		for (MessageFilter filter : filters) {
			if (filter.accept(message)) {
				return true;
			}
		}
		return false;
	}
}
