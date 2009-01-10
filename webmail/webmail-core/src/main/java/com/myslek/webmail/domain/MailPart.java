package com.myslek.webmail.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MailPart implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Object content;
	private String contentType;
	private String fileName;
	private MailPart parent;
	private List<MailPart> parts = new ArrayList<MailPart>();
	private int size;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
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

	public List<MailPart> getParts() {
		return parts;
	}

	public void setParts(List<MailPart> parts) {
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
