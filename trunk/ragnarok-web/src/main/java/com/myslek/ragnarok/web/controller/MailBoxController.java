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
import java.util.List;

import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import com.myslek.ragnarok.domain.MailFolder;
import com.myslek.ragnarok.domain.MailMessageSummary;
import com.myslek.ragnarok.domain.MailUser;
import com.myslek.ragnarok.logic.MailManager;
import com.myslek.ragnarok.mail.exception.MailException;
import com.myslek.ragnarok.persistence.ResultParams;
import com.myslek.ragnarok.web.common.FacesUtils;
import com.myslek.ragnarok.web.common.MailConstatnts;

public class MailBoxController {

    private DataModel messages;

    @EJB
    private MailManager mailManager;

    private int firstItem = 0;
    private int batchSize = 10;
    private int currentFirstItem = 0;
    private boolean refresh = false;

    public DataModel getMessages() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        MailUser user = (MailUser) FacesUtils.getSessionAttribute(ctx, MailConstatnts.MAIL_USER);
        String mailBoxToken = FacesUtils.getRequestParam(ctx, MailConstatnts.MAILBOX_ID);
        MailFolder folder = FacesUtils.getRequestParamAsEnum(ctx, MailFolder.class,
                MailConstatnts.FOLDER);

        if (validateInput(user, mailBoxToken, folder)) {
            if (messages == null || currentFirstItem != firstItem) {
                messages = getNextMessages(user, mailBoxToken, folder);
            }
            return messages;
        } else {
            return new ListDataModel(Collections.EMPTY_LIST);
        }
    }

    protected DataModel getNextMessages(MailUser user, String mailBoxToken, MailFolder folder) {
        boolean syncWithMailServer = messages == null || refresh;
        ResultParams params = new ResultParams(firstItem, batchSize);
        List<MailMessageSummary> result = null;
        try {
            result = mailManager.getMessageSummaries(user, mailBoxToken,
                    folder, params, syncWithMailServer);
        } catch (MailException e) {
            //TODO: handle exception
        }
        messages = new ListDataModel(result);
        currentFirstItem = firstItem;
        return messages;
    }

    public int getMessageCount() {
        return 0;
    }

    private boolean validateInput(MailUser user, String mailBoxToken, MailFolder folder) {
        return user != null && mailBoxToken != null && folder != null;
    }
}
