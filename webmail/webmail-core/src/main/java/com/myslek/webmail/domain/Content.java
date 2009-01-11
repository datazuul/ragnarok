package com.myslek.webmail.domain;

import java.io.Serializable;

public class Content implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String text;
	private byte[] data;
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public byte[] getData() {
		return data;
	}
	
	public void setData(byte[] data) {
		this.data = data;
	}
}
