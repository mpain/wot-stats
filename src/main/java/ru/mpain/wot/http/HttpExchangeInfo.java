package ru.mpain.wot.http;

import javax.servlet.http.HttpServletResponse;

public class HttpExchangeInfo {
	private int code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

	private String request;

	private String response;

	private String location;

	private String encoding;
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
}
