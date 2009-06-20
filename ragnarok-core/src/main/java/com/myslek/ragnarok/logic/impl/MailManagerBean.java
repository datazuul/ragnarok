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
package com.myslek.ragnarok.logic.impl;

import java.util.Collection;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.myslek.ragnarok.domain.MailBox;
import com.myslek.ragnarok.domain.MailFolder;
import com.myslek.ragnarok.domain.MailMessage;
import com.myslek.ragnarok.domain.MailMessageSummary;
import com.myslek.ragnarok.domain.MailUser;
import com.myslek.ragnarok.logic.MailManager;
import com.myslek.ragnarok.logic.MailSessionManager;
import com.myslek.ragnarok.logic.MailStoreManager;
import com.myslek.ragnarok.mail.MessageFilter;
import com.myslek.ragnarok.persistence.ResultParams;

@Stateless
public class MailManagerBean implements MailManager {

    @EJB
    private MailSessionManager mailSessionManager;
    @EJB
    private MailStoreManager mailStoreManager;

    protected void fetchAndStoreMessages(MailBox mailBox, MessageFilter filter) {
        Collection<String> uids = mailStoreManager.getUids(mailBox, MailFolder.INBOX);
        Collection<MailMessage> messages = mailSessionManager.fetchMessages(mailBox, uids,
                filter);
        mailStoreManager.storeMessages(messages);
    }

    public List<MailMessageSummary> getMessageSummaries(MailUser user, String mailBoxToken,
            MailFolder folder, ResultParams params, boolean syncWithMailServer) {
        MailBox mailBox = mailStoreManager.getMailBox(user, mailBoxToken);
        if (mailBox == null) {
            //TODO: throw ApplicationException
        }
        if (syncWithMailServer) {
            fetchAndStoreMessages(mailBox, null);
        }
        return mailStoreManager.getMessageSummaries(user, mailBox, folder, params);
    }

    public MailSessionManager getMailSessionManager() {
        return mailSessionManager;
    }

    public void setMailSessionManager(MailSessionManager mailSessionManager) {
        this.mailSessionManager = mailSessionManager;
    }

    public MailStoreManager getMailStoreManager() {
        return mailStoreManager;
    }

    public void setMailStoreManager(MailStoreManager mailStoreManager) {
        this.mailStoreManager = mailStoreManager;
    }

    public void saveMailBox(MailBox mailBox) {
        mailStoreManager.saveMailBox(mailBox);
    }

    public void saveMailUser(MailUser user) {
        mailStoreManager.saveMailUser(user);
    }
}