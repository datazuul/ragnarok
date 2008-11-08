package com.myslek.webmail.domain;

import java.io.Serializable;

public class MailAddress implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String address;
	private String personal;
	private MailAddressType type;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getPersonal() {
		return personal;
	}
	
	public void setPersonal(String personal) {
		this.personal = personal;
	}
	
	public MailAddressType getType() {
		return type;
	}
	
	public void setType(MailAddressType type) {
		this.type = type;
	}
}
