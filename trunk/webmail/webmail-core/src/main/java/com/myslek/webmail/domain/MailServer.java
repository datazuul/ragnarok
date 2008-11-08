package com.myslek.webmail.domain;

import java.io.Serializable;

public class MailServer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private MailBox mailBox;
	private String username;
	private String password;
	private String host;
	private String protocol;
	private String defaultFolder;
	private MailServerType type;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public MailBox getMailBox() {
		return mailBox;
	}
	
	public void setMailBox(MailBox mailBox) {
		this.mailBox = mailBox;
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
	
	public String getHost() {
		return host;
	}
	
	public void setHost(String host) {
		this.host = host;
	}
	
	public String getProtocol() {
		return protocol;
	}
	
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getDefaultFolder() {
		return defaultFolder;
	}
	public void setDefaultFolder(String defaultFolder) {
		this.defaultFolder = defaultFolder;
	}
	public MailServerType getType() {
		return type;
	}
	public void setType(MailServerType type) {
		this.type = type;
	}
}
