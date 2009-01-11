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
	private Content content;
	private String contentType;
	private String fileName;
	private String description;
	private String disposition;
	private MailPart parent;
	private List<MailHeader> headers = new ArrayList<MailHeader>();
	private List<MailPart> parts = new ArrayList<MailPart>();
	private int size;
	
	public static final String ATTACHMENT = "attachment";
	public static final String INLINE = "inline";
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDisposition() {
		return disposition;
	}

	public void setDisposition(String disposition) {
		this.disposition = disposition;
	}

	public MailPart getParent() {
		return parent;
	}

	public void setParent(MailPart parent) {
		this.parent = parent;
	}

	public List<MailHeader> getHeaders() {
		return headers;
	}

	public void setHeaders(List<MailHeader> headers) {
		this.headers = headers;
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
