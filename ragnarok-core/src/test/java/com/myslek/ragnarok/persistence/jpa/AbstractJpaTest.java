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

import java.sql.Connection;
import java.sql.DriverManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;

import junit.framework.TestCase;

public abstract class AbstractJpaTest extends TestCase {

    private static Logger LOG = Logger.getLogger(AbstractJpaTest.class);

    private EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;

    private Connection connection;

    private static final String UNIT_NAME = "ragnarok";

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        try {
            LOG.info("Starting in-memory HSQL database for unit tests");
            Class.forName("org.hsqldb.jdbcDriver");
            connection = DriverManager.getConnection("jdbc:hsqldb:mem:unit-testing-jpa", "sa", "");
        } catch (Exception e) {
            LOG.error(e, e);
            fail("Exception during HSQL database startup.");
        }
        try {
            LOG.info("Building JPA EntityManager for unit tests");
            entityManagerFactory = Persistence.createEntityManagerFactory(UNIT_NAME);
            entityManager = entityManagerFactory.createEntityManager();
        } catch (Exception e) {
            LOG.error(e, e);
            fail("Exception during JPA EntityManager instanciation.");
        }
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        LOG.info("Shuting down Hibernate JPA layer.");
        if (entityManager != null) {
            entityManager.close();
        }
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
        LOG.info("Stopping in-memory HSQL database.");
        try {
            connection.createStatement().execute("SHUTDOWN");
        } catch (Exception e) {
            LOG.error(e, e);
        }
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void beginTransaction() {
        entityManager.getTransaction().begin();
    }

    public void commitTransaction() {
        entityManager.getTransaction().commit();
    }
}
