package com.excilys.computerdb.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * The Class Company.
 */
public class Company {
	
	/** The id. */
	@Min(1)
	private long id;
	
	/** The name. */
	@NotNull
	@NotEmpty
	@Size(min = 2)
	private String name;
	
	/**
	 * Instantiates a new company.
	 */
	public Company() {}
	
	/**
	 * Instantiates a new company.
	 *
	 * @param id the id of the company
	 * @param name the name of the company
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
	 * @param id the new id
	 */
	public void setId(long id) {
		this.id = id;
	}


	/**
	 * Sets the name of the company.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new Long(id).intValue();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {		
		return this.id == ((Company)obj).id;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[id = " + id + ", "
				+ (name != null ? "  name : " + name : "") + "]";
	}
	
	

}
