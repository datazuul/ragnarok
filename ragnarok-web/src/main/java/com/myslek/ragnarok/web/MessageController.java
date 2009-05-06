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
package com.myslek.ragnarok.web;

import javax.ejb.EJB;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import com.myslek.ragnarok.domain.MailMessage;
import com.myslek.ragnarok.manager.MailManagerFacade;

public class MessageController {

    private MailMessage message;

    private DataModel messages;

    @EJB
    private MailManagerFacade mailManagerFacade;

    private int firstItem = 0;
    private int currentItem = 0;
    private int batchSize = 10;

    public DataModel getMessages() {
        if (messages == null || currentItem != firstItem) {
            messages = getNextMessages();
        }
        return messages;
    }
    
    protected DataModel getNextMessages() {
        messages = new ListDataModel();//new ListDataModel(mailFacade.getMessages(firstItem, batchSize));
        currentItem = firstItem;
        return messages;
    }
    
    protected int getMessageCount() {
        return 0;
    }
}
