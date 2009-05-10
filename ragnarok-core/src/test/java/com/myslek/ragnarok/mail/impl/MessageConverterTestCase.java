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
package com.myslek.ragnarok.mail.impl;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;

import junit.framework.Assert;

import com.myslek.ragnarok.domain.MailMessage;
import com.myslek.ragnarok.domain.MailPart;
import com.myslek.ragnarok.mail.MailSession;
import com.myslek.ragnarok.util.IOUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class MessageConverterTest.
 */
public class MessageConverterTestCase extends AbstractMailTestCase {

	/**
	 * Test convert from text plain message.
	 * 
	 * @throws Exception the exception
	 */
	public void testConvertFromTextPlainMessage() throws Exception {
		Message message = createTextPlainMessage();
		MailMessage mailMessage = getMessageConverter().fromMessage(message);

		Assert.assertNotNull(mailMessage);
		Assert.assertEquals(message.getContentType(), mailMessage.getContentType());
		Assert.assertEquals(message.getSubject(), mailMessage.getSubject());
		Assert.assertEquals(SUBJECT, mailMessage.getSubject());
		Assert.assertEquals((String) message.getContent(), mailMessage.getText());
		Assert.assertEquals(TEXT_PLAIN_CONTENT, mailMessage.getText());
	}

	/**
	 * Test convert from multipart message.
	 * 
	 * @throws Exception the exception
	 */
	public void testConvertFromMultipartMessage() throws Exception {
		Message message = createMultipartMessage();
		MailMessage mailMessage = getMessageConverter().fromMessage(message);

		Assert.assertNotNull(mailMessage);
		Assert.assertEquals("ContentType is both messages must be the same", message.getContentType(), 
				mailMessage.getContentType());
		Assert.assertEquals(SUBJECT, mailMessage.getSubject());
		Assert.assertNotNull("MailMessage parts must not be null", mailMessage.getParts());
		Assert.assertEquals("Expected MailMessage parts size: 1", 1, mailMessage.getParts().size());

		MailPart multiPart = mailMessage.getParts().get(0);
		Assert.assertNotNull("MailMessage multiPart must not be null", multiPart);
		Assert.assertEquals("Expected MailMessage multiPart parts size: 2", 2, multiPart.getParts().size());
		
		MailPart part1 = multiPart.getParts().get(0);
		Assert.assertNotNull("First part of the MailMessage multiPart must not be null", part1);
		Assert.assertEquals("Expected contentType of 1st part of the MailMessage multiPart is: "
						+ TEXT_PLAIN_TYPE, TEXT_PLAIN_TYPE, part1.getContentType());
		Assert.assertEquals("Expected content of the 1st part of the MailMessage multiPart is: "
						+ TEXT_PLAIN_CONTENT, TEXT_PLAIN_CONTENT, part1.getText());
		MailPart part2 = multiPart.getParts().get(1);
		Assert.assertNotNull("Second part of the MailMessage multiPart must not be null", part2);
		Assert.assertEquals("Expected contentType of the 2nd part of the MailMessage multiPart is: "
						+ TEXT_HTML_TYPE, TEXT_HTML_TYPE, part2.getContentType());
		Assert.assertEquals("Expected content of the 2nd part of the MailMessage multiPart is: "
						+ TEXT_HTML_CONTENT, TEXT_HTML_CONTENT, part2.getText());
	}

	/**
	 * Test convert from simple forward message.
	 * 
	 * @throws Exception the exception
	 */
	public void testConvertFromSimpleForwardMessage() throws Exception {
		Message message = createSimpleForwardMessage();
		MailMessage mailMessage = getMessageConverter().fromMessage(message);

		Assert.assertNotNull("MailMessage must not be null", mailMessage);
		Assert.assertTrue("Expected MailMessage contentType is: multipart/mixed", 
				mailMessage.isMimeType("multipart/mixed"));
		Assert.assertEquals("Expected MailMessage parts size is: 1", 1, mailMessage.getParts().size());
		
		MailPart multiPart = mailMessage.getParts().get(0);
		Assert.assertNotNull("MultiPart object must not be null", multiPart);
		
		MailPart part1 = multiPart.getParts().get(0);
		Assert.assertNotNull("Part1 object must not be null", part1);
		Assert.assertTrue("Expected contentType of part1 object is: text/plain", part1.isMimeType("text/plain"));
		Assert.assertEquals("Expected content of part1 object is: " + TEXT_PLAIN_CONTENT, TEXT_PLAIN_CONTENT, 
				part1.getText());
		
		MailPart part2 = multiPart.getParts().get(1);
		Assert.assertNotNull("Part2 object must not be null", part2);
		Assert.assertTrue("Expected contentType of part2 object is: message/rfc822", 
				part2.isMimeType("message/rfc822"));
		
		MailPart part2_1 = part2.getParts().get(0);
		Assert.assertNotNull("Part2_1 object must not be null", part2_1);
		Assert.assertTrue("Expected type of part2_1 is MailMessage", part2_1 instanceof MailMessage);
		MailMessage forward = (MailMessage) part2_1;
		Assert.assertNotNull("Forward subject must not be null", forward.getSubject());
		Assert.assertEquals("Expected forward subject: " + SUBJECT, SUBJECT, forward.getSubject());
		Assert.assertTrue("Expected contentType of the part2_1 object is: text/html", 
				part2_1.isMimeType("text/html"));
		Assert.assertEquals("Expected content of part2_1 object is: " + TEXT_HTML_CONTENT, TEXT_HTML_CONTENT, 
				part2_1.getText());
	}
	
