package com.myslek.webmail.api;

import javax.mail.Message;

public class AndMessageFilter extends CompositeMessageFilter {

	public AndMessageFilter(MessageFilter[] filters) {
		super(filters);
	}

	@Override
	public boolean accept(Message message) {
		for (MessageFilter filter : filters) {
			if (!filter.accept(message)) {
				return false;
			}
		}
		return true;
	}
}
