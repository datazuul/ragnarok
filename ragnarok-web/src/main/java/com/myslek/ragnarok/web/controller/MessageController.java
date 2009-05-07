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
package com.myslek.ragnarok.web.controller;

import java.util.Collections;

import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import com.myslek.ragnarok.domain.MailFolder;
import com.myslek.ragnarok.domain.MailUser;
import com.myslek.ragnarok.manager.MailManagerFacade;
import com.myslek.ragnarok.persistence.ResultParams;
import com.myslek.ragnarok.web.common.FacesUtils;
import com.myslek.ragnarok.web.common.MailConstatnts;

public class MessageController {

    private DataModel messages;

    @EJB
    private MailManagerFacade mailFacade;

    private int firstItem = 0;
    private int currentItem = 0;
    private int batchSize = 10;

    public DataModel getMessages() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        MailUser user = (MailUser) FacesUtils.getSessionAttribute(ctx, MailConstatnts.MAIL_USER);
        int mailBoxId = FacesUtils.getRequestParamAsInt(ctx, MailConstatnts.MAILBOX_ID);
        MailFolder folder = FacesUtils.getRequestParamAsEnum(ctx, MailFolder.class,
                MailConstatnts.FOLDER);
        if (validateInput(user, mailBoxId, folder)) {
            if (messages == null || currentItem != firstItem) {
                messages = getNextMessages(user, mailBoxId, folder);
            }
            return messages;
        } else {
            return new ListDataModel(Collections.EMPTY_LIST);
        }
    }

    protected DataModel getNextMessages(MailUser user, int mailBoxId, MailFolder folder) {
        ResultParams params = new ResultParams(firstItem, batchSize);
        messages = new ListDataModel(mailFacade.getMessages(user, mailBoxId, folder, params));
        currentItem = firstItem;
        return messages;
    }

    public int getMessageCount() {
        return 0;
    }

    private boolean validateInput(MailUser user, int mailBoxId, MailFolder folder) {
        return user != null && mailBoxId != -1 && folder != null;
    }
}