	/**
	 * Test convert from message with image attachment.
	 * 
	 * @throws Exception the exception
	 */
	public void testConvertFromMessageWithImageAttachment() throws Exception {
		Message message = createMessageWithImageAttachment();
		MailMessage mailMessage = getMessageConverter().fromMessage(message);
		
		Assert.assertNotNull("MailMessage must not be null", mailMessage);
		Assert.assertTrue("MailMessage contentType should be multipart/mixed", 
				mailMessage.isMimeType("multipart/mixed"));
		
		MailPart multiPart = mailMessage.getParts().get(0);
		Assert.assertNotNull("MultiPart must not be null", multiPart);
		Assert.assertEquals("MultiPart size should be: 2", 2, multiPart.getParts().size());
		
		MailPart part1 = multiPart.getParts().get(0);
		Assert.assertNotNull("Part1 must not be null", part1);
		Assert.assertTrue("Part1 contentType should be: text/plain", part1.isMimeType("text/plain"));
		Assert.assertEquals("Part1 content should be: " + TEXT_PLAIN_CONTENT, 
				TEXT_PLAIN_CONTENT, part1.getText());
		
		MailPart part2 = multiPart.getParts().get(1);
		Assert.assertNotNull("Part2 must not be null", part2);
		Assert.assertTrue("Part2 contentType should be: image/jpeg", part2.isMimeType("image/jpeg"));
		Assert.assertEquals("Part2 filename should be: image.jpeg", IMAGE_FILE, part2.getFileName());
		Assert.assertEquals("Part2 content.getData().length == getImageBytes().length", 
				getImageBytes().length, part2.getData().length);
	}
	
	/**
	 * Test convert to plain text message.
	 * 
	 * @throws Exception the exception
	 */
	public void testConvertToPlainTextMessage() throws Exception {
		MailMessage mailMessage = createTextPlainMailMessage();
		MailSession mailSession = createMailSession();
		Message message = getMessageConverter().toMessage(mailMessage, mailSession.getSession());
		
		Assert.assertNotNull("Message must not be null", message);
		Assert.assertTrue("Message contentType should be: text/plain", message.isMimeType("text/plain"));
		Assert.assertTrue("Message content should a string", message.getContent() instanceof String);
		Assert.assertEquals("Message content should be: " + TEXT_PLAIN_CONTENT, 
				TEXT_PLAIN_CONTENT, (String) message.getContent());
		Assert.assertNotNull("Message subject must not be null", message.getSubject());
		Assert.assertEquals("Message subject should be: " + SUBJECT, SUBJECT, message.getSubject());
	}
	
	/**
	 * Test convert to multipart message.
	 * 
	 * @throws Exception the exception
	 */
	public void testConvertToMultipartMessage() throws Exception {
		MailMessage mailMessage = createMultipartMailMessage();
		MailSession mailSession = createMailSession();
		Message message = getMessageConverter().toMessage(mailMessage, mailSession.getSession());
		
		Assert.assertNotNull("Message must not be null", message);
		Assert.assertTrue("Message contentType should be: " + MULTIPART_MIXED_TYPE, 
				message.isMimeType(MULTIPART_MIXED_TYPE));
		Assert.assertTrue("Message content should be Multipart", message.getContent() instanceof Multipart);
		
		Multipart multipart = (Multipart) message.getContent();
		Assert.assertNotNull("Multipart must not be null", multipart);
		Assert.assertEquals("Expected Multipart size: 2", 2, multipart.getCount());
		
		BodyPart part1 = multipart.getBodyPart(0);
		Assert.assertNotNull("Part1 must not be null", part1);
		Assert.assertTrue("Part1 contentType should be: " + TEXT_PLAIN_TYPE, 
				part1.isMimeType(TEXT_PLAIN_TYPE));
		Assert.assertEquals("Part1 content should be:" + TEXT_PLAIN_CONTENT, 
				TEXT_PLAIN_CONTENT, (String) part1.getContent());
		
		BodyPart part2 = multipart.getBodyPart(1);
		Assert.assertNotNull("Part2 must not be null", part1);
		Assert.assertTrue("Part2 contentType should be: " + TEXT_HTML_TYPE, 
				part2.isMimeType(TEXT_HTML_TYPE));
		Assert.assertEquals("Part2 content should be:" + TEXT_HTML_CONTENT, 
				TEXT_HTML_CONTENT, (String) part2.getContent());
	}
	
