package com.myslek.webmail.domain;

import java.util.ArrayList;
import java.util.Collection;

public class MailMessage extends MailPart {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String from;
	private Collection<MailAddress> to = new ArrayList<MailAddress>();
	private Collection<MailAddress> cc = new ArrayList<MailAddress>();
	private Collection<MailAddress> bcc = new ArrayList<MailAddress>();
	
	private String subject;
	
	private MailFolder folder;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public Collection<MailAddress> getTo() {
		return to;
	}

	public void setTo(Collection<MailAddress> to) {
		this.to = to;
	}

	public Collection<MailAddress> getCc() {
		return cc;
	}

	public void setCc(Collection<MailAddress> cc) {
		this.cc = cc;
	}

	public Collection<MailAddress> getBcc() {
		return bcc;
	}

	public void setBcc(Collection<MailAddress> bcc) {
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
}
