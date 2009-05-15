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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.internet.InternetAddress;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.myslek.ragnarok.util.MailUtils;

/**
 * The Class MailMessage.
 */
@Entity
@DiscriminatorValue("MESSAGE")
public class MailMessage extends MailPart {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    private MailBox mailBox;

    /** The uid. */
    private String uid;

    /** The from. */
    private List<InternetAddress> from = new ArrayList<InternetAddress>();

    /** The to. */
    private List<InternetAddress> to = new ArrayList<InternetAddress>();

    /** The cc. */
    private List<InternetAddress> cc = new ArrayList<InternetAddress>();

    /** The bcc. */
    private List<InternetAddress> bcc = new ArrayList<InternetAddress>();

    /** The subject. */
    private String subject;

    /** The folder. */
    private MailFolder folder;

    private Date sentDate;

    private Date receivedDate;

    @ManyToOne
    @JoinColumn(name = "MAILBOX_ID")
    public MailBox getMailBox() {
        return mailBox;
    }

    public void setMailBox(MailBox mailBox) {
        this.mailBox = mailBox;
    }

    /**
     * Gets the uid.
     * 
     * @return the uid
     */
    @Column(name = "MESSAGE_UID", length = 100)
    public String getUid() {
        return uid;
    }

    /**
     * Sets the uid.
     * 
     * @param uid
     *            the new uid
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * Gets the subject.
     * 
     * @return the subject
     */
    @Column(name = "SUBJECT", length = 250)
    public String getSubject() {
        return subject;
    }

    /**
     * Sets the subject.
     * 
     * @param subject
     *            the new subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Gets the folder.
     * 
     * @return the folder
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "FOLDER", length = 10)
    public MailFolder getFolder() {
        return folder;
    }

    /**
     * Sets the folder.
     * 
     * @param folder
     *            the new folder
     */
    public void setFolder(MailFolder folder) {
        this.folder = folder;
    }

    @Transient
    public List<InternetAddress> getFromList() {
        return from;
    }

    public void setFromList(List<InternetAddress> from) {
        this.from = from;
    }

    @Transient
    public List<InternetAddress> getToList() {
        return to;
    }

    public void setToList(List<InternetAddress> to) {
        this.to = to;
    }

    @Transient
    public List<InternetAddress> getCcList() {
        return cc;
    }

    public void setCcList(List<InternetAddress> cc) {
        this.cc = cc;
    }

    @Transient
    public List<InternetAddress> getBccList() {
        return bcc;
    }

    public void setBccList(List<InternetAddress> bcc) {
        this.bcc = bcc;
    }

    @Column(name = "ADDRESS_FROM", length = 2000)
    public String getFrom() {
        return MailUtils.getAddressStringFromList(from);
    }

    public void setFrom(String addresses) {
        from = MailUtils.getAddressListFromString(addresses);
    }

    @Column(name = "ADDRESS_TO", length = 2000)
    public String getTo() {
        return MailUtils.getAddressStringFromList(to);
    }

    public void setTo(String addresses) {
        to = MailUtils.getAddressListFromString(addresses);
    }

    @Column(name = "ADDRESS_CC", length = 2000)
    public String getCc() {
        return MailUtils.getAddressStringFromList(cc);
    }

    public void setCc(String addresses) {
        cc = MailUtils.getAddressListFromString(addresses);
    }

    @Column(name = "ADDRESS_BCC", length = 2000)
    public String getBcc() {
        return MailUtils.getAddressStringFromList(bcc);
    }

    public void setBcc(String addresses) {
        bcc = MailUtils.getAddressListFromString(addresses);
    }

    @Column(name = "SENT_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    @Column(name = "RECEIVED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    /**
     * Gets the mail part by header value.
     * 
     * @param name
     *            the name
     * @param value
     *            the value
     * 
     * @return the mail part by header value
     */
    @Transient
    public MailPart getMailPartByHeaderValue(String name, String value) {
        for (MailPart part : getParts()) {
            MailHeader header = getMailHeader(name);
            if (header != null && header.getValue().equals(value)) {
                return part;
            }
        }
        return null;
    }

    public void addFrom(InternetAddress from) {
        this.from.add(from);
    }

    public void addTo(InternetAddress to) {
        this.to.add(to);
    }

    public void addCc(InternetAddress cc) {
        this.cc.add(cc);
    }

    public void addBcc(InternetAddress bcc) {
        this.bcc.add(bcc);
    }
}
