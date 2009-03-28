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
package com.myslek.ragnarok.util;

import java.util.Arrays;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import com.myslek.ragnarok.mail.InvalidAddressException;

public final class MailUtils {

	private MailUtils() {

	}

	public static List<InternetAddress> getAddressListFromString(String addresses) {
		try {
			InternetAddress[] ret = InternetAddress.parse(addresses);
			return Arrays.asList(ret);
		} catch (AddressException e) {
			throw new InvalidAddressException(e);
		}
	}

	public static String getAddressStringFromList(List<InternetAddress> list) {
		return InternetAddress.toString(list.toArray(new InternetAddress[list.size()]));
	}
}
