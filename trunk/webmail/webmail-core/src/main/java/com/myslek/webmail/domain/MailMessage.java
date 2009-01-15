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

public class MailMessage extends MailPart {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String uid;
	private MailAddress from;
	private List<MailAddress> to = new ArrayList<MailAddress>();
	private List<MailAddress> cc = new ArrayList<MailAddress>();
	private List<MailAddress> bcc = new ArrayList<MailAddress>();
	private String subject;
	private MailFolder folder;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public MailAddress getFrom() {
		return from;
	}

	public void setFrom(MailAddress from) {
		this.from = from;
	}

	public List<MailAddress> getTo() {
		return to;
	}

	public void setTo(List<MailAddress> to) {
		this.to = to;
	}

	public List<MailAddress> getCc() {
		return cc;
	}

	public void setCc(List<MailAddress> cc) {
		this.cc = cc;
	}

	public List<MailAddress> getBcc() {
		return bcc;
	}

	public void setBcc(List<MailAddress> bcc) {
		this.bcc = bcc;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public MailFolder getFolder() {
		return folder;
	}

	public void setFolder(MailFolder folder) {
		this.folder = folder;
	}
	
	public MailHeader getMailHeaderByName(String name) {
		for (MailHeader header : getHeaders()) {
			if (header.getName().equals(name)) {
				return header;
			}
		}
		return null;
	}

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
}
