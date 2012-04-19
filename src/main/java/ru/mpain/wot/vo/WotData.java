package ru.mpain.wot.vo;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;
import java.util.List;

/**
 * User: sergey
 * Date: 4/19/12
 * Time: 11:09 AM
 */
public class WotData {
	@JsonProperty("name")
	private String name;

	@JsonProperty("created_at")
	private double created;

	@JsonProperty("updated_at")
	private double updated;

	@JsonProperty("battles")
	private WotBattles battles;

	@JsonProperty("summary")
	private WotSummary summary;

	@JsonProperty("experience")
	private WotExperience experience;

	@JsonProperty("clan")
	private WotClan clan;

	@JsonProperty("achievements")
	private WotAchievements achievements;

	@JsonProperty("ratings")
	private WotRatings ratings;

	@JsonProperty("vehicles")
	private List<WotVehicle> vehicles;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getCreated() {
		return created;
	}

	public void setCreated(double created) {
		this.created = created;
	}

	public double getUpdated() {
		return updated;
	}

	public void setUpdated(double updated) {
		this.updated = updated;
	}

	public WotBattles getBattles() {
		return battles;
	}

	public void setBattles(WotBattles battles) {
		this.battles = battles;
	}

	public WotSummary getSummary() {
		return summary;
	}

	public void setSummary(WotSummary summary) {
		this.summary = summary;
	}

	public WotExperience getExperience() {
		return experience;
	}

	public void setExperience(WotExperience experience) {
		this.experience = experience;
	}

	public WotClan getClan() {
		return clan;
	}

	public void setClan(WotClan clan) {
		this.clan = clan;
	}

	public WotAchievements getAchievements() {
		return achievements;
	}

	public void setAchievements(WotAchievements achievements) {
		this.achievements = achievements;
	}

	public WotRatings getRatings() {
		return ratings;
	}

	public void setRatings(WotRatings ratings) {
		this.ratings = ratings;
	}

	public List<WotVehicle> getVehicles() {
		return vehicles;
	}

	public void setVehicles(List<WotVehicle> vehicles) {
		this.vehicles = vehicles;
	}

	public Date getCreatedDate() {
		return new Date((long)getCreated() * 1000);
	}

	public Date getUpdatedDate() {
		return new Date((long)getUpdated() * 1000);
	}
}
