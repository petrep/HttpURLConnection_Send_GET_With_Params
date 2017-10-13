package com.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class HttpURLConnection_Get_Params {

	public static void main(String[] args) throws UnsupportedEncodingException,
			IOException {

		// The url has to end with '?' if we want to send parameters
//		String url = "http://example.com:8082/subfolder/subsubfolder/result?";
		String url = "http://posttestserver.com/post.php?";

		// Uses LinkedHashMap instead of HashMap to preserve the order of
		// the inserted parameters
		Map<String, String> parameters = new LinkedHashMap<>();
		parameters.put("param1name", "param1value");
		parameters.put("param2name", "param2value");
		parameters.put("param3name", "param3value");

		// Calls the getParamsString method to convert the above strings
		// to real parameters
		String parametersString = getParamsString(parameters);

		url += parametersString;
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setReadTimeout(5000);
		con.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
		con.addRequestProperty("User-Agent", "Mozilla");
		con.addRequestProperty("Referer", "google.com");
		con.setDoOutput(true);
		con.setConnectTimeout(5000);
		con.setReadTimeout(5000);

		// Sends the http request
		int status = con.getResponseCode();
		String respMsg = con.getResponseMessage();

		// Reads http response
		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		String inputLine;
		StringBuilder content = new StringBuilder();
		while ((inputLine = in.readLine()) != null) {
			content.append(inputLine);
			System.out.println(inputLine);
		}
		in.close();
		// System.out.println("http status is: " + status);
		// System.out.println("content is: " + content);
		// System.out.println("resp msg: " + respMsg);
	}

	public static String getParamsString(Map<String, String> params)
			throws UnsupportedEncodingException {
		StringBuilder result = new StringBuilder();

		for (Map.Entry<String, String> entry : params.entrySet()) {
			result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
			result.append("=");
			result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
			result.append("&");
		}

		String resultString = result.toString();
		System.out.println("sr: " + resultString);
		return resultString.length() > 0 ? resultString.substring(0,
				resultString.length() - 1) : resultString;
	}
}