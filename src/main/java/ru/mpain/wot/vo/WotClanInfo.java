package ru.mpain.wot.vo;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * User: sergey
 * Date: 4/19/12
 * Time: 12:16 PM
 */
public class WotClanInfo {
	@JsonProperty("id")
	private long id;

	@JsonProperty("name")
	private String name;

	@JsonProperty("abbreviation")
	private String abbreviation;

	@JsonProperty("color")
	private String color;

	@JsonProperty("emblems_urls")
	private WotClanEmblems emblems;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public WotClanEmblems getEmblems() {
		return emblems;
	}

	public void setEmblems(WotClanEmblems emblems) {
		this.emblems = emblems;
	}
}
