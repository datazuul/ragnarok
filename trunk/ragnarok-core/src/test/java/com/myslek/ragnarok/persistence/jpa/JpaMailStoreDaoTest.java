/*
 * Copyright 2009 Rafal Myslek <rafal.myslek@gmail.com>
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

import org.apache.log4j.Logger;

import com.myslek.ragnarok.domain.MailBox;
import com.myslek.ragnarok.domain.MailFolder;
import com.myslek.ragnarok.domain.MailMessage;
import com.myslek.ragnarok.domain.MailUser;
import com.myslek.ragnarok.persistence.ResultParams;
import com.myslek.ragnarok.test.common.TestUtils;

public class JpaMailStoreDaoTest extends AbstractJpaTest {

    private static Logger LOG = Logger.getLogger(JpaMailStoreDaoTest.class);

    private JpaMailStoreDao mailStoreDao;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mailStoreDao = new JpaMailStoreDao();
        mailStoreDao.setEntityManager(getEntityManager());
        createInitialData();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        mailStoreDao = null;
    }

    public void testGetUserByName() throws Exception {
        beginTransaction();
        MailUser user = mailStoreDao.getUser("ragnarok");
        commitTransaction();
        assertNotNull("user should not be null", user);
        LOG.info("### userId: [" + user.getId() + "] ###");
        assertEquals("user name should be 'ragnarok'", "ragnarok", user.getUsername());
    }

    public void testGetUserByNameAndPassword() throws Exception {
        beginTransaction();
        MailUser user = mailStoreDao.getUser("ragnarok", "ragnarok");
        commitTransaction();
        assertNotNull("user should not be null", user);
        LOG.info("### userId: [" + user.getId() + "] ###");
        assertEquals("user name should be 'ragnarok'", "ragnarok", user.getUsername());
        assertEquals("user password should be 'ragnarok'", "ragnarok", user.getPassword());
    }

    public void testGetMailBoxesByUser() throws Exception {
        beginTransaction();
        MailUser user = mailStoreDao.getUser("ragnarok", "ragnarok");
        assertNotNull("user should not be null", user);

        List<MailBox> mailBoxes = mailStoreDao.getMailBoxes(user);
        commitTransaction();
        assertNotNull("mailBoxes should be not null", mailBoxes);
        assertEquals("mailBoxes size should be: 1", 1, mailBoxes.size());
    }
    
    public void testGetMessagesByMailBoxAndFolder() throws Exception {
        beginTransaction();
        MailUser user = mailStoreDao.getUser("ragnarok", "ragnarok");
        assertNotNull("user should not be null", user);
        List<MailBox> mailBoxes = mailStoreDao.getMailBoxes(user);
        assertNotNull("mailBoxes should be not null", mailBoxes);
        assertEquals("mailBoxes size should be: 1", 1, mailBoxes.size());
        MailBox mailBox = mailBoxes.get(0);
        ResultParams params = new ResultParams(0, 10);
        List<MailMessage> messages = mailStoreDao.getMailMessages(mailBox, MailFolder.INBOX, params);
        commitTransaction();
        assertNotNull("messages should be not null", messages);
        assertEquals("messages size should be: 1", 1, messages.size());
    }

    

    public void createInitialData() {
        beginTransaction();
        MailUser user = new MailUser();
        user.setUsername("ragnarok");
        user.setPassword("ragnarok");

        MailBox mailBox = TestUtils.createMailBox();

        user.addMailBox(mailBox);

        MailMessage message = TestUtils.createMultipartMailMessage(mailBox, MailFolder.INBOX);

        getEntityManager().persist(mailBox);
        getEntityManager().persist(message);
        commitTransaction();
    }
}
