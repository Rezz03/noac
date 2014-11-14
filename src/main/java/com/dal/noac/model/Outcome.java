package com.dal.noac.model;

// Generated Oct 26, 2014 5:44:33 PM by Hibernate Tools 4.3.1

import java.util.HashSet;
import java.util.Set;

/**
 * Outcome generated by hbm2java
 */
public class Outcome implements java.io.Serializable {

	private int id;
	private byte[] successful;
	private String criteria;
	private String message;
	private Set<Noacform> noacforms = new HashSet<Noacform>(0);

	public Outcome() {
	}

	public Outcome(int id, byte[] successful, String criteria, String message) {
		this.id = id;
		this.successful = successful;
		this.criteria = criteria;
		this.message = message;
	}

	public Outcome(int id, byte[] successful, String criteria, String message,
			Set<Noacform> noacforms) {
		this.id = id;
		this.successful = successful;
		this.criteria = criteria;
		this.message = message;
		this.noacforms = noacforms;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte[] getSuccessful() {
		return this.successful;
	}

	public void setSuccessful(byte[] successful) {
		this.successful = successful;
	}

	public String getCriteria() {
		return this.criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Set<Noacform> getNoacforms() {
		return this.noacforms;
	}

	public void setNoacforms(Set<Noacform> noacforms) {
		this.noacforms = noacforms;
	}

}