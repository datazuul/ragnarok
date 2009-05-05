/*
 * Copyright 2009 Rafal Myslek 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0 
 *     
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License.     
 */
package com.myslek.ragnarok.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * The Class MailPart.
 */
@Entity
@Table(name = "RAG_MAIL")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DISCRIMINATOR", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("PART")
public class MailPart implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Long id;

	/** The text. */
	private String text;

	/** The data. */
	private byte[] data;

	/** The content type. */
	private String contentType;

	/** The file name. */
	private String fileName;

	/** The description. */
	private String description;

	/** The disposition. */
	private String disposition;

	/** The parent. */
	private MailPart parent;

	/** The parts. */
	private List<MailPart> parts = new ArrayList<MailPart>();

	/** The headers. */
	private List<MailHeader> headers = new ArrayList<MailHeader>();
	
	private MailBox mailBox;

	/** The size. */
	private int size;

	/** The Constant ATTACHMENT. */
	public static final String ATTACHMENT = "attachment";

	/** The Constant INLINE. */
	public static final String INLINE = "inline";

	/** The Constant TEXT_TYPE_PREFIX. */
	public static final String TEXT_TYPE_PREFIX = "text/";

	/** The Constant MESSAGE_TYPE_PREFIX. */
	public static final String MESSAGE_TYPE_PREFIX = "message/";

	/** The Constant MULTIPART_TYPE_PREFIX. */
	public static final String MULTIPART_TYPE_PREFIX = "multipart/";

	/** The Constant IMAGE_TYPE_PREFIX. */
	public static final String IMAGE_TYPE_PREFIX = "image/";

	/** The Constant VIDEO_TYPE_PREFIX. */
	public static final String VIDEO_TYPE_PREFIX = "video/";

	/** The Constant APPLICATION_TYPE_PREFIX. */
	public static final String APPLICATION_TYPE_PREFIX = "application/";

	/** The Constant AUDIO_TYPE_PREFIX. */
	public static final String AUDIO_TYPE_PREFIX = "audio/";

	/** The Constant MESSAGE_RFC822_TYPE. */
	public static final String MESSAGE_RFC822_TYPE = "message/rfc822";

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	@Id
	@GeneratedValue
	@Column(name = "ID")
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the text.
	 * 
	 * @return the text
	 */
	@Column(name = "PART_TEXT", length = 4000)
	public String getText() {
		return text;
	}

	/**
	 * Sets the text.
	 * 
	 * @param text the new text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Gets the data.
	 * 
	 * @return the data
	 */
	@Lob
	@Column(name = "PART_BLOB")
	@Basic(fetch = FetchType.LAZY)
	public byte[] getData() {
		return data;
	}

	/**
	 * Sets the data.
	 * 
	 * @param data the new data
	 */
	public void setData(byte[] data) {
		this.data = data;
	}

	/**
	 * Gets the content type.
	 * 
	 * @return the content type
	 */
	@Column(name = "CONTENT_TYPE", nullable = false, length = 30)
	public String getContentType() {
		return contentType;
	}

	/**
	 * Sets the content type.
	 * 
	 * @param contentType the new content type
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * Gets the file name.
	 * 
	 * @return the file name
	 */
	@Column(name = "FILE_NAME", length = 50)
	public String getFileName() {
		return fileName;
	}

	/**
	 * Sets the file name.
	 * 
	 * @param fileName
	 *            the new file name
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Gets the description.
	 * 
	 * @return the description
	 */
	@Column(name = "DESCRIPTION", length = 100)
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 * 
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the disposition.
	 * 
	 * @return the disposition
	 */
	@Column(name = "DISPOSITION", length = 20)
	public String getDisposition() {
		return disposition;
	}

	/**
	 * Sets the disposition.
	 * 
	 * @param disposition the new disposition
	 */
	public void setDisposition(String disposition) {
		this.disposition = disposition;
	}

	/**
	 * Gets the parent.
	 * 
	 * @return the parent
	 */
	@ManyToOne
	@JoinColumn(name = "PARENT_ID")
	public MailPart getParent() {
		return parent;
	}

	/**
	 * Sets the parent.
	 * 
	 * @param parent the new parent
	 */
	public void setParent(MailPart parent) {
		this.parent = parent;
	}

	/**
	 * Gets the headers.
	 * 
	 * @return the headers
	 */
	@OneToMany(mappedBy = "mailPart")
	public List<MailHeader> getHeaders() {
		return headers;
	}

	/**
	 * Sets the headers.
	 * 
	 * @param headers the new headers
	 */
	public void setHeaders(List<MailHeader> headers) {
		this.headers = headers;
	}

	/**
	 * Gets the parts.
	 * 
	 * @return the parts
	 */
	@OneToMany(mappedBy = "parent")
	public List<MailPart> getParts() {
		return parts;
	}

	/**
	 * Sets the parts.
	 * 
	 * @param parts the new parts
	 */
	public void setParts(List<MailPart> parts) {
		this.parts = parts;
	}

	/**
	 * Gets the size.
	 * 
	 * @return the size
	 */
	@Column(name = "PART_SIZE", nullable = false)
	public int getSize() {
		return size;
	}

	/**
	 * Sets the size.
	 * 
	 * @param size the new size
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * Adds the part.
	 * 
	 * @param part the part
	 */
	public void addPart(MailPart part) {
		part.setParent(this);
		getParts().add(part);
	}

	/**
	 * Adds the header.
	 * 
	 * @param header the header
	 */
	public void addHeader(MailHeader header) {
		header.setMailPart(this);
		getHeaders().add(header);
	}

	@ManyToOne
	@JoinColumn(name="MAILBOX_ID")
	public MailBox getMailBox() {
        return mailBox;
    }

    public void setMailBox(MailBox mailBox) {
        this.mailBox = mailBox;
    }

    /**
	 * Checks if is mime type.
	 * 
	 * @param mimeType the mime type
	 * 
	 * @return true, if is mime type
	 */
	@Transient
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
}
