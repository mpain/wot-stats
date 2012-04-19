package ru.mpain.wot.vo;

import java.util.Date;

/**
 * User: sergey
 * Date: 4/19/12
 * Time: 12:13 PM
 */
public class WotClanMember {
	private double since;

	private String role;

	public double getSince() {
		return since;
	}

	public void setSince(double since) {
		this.since = since;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Date getSinceDate() {
		return new Date((long)getSince() * 1000);
	}
}
