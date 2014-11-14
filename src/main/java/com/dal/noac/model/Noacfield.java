package com.dal.noac.model;

// Generated Oct 26, 2014 5:44:33 PM by Hibernate Tools 4.3.1

import java.util.HashSet;
import java.util.Set;

/**
 * Noacfield generated by hbm2java
 */
public class Noacfield implements java.io.Serializable {

	private int id;
	private Section section;
	private String label;
	private String inputFormat;
	private String orderIndex;
	private String value;
	private Set<Noacfieldselectoptions> noacfieldselectoptionses = new HashSet<Noacfieldselectoptions>(
			0);

	public Noacfield() {
	}

	public Noacfield(int id) {
		this.id = id;
	}

	public Noacfield(int id, Section section, String label, String inputFormat,
			String orderIndex, String value,
			Set<Noacfieldselectoptions> noacfieldselectoptionses) {
		this.id = id;
		this.section = section;
		this.label = label;
		this.inputFormat = inputFormat;
		this.orderIndex = orderIndex;
		this.value = value;
		this.noacfieldselectoptionses = noacfieldselectoptionses;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Section getSection() {
		return this.section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getInputFormat() {
		return this.inputFormat;
	}

	public void setInputFormat(String inputFormat) {
		this.inputFormat = inputFormat;
	}

	public String getOrderIndex() {
		return this.orderIndex;
	}

	public void setOrderIndex(String orderIndex) {
		this.orderIndex = orderIndex;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Set<Noacfieldselectoptions> getNoacfieldselectoptionses() {
		return this.noacfieldselectoptionses;
	}

	public void setNoacfieldselectoptionses(
			Set<Noacfieldselectoptions> noacfieldselectoptionses) {
		this.noacfieldselectoptionses = noacfieldselectoptionses;
	}

}