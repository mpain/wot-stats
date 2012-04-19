package ru.mpain.wot;


import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mpain.wot.common.JsonHelper;
import ru.mpain.wot.common.Roman;
import ru.mpain.wot.http.HttpExchange;
import ru.mpain.wot.http.HttpExchangeException;
import ru.mpain.wot.http.HttpExchangeInfo;
import ru.mpain.wot.http.IHttpExchange;
import ru.mpain.wot.vo.WotStats;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WotApiTest {
	private Logger log = LoggerFactory.getLogger(WotApiTest.class);

	private static final Pattern PATTERN = Pattern.compile("\"(/community/accounts/(\\d+)-([a-zA-z0-9]+)/)\"");
	public static final String PROJECT_URL = "http://worldoftanks.ru";

	@Test
	public void simpleApiTest() throws HttpExchangeException {
		final String nick = "mpain1978";

		IHttpExchange http = new HttpExchange();

		try {
			Map<String, String> headers = Maps.newHashMap();
			headers.put("Accept", "application/json, text/javascript");
			headers.put("Accept-Charset", "utf-8");
			headers.put("Pragma", "no-cache");
			headers.put("Connection", "close");
			headers.put("Referer", "http://worldoftanks.ru/community/accounts/");
			headers.put("X-Requested-With", "XMLHttpRequest");

			http.executeGet(constructUrl(nick), false, headers);
			HttpExchangeInfo info = http.getInfo();

			log.debug("CODE: {}, RESPONSE: {}", info.getCode(), info.getResponse());

			if (info.getCode() == HttpServletResponse.SC_OK) {
				Matcher matcher = PATTERN.matcher(info.getResponse());
				while (matcher.find()) {
					log.debug("URL: {}, ID: {}, NICK {}", new Object[] {matcher.group(1), matcher.group(2), matcher.group(3)});
					loadAchievements(matcher.group(2));
					//loadUserStats(matcher.group(1));
				}
			}

		} finally {
			http.close();
		}

	}


	private void loadUserStats(String url) throws HttpExchangeException {
		IHttpExchange http = new HttpExchange();
		try {
			Map<String, String> headers = Maps.newHashMap();
			headers.put("Accept", "text/html");
			headers.put("Accept-Charset", "utf-8");
			headers.put("Pragma", "no-cache");
			headers.put("Connection", "close");
			headers.put("Referer", "http://worldoftanks.ru/community/accounts/");
			HttpExchangeInfo info = http.getInfo();

			http.executeGet(PROJECT_URL + url, false, headers);
			//log.debug("CODE: {}, RESPONSE: {}", info.getCode(), info.getResponse());

			if (info.getCode() == HttpServletResponse.SC_OK) {
				parse(info.getResponse());
			}
		} finally {
			http.close();
		}
	}

	private void loadAchievements(String id) throws HttpExchangeException {
		IHttpExchange http = new HttpExchange();
		try {
			Map<String, String> headers = Maps.newHashMap();
			headers.put("Accept", "application/json, text/javascript");
			headers.put("Accept-Charset", "utf-8");
			headers.put("Pragma", "no-cache");
			headers.put("Connection", "close");
			headers.put("Referer", "http://worldoftanks.ru/uc/accounts/");
			headers.put("X-Requested-With", "XMLHttpRequest");
			headers.put("If-Modified-Since", "Sat, 1 Jan 2000 00:00:00 GMT");

			// http://"+document.location.host+"/uc/accounts/%s/api/1.3/?source_token=Intellect_Soft-WoT_Mobile-site
			String targetUrl = String.format(PROJECT_URL + "/uc/accounts/%s/api/1.3/?source_token=Intellect_Soft-WoT_Mobile-site", id);
			http.executeGet(targetUrl, false, headers);

			HttpExchangeInfo info = http.getInfo();

			log.debug("CODE: {}, RESPONSE: {}", info.getCode(), info.getResponse());

			if (info.getCode() == HttpServletResponse.SC_OK) {
				try {
					WotStats stats = JsonHelper.fromJson(WotStats.class, info.getResponse());
					if (!Strings.isNullOrEmpty(stats.getStatus()) && stats.getStatus().equalsIgnoreCase("ok")) {
						log.debug("Stats: {}, Created at: {}, Updated at: {}", new Object[] {stats, stats.getData().getCreatedDate(), stats.getData().getUpdatedDate()});
					}
				} catch (Exception e) {
					log.error("Error", e);
					throw new HttpExchangeException(e);
				}
			}
		} finally {
			http.close();
		}
	}

	private void parse(String source) {
		Document parsed = Jsoup.parse(source);

		Element dateElement = parsed.select("div.b-data-date").get(0);
		String timestamp = dateElement.select(".js-datetime-format").get(0).attr("data-timestamp");

		log.debug("Timestamp: {}", timestamp);
		Element statTable = dateElement.nextElementSibling();

		Elements elements = parsed.select("table.t-statistic");
		for (int index = 0; index < elements.size(); index++) {
			Element element = elements.get(index);
			if (index == 0) {
				Elements rows = element.select("tr:gt(0)");
				for (Element row : rows) {
					Elements columns = row.select("td");
					List<String> array = Lists.newArrayList();

					for (Element column : columns) {
						array.add(column.text());
					}

					String value = removeSpaces(array.get(3));
					String place = removeSpaces(array.get(4));
					log.debug("{} {} {}", new Object[] {array.get(0), value, place});
				}
			} else {
				Elements rows = element.select("tr:gt(0)");
				for (Element row : rows) {
					Elements columns = row.select("td");
					List<String> array = Lists.newArrayList();

					for (int columnIndex = 0; columnIndex < columns.size(); columnIndex++) {
						Element column = columns.get(columnIndex);

						if (columnIndex == 0) {
							array.add(resolveNation(column));
						}
						array.add(column.text());
					}

					String value = removeSpaces(array.get(3));
					String place = removeSpaces(array.get(4));

					log.debug("{} {} {} {} {}", new Object[] {array.get(0), Roman.roman2Int(array.get(1)), array.get(2), value, place});

				}
			}

		}
	}

	private String resolveNation(Element column) {
		final String PREFIX = "js-";

		String result = "";

		String classAttribute= column.attr("class");
		if (!Strings.isNullOrEmpty(classAttribute)) {
			String[] classes = classAttribute.trim().split("\\s+");
			for (String clazz : classes) {
				if (clazz.startsWith(PREFIX)) {
					result = clazz.substring(PREFIX.length());
					break;
				}
			}
		}

		return result;
	}
	private String removeSpaces(String source) {
		if (Strings.isNullOrEmpty(source)) {
			return source;
		}

		return source.replaceAll("[\\s\\u00A0]+", "");
	}

	private String constructUrl(String nick) {
		final String TEMPLATE = "%s/community/accounts/?type=table&offset=0&limit=25&order_by=name&search=%s&echo=2&id=accounts_index";
		return String.format(TEMPLATE, PROJECT_URL, nick);
	}
}
