package com.myslek.webmail.api;

import com.myslek.webmail.domain.MailFolder;
import com.myslek.webmail.domain.MailMessage;

public interface MessageFilterRule {
	
	boolean matches(MailMessage mailMessage);
	
	MailFolder getDestinationFolder();
}
