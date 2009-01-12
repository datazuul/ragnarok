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
	
	public static final String TEXT_TYPE_PREFIX = "text/";
	public static final String MESSAGE_TYPE_PREFIX = "message/";
	public static final String MULTIPART_TYPE_PREFIX = "multipart/";
	public static final String IMAGE_TYPE_PREFIX = "image/";
	public static final String VIDEO_TYPE_PREFIX = "video/";
	public static final String APPLICATION_TYPE_PREFIX = "application/";
	public static final String AUDIO_TYPE_PREFIX = "audio/";
	
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
	
	public void addHeader(MailHeader header) {
		header.setMailPart(this);
		getHeaders().add(header);
	}
	
	public boolean isMimeType(String mimeType) {
		if (mimeType != null && mimeType.trim().length() > 0) {
			String contentType = this.getContentType();
			if (contentType.indexOf(";") != -1) {
				contentType = contentType.substring(0, contentType.indexOf(";"));
			}
			return contentType.equalsIgnoreCase(mimeType);
		}
		return false;
	}
	
	public static MailPart create(String contentType) {
		if (contentType != null && contentType.startsWith(MailPart.MESSAGE_TYPE_PREFIX)) {
			return new MailMessage();
		}
		return new MailPart();
	}
}
