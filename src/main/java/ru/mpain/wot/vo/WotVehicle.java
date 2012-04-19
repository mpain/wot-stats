package ru.mpain.wot.vo;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * User: sergey
 * Date: 4/19/12
 * Time: 11:35 AM
 */
public class WotVehicle {
	@JsonProperty("localized_name")
	private String localizedName;

	@JsonProperty("name")
	private String name;

	@JsonProperty("image_url")
	private String imageUrl;

	@JsonProperty("level")
	private int level;

	@JsonProperty("nation")
	private String nation;

	@JsonProperty("class")
	private String vehicleClass;

	@JsonProperty("battle_count")
	private int battles;

	@JsonProperty("win_count")
	private int battleWins;

	public String getLocalizedName() {
		return localizedName;
	}

	public void setLocalizedName(String localizedName) {
		this.localizedName = localizedName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getVehicleClass() {
		return vehicleClass;
	}

	public void setVehicleClass(String vehicleClass) {
		this.vehicleClass = vehicleClass;
	}

	public int getBattles() {
		return battles;
	}

	public void setBattles(int battles) {
		this.battles = battles;
	}

	public int getBattleWins() {
		return battleWins;
	}

	public void setBattleWins(int battleWins) {
		this.battleWins = battleWins;
	}
}
