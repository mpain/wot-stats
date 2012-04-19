package ru.mpain.wot.vo;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * User: sergey
 * Date: 4/19/12
 * Time: 11:31 AM
 */
public class WotBattles {
	@JsonProperty("spotted")
	private int spotted;

	@JsonProperty("hits_percents")
	private int hitsPercents;

	@JsonProperty("capture_points")
	private int capturePoints;

	@JsonProperty("dropped_capture_points")
	private int droppedCapturePoints;

	@JsonProperty("damage_dealt")
	private int damageSummary;

	@JsonProperty("frags")
	private int frags;

	public int getSpotted() {
		return spotted;
	}

	public void setSpotted(int spotted) {
		this.spotted = spotted;
	}

	public int getHitsPercents() {
		return hitsPercents;
	}

	public void setHitsPercents(int hitsPercents) {
		this.hitsPercents = hitsPercents;
	}

	public int getCapturePoints() {
		return capturePoints;
	}

	public void setCapturePoints(int capturePoints) {
		this.capturePoints = capturePoints;
	}

	public int getDroppedCapturePoints() {
		return droppedCapturePoints;
	}

	public void setDroppedCapturePoints(int droppedCapturePoints) {
		this.droppedCapturePoints = droppedCapturePoints;
	}

	public int getDamageSummary() {
		return damageSummary;
	}

	public void setDamageSummary(int damageSummary) {
		this.damageSummary = damageSummary;
	}

	public int getFrags() {
		return frags;
	}

	public void setFrags(int frags) {
		this.frags = frags;
	}
}
