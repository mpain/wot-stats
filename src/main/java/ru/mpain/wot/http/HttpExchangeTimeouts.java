package ru.mpain.wot.http;

import java.io.Serializable;

public class HttpExchangeTimeouts implements Serializable {
	private int timeout = 30000;
	private int connectionTimeout = 10000;
	private boolean redirects = true;

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public boolean isRedirects() {
		return redirects;
	}

	public void setRedirects(boolean redirects) {
		this.redirects = redirects;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + connectionTimeout;
		result = prime * result + (redirects ? 1231 : 1237);
		result = prime * result + timeout;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HttpExchangeTimeouts other = (HttpExchangeTimeouts) obj;
		if (connectionTimeout != other.connectionTimeout)
			return false;
		if (redirects != other.redirects)
			return false;
		if (timeout != other.timeout)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("HttpExchangeTimeouts [timeout=");
		builder.append(timeout);
		builder.append(", connectionTimeout=");
		builder.append(connectionTimeout);
		builder.append(", redirects=");
		builder.append(redirects);
		builder.append("]");
		return builder.toString();
	}
}
