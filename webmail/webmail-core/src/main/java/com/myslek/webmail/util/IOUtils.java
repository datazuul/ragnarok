package com.myslek.webmail.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

// TODO: Auto-generated Javadoc
/**
 * The Class IOUtils.
 */
public final class IOUtils {
	
	/** The Constant DEFAULT_BUFFER_SIZE. */
	public static final int DEFAULT_BUFFER_SIZE = 4 * 1024;
	
	/**
	 * Instantiates a new iO utils.
	 */
	private IOUtils() {
		
	}
	
	/**
	 * Gets the bytes.
	 * 
	 * @param in the in
	 * 
	 * @return the bytes
	 * 
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static byte[] getBytes(InputStream in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
		int len;
		while ((len = in.read(buffer)) != -1) {
			out.write(buffer, 0, len);
		}
		
		return out.toByteArray();
	}
}
