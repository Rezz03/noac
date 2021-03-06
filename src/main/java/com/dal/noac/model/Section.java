package com.dal.noac.model;

// Generated Oct 26, 2014 5:44:33 PM by Hibernate Tools 4.3.1

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Section generated by hbm2java
 */
public class Section implements java.io.Serializable {

	private int id;
	private Noacform noacform;
	private String title;
	private Integer orderIndex;
	private List<Noacfield> noacfields = new ArrayList<Noacfield>(0);

	public Section() {
	}

	public Section(int id, Noacform noacform, String title) {
		this.id = id;
		this.noacform = noacform;
		this.title = title;
	}

	public Section(int id, Noacform noacform, String title, Integer orderIndex,
			List<Noacfield> noacfields) {
		this.id = id;
		this.noacform = noacform;
		this.title = title;
		this.orderIndex = orderIndex;
		this.noacfields = noacfields;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Noacform getNoacform() {
		return this.noacform;
	}

	public void setNoacform(Noacform noacform) {
		this.noacform = noacform;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getOrderIndex() {
		return this.orderIndex;
	}

	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}

	public List<Noacfield> getNoacfields() {
		return this.noacfields;
	}

	public void setNoacfields(List<Noacfield> noacfields) {
		this.noacfields = noacfields;
	}

}
