package ru.mpain.wot.vo;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * User: sergey
 * Date: 4/19/12
 * Time: 11:11 AM
 */
public class WotRatings {
	// GR
	@JsonProperty("integrated_rating")
	private WotRating rating;

	// SPT
	@JsonProperty("spotted")
	private WotRating spotted;

	// CPT
	@JsonProperty("ctf_points")
	private WotRating captureTheFlagPoints;

	// DPT
	@JsonProperty("dropped_ctf_points")
	private WotRating droppedCaptureTheFlagPoints;

	// E/B
	@JsonProperty("battle_avg_xp")
	private WotRating averageExperiencePoints;

	// EXP
	@JsonProperty("xp")
	private WotRating experiencePoints;

	// DMG
	@JsonProperty("damage_dealt")
	private WotRating damage;

	// FRG
	@JsonProperty("frags")
	private WotRating frags;

	// GPL
	@JsonProperty("battles")
	private WotRating battles;

	// WIN
	@JsonProperty("battle_wins")
	private WotRating battleWins;

	// W/B
	@JsonProperty("battle_avg_performance")
	private WotRating averageBattleEfficiency;

	public WotRating getRating() {
		return rating;
	}

	public void setRating(WotRating rating) {
		this.rating = rating;
	}

	public WotRating getSpotted() {
		return spotted;
	}

	public void setSpotted(WotRating spotted) {
		this.spotted = spotted;
	}

	public WotRating getCaptureTheFlagPoints() {
		return captureTheFlagPoints;
	}

	public void setCaptureTheFlagPoints(WotRating captureTheFlagPoints) {
		this.captureTheFlagPoints = captureTheFlagPoints;
	}

	public WotRating getDroppedCaptureTheFlagPoints() {
		return droppedCaptureTheFlagPoints;
	}

	public void setDroppedCaptureTheFlagPoints(WotRating droppedCaptureTheFlagPoints) {
		this.droppedCaptureTheFlagPoints = droppedCaptureTheFlagPoints;
	}

	public WotRating getAverageExperiencePoints() {
		return averageExperiencePoints;
	}

	public void setAverageExperiencePoints(WotRating averageExperiencePoints) {
		this.averageExperiencePoints = averageExperiencePoints;
	}

	public WotRating getExperiencePoints() {
		return experiencePoints;
	}

	public void setExperiencePoints(WotRating experiencePoints) {
		this.experiencePoints = experiencePoints;
	}

	public WotRating getDamage() {
		return damage;
	}

	public void setDamage(WotRating damage) {
		this.damage = damage;
	}

	public WotRating getFrags() {
		return frags;
	}

	public void setFrags(WotRating frags) {
		this.frags = frags;
	}

	public WotRating getBattles() {
		return battles;
	}

	public void setBattles(WotRating battles) {
		this.battles = battles;
	}

	public WotRating getBattleWins() {
		return battleWins;
	}

	public void setBattleWins(WotRating battleWins) {
		this.battleWins = battleWins;
	}

	public WotRating getAverageBattleEfficiency() {
		return averageBattleEfficiency;
	}

	public void setAverageBattleEfficiency(WotRating averageBattleEfficiency) {
		this.averageBattleEfficiency = averageBattleEfficiency;
	}
}
