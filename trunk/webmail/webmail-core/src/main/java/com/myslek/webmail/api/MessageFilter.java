package com.myslek.webmail.api;

import javax.mail.Message;

public interface MessageFilter {
	
	public boolean accept(Message message);
}
