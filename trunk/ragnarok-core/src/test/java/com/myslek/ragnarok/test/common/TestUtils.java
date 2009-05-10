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
package com.myslek.ragnarok.test.common;

import java.io.UnsupportedEncodingException;

import javax.mail.internet.InternetAddress;

import com.myslek.ragnarok.domain.MailBox;
import com.myslek.ragnarok.domain.MailFolder;
import com.myslek.ragnarok.domain.MailMessage;
import com.myslek.ragnarok.domain.MailPart;
import com.myslek.ragnarok.domain.MailServer;
import com.myslek.ragnarok.domain.MailServerProtocol;

public final class TestUtils {

    /** The Constant FROM. */
    public static final InternetAddress FROM;

    /** The Constant TO. */
    public static final InternetAddress TO;

    /** The Constant CC1. */
    public static final InternetAddress CC1;

    /** The Constant CC2. */
    public static final InternetAddress CC2;

    /** The Constant BCC1. */
    public static final InternetAddress BCC1;

    /** The Constant BCC2. */
    public static final InternetAddress BCC2;

    /** The Constant SUBJECT. */
    public static final String SUBJECT = "Test MimeMessage";

    /** The Constant TEXT_PLAIN_CONTENT. */
    public static final String TEXT_PLAIN_CONTENT = "This a text/plain contant";

    /** The Constant TEXT_HTML_CONTENT. */
    public static final String TEXT_HTML_CONTENT = "<p>This a text/html content</p>";

    /** The Constant TEXT_PLAIN_TYPE. */
    public static final String TEXT_PLAIN_TYPE = "text/plain; charset=utf-8";

    /** The Constant TEXT_HTML_TYPE. */
    public static final String TEXT_HTML_TYPE = "text/html; charset=utf-8";

    /** The Constant MESSAGE_RFC822_TYPE. */
    public static final String MESSAGE_RFC822_TYPE = "message/rfc822";

    /** The Constant MULTIPART_MIXED_TYPE. */
    public static final String MULTIPART_MIXED_TYPE = "multipart/mixed";

    /** The Constant IMAGE_FILE. */
    public static final String IMAGE_FILE = "image.jpg";

    /** The Constant IMAGE_TYPE. */
    public static final String IMAGE_TYPE = "image/jpeg";

    static {
        try {
            FROM = new InternetAddress("from@example.com", "From");
            TO = new InternetAddress("to@example.com", "To");
            CC1 = new InternetAddress("cc1@example.com", "Cc1");
            CC2 = new InternetAddress("cc2@example.com", "Cc2");
            BCC1 = new InternetAddress("bcc1@example.com", "Bcc1");
            BCC2 = new InternetAddress("bcc2@example.com", "Bcc2");
        } catch (UnsupportedEncodingException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private TestUtils() {
    }

    public static MailBox createMailBox(String token) {
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
        mailBox.setToken(token);
        mailBox.setDefaultMailBox(true);
        mailBox.setMailStore(in);
        mailBox.setMailTransport(out);

        return mailBox;
    }

    public static MailMessage createMailMessage(MailBox mailBox, MailFolder folder, String token,
            String uid) {
        MailMessage mailMessage = new MailMessage();
        mailMessage.setMailBox(mailBox);
        mailMessage.setFolder(folder);
        mailMessage.setToken(token);
        mailMessage.setUid(uid);
        mailMessage.addFrom(FROM);
        mailMessage.addTo(TO);
        mailMessage.addCc(CC1);
        mailMessage.addCc(CC2);
        mailMessage.addBcc(BCC1);
        mailMessage.addBcc(BCC2);

        mailMessage.setSubject(SUBJECT);

        return mailMessage;
    }

    public static MailMessage createMultipartMailMessage(MailBox mailBox, MailFolder folder,
            String token, String uid) {
        MailMessage mailMessage = createMailMessage(mailBox, folder, token, uid);
        mailMessage.setContentType(MULTIPART_MIXED_TYPE);

        MailPart part1 = new MailPart();
        part1.setText(TEXT_PLAIN_CONTENT);
        part1.setContentType(TEXT_PLAIN_TYPE);

        MailPart part2 = new MailPart();
        part2.setText(TEXT_HTML_CONTENT);
        part2.setContentType(TEXT_HTML_TYPE);

        MailPart multiPart = new MailPart();
        multiPart.setContentType(MULTIPART_MIXED_TYPE);
        multiPart.addPart(part1);
        multiPart.addPart(part2);

        mailMessage.addPart(multiPart);

        return mailMessage;
    }
}
