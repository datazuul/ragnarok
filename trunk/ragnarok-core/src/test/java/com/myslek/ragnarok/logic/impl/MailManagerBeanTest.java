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
package com.myslek.ragnarok.logic.impl;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import junit.framework.TestCase;

import com.myslek.ragnarok.domain.MailUser;
import com.myslek.ragnarok.logic.MailManager;
import com.myslek.ragnarok.mail.exception.MailException;

public class MailManagerBeanTest extends TestCase {
    
    private Context context;
    
    private MailManager mailManager;
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY,
                "org.apache.openejb.client.LocalInitialContextFactory");
        props.setProperty("openejb.altdd.prefix", "test.");

        props.put("testdb", "new://Resource?type=DataSource");
        props.put("testdb.JdbcDriver", "org.h2.Driver");
        props.put("testdb.JdbcUrl", "jdbc:h2:mem:mailmanager");

        props.put("testdbUnmanaged", "new://Resource?type=DataSource");
        props.put("testdbUnmanaged.JdbcDriver", "org.h2.Driver");
        props.put("testdbUnmanaged.JdbcUrl", "jdbc:h2:mem:mailmanager");
        props.put("testdbUnmanaged.JtaManaged", "false");

        context = new InitialContext(props);

        mailManager = (MailManager) context.lookup("ejb/MailManagerLocal");
    }
    
    public void testSaveMailUser() {
        MailUser user = new MailUser();
        user.setUsername("ragnarok-user");
        user.setPassword("ragnarok");
        
        try {
            mailManager.saveMailUser(user);
        } catch (MailException e) {
            fail(e.getMessage());
        }
    }
}
