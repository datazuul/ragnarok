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
package com.myslek.ragnarok.persistence.impl;

import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import junit.framework.TestCase;

import com.myslek.ragnarok.domain.MailBox;
import com.myslek.ragnarok.domain.MailFolder;
import com.myslek.ragnarok.domain.MailMessage;
import com.myslek.ragnarok.domain.MailUser;
import com.myslek.ragnarok.persistence.MailStoreDao;
import com.myslek.ragnarok.persistence.ResultParams;
import com.myslek.ragnarok.test.common.MailTestUtils;

public class MailStoreDaoBeanTest extends TestCase {

    private static final String USERNAME = "ragnarok";

    private static final String PASSWORD = "ragnarok";

    private static final String MAILBOX_TOKEN = "WERTYUBVCD";

    private static final String MESSAGE_TOKEN = "KQRZEWDCJL";

    private static final String MESSAGE_UID = "XFTRQWEC";

    private Context context;

    private MailStoreDao mailStoreDao;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY,
                "org.apache.openejb.client.LocalInitialContextFactory");
        props.setProperty("openejb.altdd.prefix", "test");

        props.put("testdb", "new://Resource?type=DataSource");
        props.put("testdb.JdbcDriver", "org.h2.Driver");
        props.put("testdb.JdbcUrl", "jdbc:h2:mem:testdb");

        props.put("testdbUnmanaged", "new://Resource?type=DataSource");
        props.put("testdbUnmanaged.JdbcDriver", "org.h2.Driver");
        props.put("testdbUnmanaged.JdbcUrl", "jdbc:h2:mem:testdb");
        props.put("testdbUnmanaged.JtaManaged", "false");

        context = new InitialContext(props);

        mailStoreDao = (MailStoreDao) context.lookup("ejb/MailStoreDaoLocal");
        createInitialData(mailStoreDao);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        dropInitialData(mailStoreDao);
    }

    public void testGetUserByName() throws Exception {
        MailUser user = mailStoreDao.getUser(USERNAME);
        assertNotNull("user should not be null", user);
        assertEquals("user name should be '" + USERNAME + "'", USERNAME, user.getUsername());
    }

    public void testGetUserByNameAndPassword() throws Exception {
        MailUser user = mailStoreDao.getUser(USERNAME, PASSWORD);
        assertNotNull("user should not be null", user);
        assertEquals("user name should be '" + USERNAME + "'", USERNAME, user.getUsername());
        assertEquals("user password should be '" + PASSWORD + "'", PASSWORD, user.getPassword());
    }

    public void testGetMailBoxesByExistingUser() throws Exception {
        MailUser user = mailStoreDao.getUser(USERNAME, PASSWORD);
        assertNotNull("user should not be null", user);

        List<MailBox> mailBoxes = mailStoreDao.getMailBoxes(user);
        assertNotNull("mailBoxes should not be null", mailBoxes);
        assertEquals("mailBoxes size should be: 1", 1, mailBoxes.size());
    }

    public void testGetMailBoxByExistingUserAndToken() throws Exception {
        MailUser user = mailStoreDao.getUser(USERNAME, PASSWORD);
        assertNotNull("user should not be null", user);

        MailBox mailBox = mailStoreDao.getMailBox(user, MAILBOX_TOKEN);
        assertNotNull("mailBox should not be null", mailBox);
    }

    public void testGetMessagesByMailBoxAndFolder() throws Exception {
        MailUser user = mailStoreDao.getUser(USERNAME, PASSWORD);
        assertNotNull("user should not be null", user);

        MailBox mailBox = mailStoreDao.getMailBox(user, MAILBOX_TOKEN);
        assertNotNull("mailBox should not be null", mailBox);
        ResultParams params = new ResultParams(0, 10);
        List<MailMessage> messages = mailStoreDao
                .getMailMessages(mailBox, MailFolder.INBOX, params);
        assertNotNull("messages should not be null", messages);
        assertEquals("messages size should be: 1", 1, messages.size());
    }

    public void testGetCompleteMessage() throws Exception {
        MailUser user = mailStoreDao.getUser(USERNAME, PASSWORD);
        assertNotNull("user should not be null", user);

        MailMessage message = mailStoreDao.getCompleteMessage(user, MESSAGE_TOKEN);
        assertNotNull("message should not be null", message);
    }

    private void createInitialData(MailStoreDao mailStoreDao) {
        MailUser user = new MailUser();
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);

        MailBox mailBox = MailTestUtils.createMailBox(MAILBOX_TOKEN);

        user.addMailBox(mailBox);

        MailMessage message = MailTestUtils.createMultipartMailMessage(mailBox, MailFolder.INBOX,
                MESSAGE_TOKEN, MESSAGE_UID);

        mailStoreDao.persist(mailBox);
        mailStoreDao.persist(message);
    }

    private void dropInitialData(MailStoreDao mailStoreDao) {
        mailStoreDao.removeUser(USERNAME);
    }
}
