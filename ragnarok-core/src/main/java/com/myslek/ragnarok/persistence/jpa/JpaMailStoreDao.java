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
package com.myslek.ragnarok.persistence.jpa;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.seam.annotations.Name;

import com.myslek.ragnarok.domain.MailBox;
import com.myslek.ragnarok.domain.MailFolder;
import com.myslek.ragnarok.domain.MailMessage;
import com.myslek.ragnarok.domain.MailUser;
import com.myslek.ragnarok.persistence.MailStoreDao;
import com.myslek.ragnarok.persistence.ResultParams;

@Stateless
@Name("mailStoreDao")
public class JpaMailStoreDao implements MailStoreDao {

	@PersistenceContext
	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}

    public Object find(Class entityClass, Object primaryKey) {
        // TODO Auto-generated method stub
        return null;
    }

    public MailBox getMailBox(int mailBoxId) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<MailBox> getMailBoxes(MailUser user) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<MailMessage> getMailMessages(MailBox mailBox, MailFolder folder, ResultParams params) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<String> getUids(MailBox mailBox, MailFolder folder) {
        // TODO Auto-generated method stub
        return null;
    }

    public MailUser getUser(String username) {
        // TODO Auto-generated method stub
        return null;
    }

    public MailUser getUser(String username, String password) {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean isUserMailBox(MailUser mailUser, MailBox mailBox) {
        // TODO Auto-generated method stub
        return false;
    }

    public void persist(Object entity) {
        // TODO Auto-generated method stub
        
    }
}