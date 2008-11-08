package com.myslek.webmail.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class MailPart implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	//TODO: maybe we should add a separate field for text content???
	private byte[] data;
	private String contentType;
	private String fileName;
	private MailPart parent;
	private Collection<MailPart> parts = new ArrayList<MailPart>();
	private int size;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getData() {
		return data;
	}
	
	public void setData(byte[] data) {
		this.data = data;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public MailPart getParent() {
		return parent;
	}

	public void setParent(MailPart parent) {
		this.parent = parent;
	}

	public Collection<MailPart> getParts() {
		return parts;
	}

	public void setParts(Collection<MailPart> parts) {
		this.parts = parts;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	public void addPart(MailPart part) {
		part.setParent(this);
		getParts().add(part);
	}
}
