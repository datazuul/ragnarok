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
package com.myslek.ragnarok.mail.exception;

public class MailException extends ApplicationException {

    private static final long serialVersionUID = 1L;

    private MailExceptionReason reason;

    public MailException(MailExceptionReason reason) {
        this.reason = reason;
    }

    public MailException(MailExceptionReason reason, Throwable cause) {
        super(cause);
        this.reason = reason;
    }

    public MailExceptionReason getReason() {
        return reason;
    }
}
