package ru.mpain.wot.vo;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * User: sergey
 * Date: 4/19/12
 * Time: 11:39 AM
 */
public class WotSummary {
	@JsonProperty("wins")
	private int wins;

	@JsonProperty("losses")
	private int losses;

	@JsonProperty("battles_count")
	private int battles;

	@JsonProperty("survived_battles")
	private int survivals;

	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public int getLosses() {
		return losses;
	}

	public void setLosses(int losses) {
		this.losses = losses;
	}

	public int getBattles() {
		return battles;
	}

	public void setBattles(int battles) {
		this.battles = battles;
	}

	public int getSurvivals() {
		return survivals;
	}

	public void setSurvivals(int survivals) {
		this.survivals = survivals;
	}
}
