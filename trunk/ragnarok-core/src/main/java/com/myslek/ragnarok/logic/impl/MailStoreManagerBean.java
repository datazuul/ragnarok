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
import com.myslek.ragnarok.logic.MailStoreManager;
import com.myslek.ragnarok.persistence.MailStoreDao;
import com.myslek.ragnarok.persistence.ResultParams;

@Stateless
public class MailStoreManagerBean implements MailStoreManager {
	
    @EJB
	private MailStoreDao mailStoreDao;

	public MailStoreDao getMailStoreDao() {
		return mailStoreDao;
	}

	public void storeMessages(Collection<MailMessage> messages) {
		
	}

	public Collection<String> getUids(MailBox mailBox, MailFolder mailFolder) {
		return getMailStoreDao().getMailMessageUids(mailBox, mailFolder);
	}

    public List<MailMessageSummary> getMessageSummaries(MailUser user, MailBox mailBox, MailFolder folder,
            ResultParams params) {
        return mailStoreDao.getMailMessageSummaries(mailBox, folder, params);
    }

    public MailBox getMailBox(MailUser user, String mailBoxToken) {
        return mailStoreDao.getMailBox(user, mailBoxToken);
    }

    public void saveMailBox(MailBox mailBox) {
        mailStoreDao.persist(mailBox);
        
    }

    public void saveMailUser(MailUser user) {
        mailStoreDao.persist(user);
    }
}
