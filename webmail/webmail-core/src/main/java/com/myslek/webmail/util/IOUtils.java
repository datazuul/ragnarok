package com.myslek.webmail.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public final class IOUtils {
	
	public static final int DEFAULT_BUFFER_SIZE = 4 * 1024;
	
	private IOUtils() {
		
	}
	
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
