package com.myslek.webmail.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class MailUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String token;
	private String username;
	private String password;
	private Collection<MailBox> mailBoxes = new ArrayList<MailBox>();
	
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

	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Collection<MailBox> getMailBoxes() {
		return mailBoxes;
	}
	
	public void setMailBoxes(Collection<MailBox> mailBoxes) {
		this.mailBoxes = mailBoxes;
	}
}
