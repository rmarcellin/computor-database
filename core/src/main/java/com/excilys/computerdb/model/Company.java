package com.excilys.computerdb.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The Class Company.
 */
@Entity
@Table(name = "company")
public class Company implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6142732520823226097L;

	/** The id. */
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	/** The name. */
	@Column(name = "name")
	private String name;

	/**
	 * Instantiates a new company.
	 */
	public Company() {
		super();
	}

	/**
	 * Instantiates a new company.
	 *
	 * @param id
	 *            the id of the company
	 * @param name
	 *            the name of the company
	 */
	public Company(long id, String name) {
		this.id = id;
		this.name = name;
	}

	/**
	 * Gets the id of the company.
	 *
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Gets the name of the company.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the id of the company.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Sets the name of the company.
	 *
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new Long(id).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return this.id == ((Company) obj).id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[id = " + id + ", " + (name != null ? "  name : " + name : "")
				+ "]";
	}

}
