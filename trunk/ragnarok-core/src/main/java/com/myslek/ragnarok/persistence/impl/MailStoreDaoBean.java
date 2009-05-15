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
package com.myslek.ragnarok.persistence.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.myslek.ragnarok.domain.MailBox;
import com.myslek.ragnarok.domain.MailFolder;
import com.myslek.ragnarok.domain.MailMessage;
import com.myslek.ragnarok.domain.MailMessageSummary;
import com.myslek.ragnarok.domain.MailUser;
import com.myslek.ragnarok.persistence.MailStoreDao;
import com.myslek.ragnarok.persistence.ResultParams;

@Stateless(name = "ejb/MailStoreDao")
public class MailStoreDaoBean implements MailStoreDao {

    @PersistenceContext
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @SuppressWarnings(value = "unchecked")
    public Object find(Class entityClass, Object primaryKey) {
        return entityManager.find(entityClass, primaryKey);
    }

    public MailBox getMailBox(MailUser user, String token) {
        Query query = entityManager
                .createQuery("from MailBox mb where mb.token = :token and mb.user.id = :userId");
        query.setParameter("token", token);
        query.setParameter("userId", user.getId());

        return (MailBox) query.getSingleResult();
    }

    @SuppressWarnings(value = "unchecked")
    public List<MailBox> getMailBoxes(MailUser user) {
        Query query = entityManager.createQuery("from MailBox mb where mb.user.id = :userId");
        query.setParameter("userId", user.getId());

        return query.getResultList();
    }

    @SuppressWarnings(value = "unchecked")
    public List<MailMessageSummary> getMailMessageSummaries(MailBox mailBox, MailFolder folder,
            ResultParams params) {
        Query query = entityManager
                .createQuery("select new com.myslek.ragnarok.domain.MailMessageSummary(m.token, m.from, m.subject, m.contentType, m.size, m.sentDate, m.receivedDate) "
                        + "from MailMessage m where m.mailBox.id = :mailBoxId and m.folder = :folder");
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

    public void persist(Object entity) {
        entityManager.persist(entity);
    }

    public MailMessage getMailMessageDetail(MailUser user, String token) {
        Query query = entityManager.createQuery("select m from MailMessage m, MailBox mb "
                + "where m.token = :token and m.mailBox.id = mb.id " + "and mb.user.id = :userId");
        query.setParameter("token", token);
        query.setParameter("userId", user.getId());

        return (MailMessage) query.getSingleResult();
    }

    @SuppressWarnings(value = "unchecked")
    public List<MailMessage> getAllMessages(MailUser user) {
        Query query = entityManager
                .createQuery("from MailMessage m where m.mailBox.id in (select mb.id from MailBox mb where mb.user.id = :userId)");
        query.setParameter("userId", user.getId());
        return query.getResultList();
    }

    public void removeUser(String username) {
        // TODO: the remove operation should be performed in a 'bulk update'
        MailUser user = getUser(username);
        if (user == null) {
            // TODO: throw Application Exception
        }
        List<MailMessage> messages = getAllMessages(user);
        for (MailMessage message : messages) {
            entityManager.remove(message);
        }
        List<MailBox> mailBoxes = getMailBoxes(user);
        for (MailBox mailBox : mailBoxes) {
            entityManager.remove(mailBox);
        }
        entityManager.remove(user);
    }
}