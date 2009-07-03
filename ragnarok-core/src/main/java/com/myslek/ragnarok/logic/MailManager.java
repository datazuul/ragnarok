package com.myslek.ragnarok.logic;

import java.util.List;

import javax.ejb.Local;

import com.myslek.ragnarok.domain.MailBox;
import com.myslek.ragnarok.domain.MailFolder;
import com.myslek.ragnarok.domain.MailMessageSummary;
import com.myslek.ragnarok.domain.MailUser;
import com.myslek.ragnarok.mail.exception.MailException;
import com.myslek.ragnarok.persistence.ResultParams;

// TODO: Auto-generated Javadoc
/**
 * Facade interface that provides one entry point to all the messaging
 * components (i.e. connectivity, storage)
 */
@Local
public interface MailManager {

    List<MailMessageSummary> getMessageSummaries(MailUser user, String mailBoxToken,
            MailFolder folder, ResultParams params, boolean syncWithMailServer)
            throws MailException;

    void saveMailUser(MailUser user) throws MailException;

    void saveMailBox(MailBox mailBox) throws MailException;
    
    public void removeMailUser(String username);
    
    public MailUser getMailUser(String username);
    
    public MailUser getMailUser(String username, String password);
}
