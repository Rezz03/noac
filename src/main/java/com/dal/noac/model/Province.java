package com.dal.noac.model;

// Generated Oct 26, 2014 5:44:33 PM by Hibernate Tools 4.3.1

import java.util.HashSet;
import java.util.Set;

/**
 * Province generated by hbm2java
 */
public class Province implements java.io.Serializable {

	private int id;
	private String key;
	private String name;
	private Set<Noacform> noacforms = new HashSet<Noacform>(0);

	public Province() {
	}

	public Province(int id, String key, String name) {
		this.id = id;
		this.key = key;
		this.name = name;
	}

	public Province(int id, String key, String name, Set<Noacform> noacforms) {
		this.id = id;
		this.key = key;
		this.name = name;
		this.noacforms = noacforms;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Noacform> getNoacforms() {
		return this.noacforms;
	}

	public void setNoacforms(Set<Noacform> noacforms) {
		this.noacforms = noacforms;
	}

}
