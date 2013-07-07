package com.imti.framework.component.sertificate.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StreamGobbler extends Thread {
	private static final Log log = LogFactory.getLog(StreamGobbler.class);
	/**
		 * 
		 */
	InputStream is;
	String type;
	OutputStream os;

	public StreamGobbler(InputStream is, String type) {
		this.is = is;
		this.type = type;
	}

	public StreamGobbler(InputStream is, String type, OutputStream redirect) {
		this.is = is;
		this.type = type;
		this.os = redirect;
	}

	public void run() {
		try {
			PrintWriter pw = null;
			if (os != null)
				pw = new PrintWriter(os);

			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				if (pw != null)
					pw.println(line);
				log.info(type + ">" + line);
				if (pw != null)
					pw.flush();
			}

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}