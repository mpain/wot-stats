package ru.mpain.wot.vo;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * User: sergey
 * Date: 4/19/12
 * Time: 11:44 AM
 */
@JsonIgnoreProperties({"clan_ext"})
public class WotClan {
	@JsonProperty("member")
	private WotClanMember member;

	@JsonProperty("clan")
	private WotClanInfo clan;

	public WotClanMember getMember() {
		return member;
	}

	public void setMember(WotClanMember member) {
		this.member = member;
	}

	public WotClanInfo getClan() {
		return clan;
	}

	public void setClan(WotClanInfo clan) {
		this.clan = clan;
	}
}
