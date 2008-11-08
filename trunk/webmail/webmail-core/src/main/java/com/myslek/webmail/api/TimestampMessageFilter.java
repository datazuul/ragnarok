package com.myslek.webmail.api;

import javax.mail.Message;

public class TimestampMessageFilter implements MessageFilter {

	public boolean accept(Message message) {
		return false;
	}

}
