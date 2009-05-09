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

import com.bm.testsuite.BaseSessionBeanFixture;
import com.bm.testsuite.dataloader.EntityInitialDataSet;
import com.myslek.ragnarok.domain.MailBox;
import com.myslek.ragnarok.domain.MailHeader;
import com.myslek.ragnarok.domain.MailMessage;
import com.myslek.ragnarok.domain.MailPart;
import com.myslek.ragnarok.domain.MailServer;
import com.myslek.ragnarok.domain.MailServerProtocol;
import com.myslek.ragnarok.domain.MailUser;
import com.myslek.ragnarok.persistence.jpa.JpaMailStoreDao;

public class JpaMailStoreDaoTest extends BaseSessionBeanFixture<JpaMailStoreDao> {

    @SuppressWarnings(value = "unchecked")
    private static final Class[] usedEntityBeans = { MailBox.class, MailHeader.class,
            MailMessage.class, MailPart.class, MailServer.class, MailUser.class };

    public JpaMailStoreDaoTest() {
        super(JpaMailStoreDao.class, usedEntityBeans, new MailUserInitialDataSet());
    }

    public void testDependencyInjection() throws Exception {
        final JpaMailStoreDao bean = this.getBeanToTest();
        assertNotNull("bean should not be null", bean);
        assertNotNull("entity manager should not be null", bean.getEntityManager());
    }

    public void testGetUserByName() throws Exception {
        final JpaMailStoreDao bean = this.getBeanToTest();
        assertNotNull("bean should not be null", bean);
        MailUser user = bean.getUser("ragnarok");
        assertNotNull("user should not be null", user);
        assertEquals("user name should be 'ragnarok'", "ragnarok", user.getUsername());
    }

    static class MailUserInitialDataSet extends EntityInitialDataSet<MailUser> {

        public MailUserInitialDataSet() {
            super(MailUser.class);
        }

        public void create() {
            MailUser user = new MailUser();
            user.setUsername("ragnarok");
            user.setPassword("ragnarok");

            MailServer in = new MailServer();
            in.setHostname("localhost");
            in.setUsername("mailuser");
            in.setPassword("mailuser");
            in.setProtocol(MailServerProtocol.POP3);

            MailServer out = new MailServer();
            out.setHostname("localhost");
            out.setUsername("mailuser");
            out.setPassword("mailuser");
            out.setProtocol(MailServerProtocol.SMTP);

            MailBox mailBox = new MailBox();
            mailBox.setDefaultMailBox(true);
            mailBox.setMailStore(in);
            mailBox.setMailTransport(out);

            user.addMailBox(mailBox);

            this.add(user);
        }
    }
}
