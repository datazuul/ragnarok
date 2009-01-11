package com.myslek.webmail.domain;

import java.util.ArrayList;
import java.util.List;

public class MailMessage extends MailPart {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String uid;
	private String from;
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

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
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
