package com.appthon.common;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Component;

@Component("commonUtils")
public class UtilityClass {
	
	public String connectToURL(final String URL) throws IOException {
		final char[] buffer = new char[10000];
		StringBuilder responseFromServer = new StringBuilder();
		URL url = new URL(URL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		final Reader in = new InputStreamReader(conn.getInputStream(),"UTF-8");
		try {
			for (;;) {
				int rsz = in.read(buffer, 0, buffer.length);
				if (rsz < 0) {
					break;
				}
				responseFromServer.append(buffer, 0, rsz);
			}
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		} finally {
			in.close();
		} 
		return responseFromServer.toString();
	}

}
