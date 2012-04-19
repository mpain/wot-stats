package ru.mpain.wot.http;

import java.util.Map;

public interface IHttpExchange {
	public void executeGet(String address, String encoding) throws HttpExchangeException;
	public void executeGet(String address) throws HttpExchangeException;
	public void executeGet(String address, boolean enableRedirects) throws HttpExchangeException;
	public void executeGet(String address, boolean enableRedirects, Map<String, String> headers) throws HttpExchangeException;
	
	public void executePost(String address, String xmlData) throws HttpExchangeException;
	public void executePost(String address, String xmlData, Map<String, String> headers) throws HttpExchangeException;
	public void executePost(String address, String xmlData, boolean enableRedirects, Map<String, String> headers) throws HttpExchangeException;
	
	public HttpExchangeInfo getInfo();
	public HttpExchangeTimeouts getTimeouts();
	public void close();
}