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
    
    private static final String USERNAME = "ragnarok";
    
    private static final String PASSWORD = "ragnarok";

    private static final String MAILBOX_TOKEN = "WERTYUBVCD";

    private static final String MESSAGE_TOKEN = "KQRZEWDCJL";

    private static final String MESSAGE_UID = "XFTRQWEC";

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
        MailUser user = mailStoreDao.getUser(USERNAME);
        commitTransaction();
        assertNotNull("user should not be null", user);
        LOG.info("### userId: [" + user.getId() + "] ###");
        assertEquals("user name should be '" + USERNAME + "'", USERNAME, user.getUsername());
    }

    public void testGetUserByNameAndPassword() throws Exception {
        beginTransaction();
        MailUser user = mailStoreDao.getUser(USERNAME, PASSWORD);
        commitTransaction();
        assertNotNull("user should not be null", user);
        LOG.info("### userId: [" + user.getId() + "] ###");
        assertEquals("user name should be '" + USERNAME + "'", USERNAME, user.getUsername());
        assertEquals("user password should be '" + PASSWORD + "'", PASSWORD, user.getPassword());
    }

    public void testGetMailBoxesByExistingUser() throws Exception {
        beginTransaction();
        MailUser user = mailStoreDao.getUser(USERNAME, PASSWORD);
        assertNotNull("user should not be null", user);

        List<MailBox> mailBoxes = mailStoreDao.getMailBoxes(user);
        commitTransaction();
        assertNotNull("mailBoxes should not be null", mailBoxes);
        assertEquals("mailBoxes size should be: 1", 1, mailBoxes.size());
    }
    
    public void testGetMailBoxByExistingUserAndToken() throws Exception {
        beginTransaction();
        MailUser user = mailStoreDao.getUser(USERNAME, PASSWORD);
        assertNotNull("user should not be null", user);
        
        MailBox mailBox = mailStoreDao.getMailBox(user, MAILBOX_TOKEN);
        commitTransaction();
        assertNotNull("mailBox should not be null", mailBox);
    }

    public void testGetMessagesByMailBoxAndFolder() throws Exception {
        beginTransaction();
        MailUser user = mailStoreDao.getUser(USERNAME, PASSWORD);
        assertNotNull("user should not be null", user);
        
        MailBox mailBox = mailStoreDao.getMailBox(user, MAILBOX_TOKEN);
        assertNotNull("mailBox should not be null", mailBox);
        ResultParams params = new ResultParams(0, 10);
        List<MailMessage> messages = mailStoreDao
                .getMailMessages(mailBox, MailFolder.INBOX, params);
        commitTransaction();
        assertNotNull("messages should not be null", messages);
        assertEquals("messages size should be: 1", 1, messages.size());
    }
    
    public void testGetCompleteMessage() throws Exception {
        beginTransaction();
        MailUser user = mailStoreDao.getUser(USERNAME, PASSWORD);
        assertNotNull("user should not be null", user);
        
        MailMessage message = mailStoreDao.getCompleteMessage(user, MESSAGE_TOKEN);
        assertNotNull("message should not be null", message);
    }

    public void createInitialData() {
        beginTransaction();
        MailUser user = new MailUser();
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);

        MailBox mailBox = TestUtils.createMailBox(MAILBOX_TOKEN);

        user.addMailBox(mailBox);

        MailMessage message = TestUtils.createMultipartMailMessage(mailBox, MailFolder.INBOX,
                MESSAGE_TOKEN, MESSAGE_UID);

        getEntityManager().persist(mailBox);
        getEntityManager().persist(message);
        commitTransaction();
    }
}
