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
package com.myslek.webmail.domain;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class MailMessage.
 */
public class MailMessage extends MailPart {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The uid. */
	private String uid;
	
	/** The from. */
	private MailAddress from;
	
	/** The to. */
	private List<MailAddress> to = new ArrayList<MailAddress>();
	
	/** The cc. */
	private List<MailAddress> cc = new ArrayList<MailAddress>();
	
	/** The bcc. */
	private List<MailAddress> bcc = new ArrayList<MailAddress>();
	
	/** The subject. */
	private String subject;
	
	/** The folder. */
	private MailFolder folder;

	/**
	 * Gets the uid.
	 * 
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * Sets the uid.
	 * 
	 * @param uid the new uid
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}

	/**
	 * Gets the from.
	 * 
	 * @return the from
	 */
	public MailAddress getFrom() {
		return from;
	}

	/**
	 * Sets the from.
	 * 
	 * @param from the new from
	 */
	public void setFrom(MailAddress from) {
		this.from = from;
	}

	/**
	 * Gets the to.
	 * 
	 * @return the to
	 */
	public List<MailAddress> getTo() {
		return to;
	}

	/**
	 * Sets the to.
	 * 
	 * @param to the new to
	 */
	public void setTo(List<MailAddress> to) {
		this.to = to;
	}

	/**
	 * Gets the cc.
	 * 
	 * @return the cc
	 */
	public List<MailAddress> getCc() {
		return cc;
	}

	/**
	 * Sets the cc.
	 * 
	 * @param cc the new cc
	 */
	public void setCc(List<MailAddress> cc) {
		this.cc = cc;
	}

	/**
	 * Gets the bcc.
	 * 
	 * @return the bcc
	 */
	public List<MailAddress> getBcc() {
		return bcc;
	}

	/**
	 * Sets the bcc.
	 * 
	 * @param bcc the new bcc
	 */
	public void setBcc(List<MailAddress> bcc) {
		this.bcc = bcc;
	}

	/**
	 * Gets the subject.
	 * 
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Sets the subject.
	 * 
	 * @param subject the new subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * Gets the folder.
	 * 
	 * @return the folder
	 */
	public MailFolder getFolder() {
		return folder;
	}

	/**
	 * Sets the folder.
	 * 
	 * @param folder the new folder
	 */
	public void setFolder(MailFolder folder) {
		this.folder = folder;
	}
	
	/**
	 * Gets the mail header by name.
	 * 
	 * @param name the name
	 * 
	 * @return the mail header by name
	 */
	public MailHeader getMailHeaderByName(String name) {
		for (MailHeader header : getHeaders()) {
			if (header.getName().equals(name)) {
				return header;
			}
		}
		return null;
	}

	/**
	 * Gets the mail part by header value.
	 * 
	 * @param name the name
	 * @param value the value
	 * 
	 * @return the mail part by header value
	 */
	public MailPart getMailPartByHeaderValue(String name, String value) {
		for (MailPart part : getParts()) {
			for (MailHeader header : part.getHeaders()) {
				if (header.getName().equals(name)
						&& header.getValue().equals(value)) {
					return part;
				}
			}
		}
		return null;
	}
	
	
	
	/**
	 * Adds the recipient.
	 * 
	 * @param type the type
	 * @param address the address
	 */
	public void addRecipient(RecipientType type, MailAddress address) {
		switch (type) {
		case TO:
			to.add(address);
			break;
		case CC:
			cc.add(address);
			break;
		case BCC:
			bcc.add(address);
			break;
		}
	}
}
