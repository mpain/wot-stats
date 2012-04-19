package ru.mpain.wot.http;

public class HttpExchangeException extends Exception {

	public HttpExchangeException() {
	}

	public HttpExchangeException(String message) {
		super(message);
	}

	public HttpExchangeException(Throwable cause) {
		super(cause);
	}

	public HttpExchangeException(String message, Throwable cause) {
		super(message, cause);
	}

}
