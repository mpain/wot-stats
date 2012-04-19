package ru.mpain.wot.vo;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * User: sergey
 * Date: 4/19/12
 * Time: 12:16 PM
 */
public class WotClanEmblems {
	@JsonProperty("small")
	private String small;

	@JsonProperty("large")
	private String large;

	@JsonProperty("bw_tank")
	private String tank;

	@JsonProperty("medium")
	private String medium;

	public String getSmall() {
		return small;
	}

	public void setSmall(String small) {
		this.small = small;
	}

	public String getLarge() {
		return large;
	}

	public void setLarge(String large) {
		this.large = large;
	}

	public String getTank() {
		return tank;
	}

	public void setTank(String tank) {
		this.tank = tank;
	}

	public String getMedium() {
		return medium;
	}

	public void setMedium(String medium) {
		this.medium = medium;
	}
}
