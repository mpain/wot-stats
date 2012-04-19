package ru.mpain.wot.http;

import com.google.common.base.Strings;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class HttpExchange implements IHttpExchange {
	private static final String CHARSET_PREFIX = "charset=";
	private static final String CONTEXT_TYPE_SSL = "SSL";
	private static final String PROTO_TYPE_HTTPS = "https";
	private static final String PROTO_TYPE_HTTP = "http";

	private static final String DEFAULT_ENCODING = "utf-8";

	private Logger log = LoggerFactory.getLogger(this.getClass());

	private static Map<HttpExchangeTimeouts, HttpClient> cache = Collections.synchronizedMap(new HashMap<HttpExchangeTimeouts, HttpClient>());

	private ThreadLocal<HttpExchangeInfo> info = new ThreadLocal<HttpExchangeInfo>();
	private ThreadLocal<HttpExchangeTimeouts> timeouts = new ThreadLocal<HttpExchangeTimeouts>();

	@Override
	public void executeGet(String address, String encoding) throws HttpExchangeException {
		getData().setEncoding(encoding);
		executeGet(address, true);
	}

	@Override
	public void executeGet(String address) throws HttpExchangeException {
		executeGet(address, true);
	}

	@Override
	public void executePost(String address, String xmlData) throws HttpExchangeException {
		executePost(address, xmlData, true, null);
	}

	@Override
	public void executePost(String address, String xmlData, Map<String, String> headers) throws HttpExchangeException {
		executePost(address, xmlData, true, headers);
	}

	@Override
	public void executeGet(String address, boolean enableRedirects) throws HttpExchangeException {
		try {
			executeGetRequest(address, enableRedirects, null);
		} catch (IOException e) {
			throw new HttpExchangeException(e);
		}
	}


	@Override
	public void executeGet(String address, boolean enableRedirects, Map<String, String> headers) throws HttpExchangeException {
		try {
			executeGetRequest(address, enableRedirects, headers);
		} catch (IOException e) {
			throw new HttpExchangeException(e);
		}
	}

	@Override
	public void executePost(String address, String xmlData, boolean enableRedirects, Map<String, String> headers) throws HttpExchangeException {
		try {
			executePostRequest(address, xmlData, enableRedirects, headers);
		} catch (IOException e) {
			throw new HttpExchangeException(e);
		}
	}

	private void executeGetRequest(String urlString, boolean enableRedirects, Map<String, String> headers) throws IOException {
		HttpGet method = new HttpGet(urlString);
		setupHeaders(method, headers);

		getData().setRequest(method.getRequestLine().toString());
		executeRequest(method, enableRedirects);
	}

	private void executePostRequest(String urlString, String data, boolean enableRedirects, Map<String, String> headers) throws IOException {
		HttpPost method = new HttpPost(urlString);

		if (headers == null) {
			method.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
		}

		setupHeaders(method, headers);

		StringEntity entity = new StringEntity(data, DEFAULT_ENCODING);
		method.setEntity(entity);

		getData().setRequest(String.format("%s\r\n\r\n%s", method.getRequestLine().toString(), data));
		executeRequest(method, enableRedirects);
	}

	private void setupHeaders(HttpRequestBase method, Map<String, String> headers) {
		if (headers == null) {
			return;
		}

		for (String key : headers.keySet()) {
			method.setHeader(key, headers.get(key));
		}
	}

	private void executeRequest(HttpRequestBase method, boolean enableRedirects) throws IOException {
		HttpClient http = getClient(enableRedirects);
		HttpExchangeInfo data = getData();

		log.debug(data.getRequest());

		HttpResponse httpResponse = http.execute(method);

		if (data.getEncoding() == null) {
			data.setEncoding(getEncoding(httpResponse.getEntity()));
		}

		data.setCode(httpResponse.getStatusLine().getStatusCode());
		data.setResponse(httpReadResponse(httpResponse.getEntity()));

		if (data.getCode() == HttpServletResponse.SC_MOVED_PERMANENTLY ||
				data.getCode() == HttpServletResponse.SC_MOVED_TEMPORARILY) {

			Header header = httpResponse.getFirstHeader("Location");
			if (header != null) {
				data.setLocation(header.getValue());
			}
		}

		if (log.isDebugEnabled()) {
			String response = data.getResponse();
			log.debug("RESPONSE: " + ((response != null && response.length() > 1024) ? response.substring(0, 1024) + "\n...\n" : response));
		}
	}

	private String getEncoding(HttpEntity entity) {
		Header encodingHeader = entity.getContentEncoding();
		log.debug("Encoding header: {}", encodingHeader);

		String encoding = (encodingHeader != null && !Strings.isNullOrEmpty(encodingHeader.getValue())) ? encodingHeader.getValue() : null;
		log.debug("Encoding from Content-Encoding: {}", encoding);

		if (encoding == null) {
			encodingHeader = entity.getContentType();
			log.debug("Encoding header: {}", encodingHeader);

			if (encodingHeader != null && !Strings.isNullOrEmpty(encodingHeader.getValue())) {
				String[] pairs = encodingHeader.getValue().split(";");
				if (pairs != null) {
					for (String pair : pairs) {
						if (!Strings.isNullOrEmpty(pair)) {
							String data = pair.trim();
							if (data.startsWith(CHARSET_PREFIX)) {
								encoding = data.substring(CHARSET_PREFIX.length());
								break;
							}
						}
					}
				}
			}

			if (encoding == null) {
				encoding = DEFAULT_ENCODING;
			}
			log.debug("Encoding from Content-Type: {}", encoding);
		}

		return encoding;
	}

	private String httpReadResponse(HttpEntity resp) throws IOException {
		if (resp == null) {
			return null;
		}


		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(resp.getContent(), getData().getEncoding()));

			StringBuilder sb = new StringBuilder();
			for (String line; (line = br.readLine()) != null; ) {
				sb.append(line);
				sb.append("\r\n");
			}
			return sb.toString();

		} finally {
			if (br != null) {
				br.close();
			}
		}
	}

	public void close() {
		info.remove();
		timeouts.remove();
	}

	public HttpClient getClient(boolean enableRedirects) {
		HttpExchangeTimeouts timeouts = getTimeouts();
		timeouts.setRedirects(enableRedirects);

		HttpClient currentClient = getCachedClient(timeouts);

		if (currentClient == null) {
			synchronized (this) {
				log.trace("It's performing double check...");
				currentClient = getCachedClient(timeouts);
				if (currentClient == null) {
					currentClient = createHttpClient(timeouts);

					cache.put(timeouts, currentClient);
				}
			}
		}

		return currentClient;
	}

	private HttpClient getCachedClient(HttpExchangeTimeouts timeouts) {
		HttpClient client = cache.get(timeouts);

		log.trace("Timeouts: {}; Http client {}!!!", timeouts, client != null ? "found" : "isn't found");
		return client;
	}

	private static HttpClient createHttpClient(HttpExchangeTimeouts timeouts) {
		HttpClient currentClient;
		HttpParams params = new BasicHttpParams();

		HttpClientParams.setRedirecting(params, timeouts.isRedirects());
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);

		HttpConnectionParams.setConnectionTimeout(params, timeouts.getTimeout());
		
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme(PROTO_TYPE_HTTP, 80, PlainSocketFactory.getSocketFactory()));

		HttpConnectionParams.setSoTimeout(params, timeouts.getTimeout());
		HttpConnectionParams.setConnectionTimeout(params, timeouts.getConnectionTimeout());

		try {
			SSLContext context = SSLContext.getInstance(CONTEXT_TYPE_SSL);
			context.init(null, new TrustManager[]{new X509TrustManager() {

				@Override
				public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
				}

				@Override
				public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
				}

				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

			}}, null);
			SSLSocketFactory factory = new SSLSocketFactory(context, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			schemeRegistry.register(new Scheme(PROTO_TYPE_HTTPS, 443, factory));
		} catch (NoSuchAlgorithmException ignored) {
		} catch (KeyManagementException ignored) {
		}

		PoolingClientConnectionManager cm = new PoolingClientConnectionManager(schemeRegistry, 120, TimeUnit.SECONDS);
		cm.setDefaultMaxPerRoute(50);
		cm.setMaxTotal(200);

		currentClient = new DefaultHttpClient(cm, params);
		return currentClient;
	}

	public HttpExchangeInfo getInfo() {
		return getInfoImpl(false);
	}

	private HttpExchangeInfo getData() {
		return getInfoImpl(true);
	}

	private HttpExchangeInfo getInfoImpl(boolean needExistingResult) {
		HttpExchangeInfo result = info.get();
		if (result == null && needExistingResult) {
			info.set(new HttpExchangeInfo());
			result = info.get();
		}

		return result;
	}

	public HttpExchangeTimeouts getTimeouts() {
		HttpExchangeTimeouts result = timeouts.get();
		if (result == null) {
			timeouts.set(new HttpExchangeTimeouts());
			result = timeouts.get();
		}

		return result;
	}
}
