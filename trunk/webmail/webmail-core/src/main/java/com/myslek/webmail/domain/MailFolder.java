package com.myslek.webmail.domain;

import java.io.Serializable;

public class MailFolder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String token;
	private MailBox mailBox;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public MailBox getMailBox() {
		return mailBox;
	}
	
	public void setMailBox(MailBox mailBox) {
		this.mailBox = mailBox;
	}
}
