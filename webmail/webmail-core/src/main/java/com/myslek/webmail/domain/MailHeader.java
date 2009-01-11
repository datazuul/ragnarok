package com.myslek.webmail.domain;

import java.io.Serializable;

public class MailHeader implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private MailPart mailPart;
	private String name;
	private String value;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public MailPart getMailPart() {
		return mailPart;
	}

	public void setMailPart(MailPart mailPart) {
		this.mailPart = mailPart;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
