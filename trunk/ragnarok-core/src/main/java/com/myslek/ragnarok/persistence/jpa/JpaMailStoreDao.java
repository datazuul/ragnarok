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
import javax.persistence.Query;

import com.myslek.ragnarok.domain.MailBox;
import com.myslek.ragnarok.domain.MailFolder;
import com.myslek.ragnarok.domain.MailMessage;
import com.myslek.ragnarok.domain.MailUser;
import com.myslek.ragnarok.persistence.MailStoreDao;
import com.myslek.ragnarok.persistence.ResultParams;

@Stateless
public class JpaMailStoreDao implements MailStoreDao {

    @PersistenceContext
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @SuppressWarnings(value = "unchecked")
    public Object find(Class entityClass, Object primaryKey) {
        return entityManager.find(entityClass, primaryKey);
    }

    public MailBox getMailBox(int mailBoxId) {
        Query query = entityManager.createQuery("from MailBox mb where mb.id = :mailBoxId");
        query.setParameter("mailBoxId", mailBoxId);

        return (MailBox) query.getSingleResult();
    }

    @SuppressWarnings(value = "unchecked")
    public List<MailBox> getMailBoxes(MailUser user) {
        Query query = entityManager.createQuery("from MailBox mb where mb.user.id = :userId");
        query.setParameter("userId", user.getId());

        return query.getResultList();
    }

    @SuppressWarnings(value = "unchecked")
    public List<MailMessage> getMailMessages(MailBox mailBox, MailFolder folder, ResultParams params) {
        Query query = entityManager
                .createQuery("from MailMessage m where m.mailBox.id = :mailBoxId and m.folder = :folder");
        query.setParameter("mailBoxId", mailBox.getId());
        query.setParameter("folder", folder);

        query.setFirstResult(params.getFirstItem());
        query.setMaxResults(params.getBatchSize());

        return query.getResultList();
    }

    @SuppressWarnings(value = "unchecked")
    public List<String> getUids(MailBox mailBox, MailFolder folder) {
        Query query = entityManager
                .createQuery("select m.uid from MailMessage m where m.mailBox.id = :mailBoxId and m.folder = :folder");
        query.setParameter("mailBoxId", mailBox.getId());
        query.setParameter("folder", folder);

        return query.getResultList();
    }

    public MailUser getUser(String username) {
        Query query = entityManager.createQuery("from MailUser u where u.username = :username");
        query.setParameter("username", username);

        return (MailUser) query.getSingleResult();
    }

    public MailUser getUser(String username, String password) {
        Query query = entityManager
                .createQuery("from MailUser u where u.username = :username and u.password = :password");
        query.setParameter("username", username);
        query.setParameter("password", password);
        
        return (MailUser) query.getSingleResult();
    }

    public boolean isUserMailBox(MailUser mailUser, MailBox mailBox) {
        // TODO Auto-generated method stub
        return false;
    }

    public void persist(Object entity) {
        entityManager.persist(entity);
    }

    public MailMessage getCompleteMessage(int messageId) {
        // TODO Auto-generated method stub
        return null;
    }
}