	/**
	 * Test convert to simple forward message.
	 * 
	 * @throws Exception the exception
	 */
	public void testConvertToSimpleForwardMessage() throws Exception {
		MailMessage mailMessage = createSimpleForwardMailMessage();
		MailSession mailSession = createMailSession();
		Message message = getMessageConverter().toMessage(mailMessage, mailSession.getSession());
		
		Assert.assertNotNull("Message must not be null", message);
		Assert.assertTrue("Message contentType should be: " + MULTIPART_MIXED_TYPE, 
				message.isMimeType(MULTIPART_MIXED_TYPE));
		Assert.assertTrue("Message content should be Multipart", message.getContent() instanceof Multipart);
		
		Multipart multipart = (Multipart) message.getContent();
		Assert.assertNotNull("Multipart must not be null", multipart);
		Assert.assertEquals("Expected Multipart size: 2", 2, multipart.getCount());
		
		BodyPart part1 = multipart.getBodyPart(0);
		Assert.assertNotNull("Part1 must not be null", part1);
		Assert.assertTrue("Part1 contentType should be: " + TEXT_PLAIN_TYPE, 
				part1.isMimeType(TEXT_PLAIN_TYPE));
		Assert.assertEquals("Part1 content should be:" + TEXT_PLAIN_CONTENT, 
				TEXT_PLAIN_CONTENT, (String) part1.getContent());
		
		BodyPart part2 = multipart.getBodyPart(1);
		Assert.assertNotNull("Part2 must not be null", part1);
		Assert.assertTrue("Part2 contentType should be: " + MESSAGE_RFC822_TYPE, 
				part2.isMimeType(MESSAGE_RFC822_TYPE));	
		Assert.assertTrue("Part2 content should be of type javax.mail.Message", 
				part2.getContent() instanceof Message);
		Message forward = (Message) part2.getContent();
		Assert.assertNotNull("Forward must not be null", forward);
		Assert.assertTrue("Forward contentType should be: " + TEXT_HTML_TYPE, 
				forward.isMimeType(TEXT_HTML_TYPE));
		Assert.assertEquals("Forward content should be: " + TEXT_HTML_CONTENT, 
				TEXT_HTML_CONTENT, (String) forward.getContent());
	}
	
	/**
	 * Test convert to message with image attachment.
	 * 
	 * @throws Exception the exception
	 */
	public void testConvertToMessageWithImageAttachment() throws Exception {
		MailMessage mailMessage = createMailMessageWithImageAttachment();
		MailSession mailSession = createMailSession();
		Message message = getMessageConverter().toMessage(mailMessage, mailSession.getSession());
		
		Assert.assertNotNull("Message must not be null", message);
		Assert.assertTrue("Message contentType should be: " + MULTIPART_MIXED_TYPE, 
				message.isMimeType(MULTIPART_MIXED_TYPE));
		Assert.assertTrue("Message content should be Multipart", message.getContent() instanceof Multipart);
		
		Multipart multipart = (Multipart) message.getContent();
		Assert.assertNotNull("Multipart must not be null", multipart);
		Assert.assertEquals("Expected Multipart size: 2", 2, multipart.getCount());
		
		BodyPart part1 = multipart.getBodyPart(0);
		Assert.assertNotNull("Part1 must not be null", part1);
		Assert.assertTrue("Part1 contentType should be: " + TEXT_PLAIN_TYPE, 
				part1.isMimeType(TEXT_PLAIN_TYPE));
		Assert.assertEquals("Part1 content should be:" + TEXT_PLAIN_CONTENT, 
				TEXT_PLAIN_CONTENT, (String) part1.getContent());
		
		BodyPart part2 = multipart.getBodyPart(1);
		Assert.assertNotNull("Part2 must not be null", part1);
		Assert.assertTrue("Part2 contentType should be: " + IMAGE_TYPE, 
				part2.isMimeType(IMAGE_TYPE));	
		Assert.assertEquals("Part2 bytes length == getImageBytes().length", 
				getImageBytes().length, IOUtils.getBytes(part2.getInputStream()).length);
	}	
}
