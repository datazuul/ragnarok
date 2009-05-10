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
package com.myslek.ragnarok.persistence;

import java.util.List;

import javax.ejb.Local;

import com.myslek.ragnarok.domain.MailBox;
import com.myslek.ragnarok.domain.MailFolder;
import com.myslek.ragnarok.domain.MailMessage;
import com.myslek.ragnarok.domain.MailUser;

@Local
public interface MailStoreDao {

    @SuppressWarnings(value = "unchecked")
    Object find(Class entityClass, Object primaryKey);

    void persist(Object entity);

    MailUser getUser(String username);

    MailUser getUser(String username, String password);

    List<MailBox> getMailBoxes(MailUser user);

    MailBox getMailBox(String token);

    boolean isUserMailBox(MailUser mailUser, MailBox mailBox);

    List<String> getUids(MailBox mailBox, MailFolder folder);

    List<MailMessage> getMailMessages(MailBox mailBox, MailFolder folder, ResultParams params);
    
    MailMessage getCompleteMessage(String token);
}
