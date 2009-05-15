package com.myslek.ragnarok.logic;

import java.util.List;

import javax.ejb.Local;

import com.myslek.ragnarok.domain.MailBox;
import com.myslek.ragnarok.domain.MailFolder;
import com.myslek.ragnarok.domain.MailMessageSummary;
import com.myslek.ragnarok.domain.MailUser;
import com.myslek.ragnarok.mail.MessageFilter;
import com.myslek.ragnarok.persistence.ResultParams;

// TODO: Auto-generated Javadoc
/**
 * Facade interface that provides one entry point to all the messaging
 * components (i.e. connectivity, storage)
 */
@Local
public interface MailManagerFacade {

    /**
     * Fetch and store messages.
     * 
     * @param mailBox
     *            the mail box
     * @param filter
     *            the filter
     */
    void fetchAndStoreMessages(MailBox mailBox, MessageFilter filter);

    List<MailMessageSummary> getMessageSummaries(MailUser user, String mailBoxToken, MailFolder folder,
            ResultParams params);
}
