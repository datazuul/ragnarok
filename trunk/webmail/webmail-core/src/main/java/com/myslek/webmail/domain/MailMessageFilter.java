package com.myslek.webmail.domain;

import java.io.Serializable;

public class MailMessageFilter implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private MailBox mailBox;
	private String filteringRule;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MailBox getMailBox() {
		return mailBox;
	}

	public void setMailBox(MailBox mailBox) {
		this.mailBox = mailBox;
	}

	public String getFilteringRule() {
		return filteringRule;
	}

	public void setFilteringRule(String filteringRule) {
		this.filteringRule = filteringRule;
	}
}
