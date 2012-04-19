package ru.mpain.wot.vo;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * User: sergey
 * Date: 4/19/12
 * Time: 11:39 AM
 */
public class WotExperience {
	@JsonProperty("xp")
	private int experience;

	@JsonProperty("battle_avg_xp")
	private int average;

	@JsonProperty("max_xp")
	private int max;

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public int getAverage() {
		return average;
	}

	public void setAverage(int average) {
		this.average = average;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}
}
