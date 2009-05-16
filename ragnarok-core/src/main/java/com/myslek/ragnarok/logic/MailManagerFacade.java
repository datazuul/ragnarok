package com.myslek.ragnarok.logic;

import java.util.List;

import javax.ejb.Local;

import com.myslek.ragnarok.domain.MailFolder;
import com.myslek.ragnarok.domain.MailMessageSummary;
import com.myslek.ragnarok.domain.MailUser;
import com.myslek.ragnarok.persistence.ResultParams;

// TODO: Auto-generated Javadoc
/**
 * Facade interface that provides one entry point to all the messaging
 * components (i.e. connectivity, storage)
 */
@Local
public interface MailManagerFacade {

    List<MailMessageSummary> getMessageSummaries(MailUser user, String mailBoxToken, MailFolder folder,
            ResultParams params, boolean synchronize);
}
