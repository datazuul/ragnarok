package com.myslek.webmail.api;

import com.myslek.webmail.impl.DefaultAttributesHandler;
import com.myslek.webmail.impl.DefaultEnvelopeHandler;

public abstract class AbstractContentHandler implements ContentHandler {

	private AttributesHandler attributesHandler;
	private EnvelopeHandler envelopeHandler;

	public AbstractContentHandler() {
		attributesHandler = new DefaultAttributesHandler();
		envelopeHandler = new DefaultEnvelopeHandler();
	}

	public AbstractContentHandler(AttributesHandler attributesHandler) {
		this.attributesHandler = attributesHandler;
	}

	public AttributesHandler getAttributesHandler() {
		return attributesHandler;
	}

	public void setAttributesHandler(AttributesHandler attributesHandler) {
		this.attributesHandler = attributesHandler;
	}
	
	public EnvelopeHandler getEnvelopeHandler() {
		return envelopeHandler;
	}

	public void setEnvelopeHandler(EnvelopeHandler envelopeHandler) {
		this.envelopeHandler = envelopeHandler;
	}
}