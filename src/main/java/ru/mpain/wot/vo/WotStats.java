package ru.mpain.wot.vo;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;

/**
 * User: sergey
 * Date: 4/19/12
 * Time: 10:53 AM
 */
public class WotStats {
	@JsonProperty("status")
	private String status;

	@JsonProperty("status_code")
	private String statusCode;

	@JsonProperty("error")
	private String error;

	@JsonProperty("error_type")
	private String errorType;

	@JsonProperty("data")
	private WotData data;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public WotData getData() {
		return data;
	}

	public void setData(WotData data) {
		this.data = data;
	}
}
