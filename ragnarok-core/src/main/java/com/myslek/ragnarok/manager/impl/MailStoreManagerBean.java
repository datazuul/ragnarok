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
package com.myslek.ragnarok.manager.impl;

import java.util.Collection;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.myslek.ragnarok.domain.MailBox;
import com.myslek.ragnarok.domain.MailFolder;
import com.myslek.ragnarok.domain.MailMessage;
import com.myslek.ragnarok.domain.MailUser;
import com.myslek.ragnarok.manager.MailStoreManager;
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
		return getMailStoreDao().getUids(mailBox, mailFolder);
	}

    public List<MailMessage> getMessages(MailUser user, String mailBoxToken, MailFolder folder,
            ResultParams params) {
        MailBox mailBox = mailStoreDao.getMailBox(mailBoxToken);
        if (mailBox == null) {
            //TODO: throw application exception
        }
        boolean isUserMailBox = mailStoreDao.isUserMailBox(user, mailBox);
        if (!isUserMailBox) {
            //TODO: thorw application exception
        }
        
        return mailStoreDao.getMailMessages(mailBox, folder, params);
    }
}
