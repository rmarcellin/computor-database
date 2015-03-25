package com.excilys.computerdb.beans;

import org.joda.time.LocalDate;


// TODO: Auto-generated Javadoc
/**
 * The Class Computer.
 */
public class Computer {
	
	/** The id. */
	private long id;
	
	/** The name. */
	private String name;
	
	/** The introduced. */
	private LocalDate introduced;
	
	/** The discontinued. */
	private LocalDate discontinued;
	
	/** The company id. */
	private long companyId;
	
	private String companyName;
	
	/**
	 * Instantiates a new computer.
	 */
	public Computer() {}
	 
	/**
	 * Instantiates a new computer.
	 *
	 * @param id the id of the new computer
	 * @param name the name of the new computer
	 * @param introduced the introduced of the new computer
	 * @param discontinued the discontinued of the new computer
	 * @param companyId the company id of the new computer
	 */
	public Computer(long id, String name, LocalDate introduced, LocalDate discontinued,
			long companyId) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyId = companyId;
		this.companyName = "";
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the introduced.
	 *
	 * @return the introduced
	 */
	public LocalDate getIntroduced() {
		return introduced;
	}

	/**
	 * Gets the discontinued.
	 *
	 * @return the discontinued
	 */
	public LocalDate getDiscontinued() {
		return discontinued;
	}

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	public long getCompanyId() {
		return companyId;
	}
	
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the introduced.
	 *
	 * @param introduced the new introduced
	 */
	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}

	/**
	 * Sets the discontinued.
	 *
	 * @param discontinued the new discontinued
	 */
	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}

	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
	
	public void setCompanyName(String name) {
		this.companyName = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (companyId ^ (companyId >>> 32));
		result = prime * result
				+ ((companyName == null) ? 0 : companyName.hashCode());
		result = prime * result
				+ ((discontinued == null) ? 0 : discontinued.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result
				+ ((introduced == null) ? 0 : introduced.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Computer)) {
			return false;
		}
		Computer other = (Computer) obj;
		if (companyId != other.companyId) {
			return false;
		}
		if (companyName == null) {
			if (other.companyName != null) {
				return false;
			}
		} else if (!companyName.equals(other.companyName)) {
			return false;
		}
		if (discontinued == null) {
			if (other.discontinued != null) {
				return false;
			}
		} else if (!discontinued.equals(other.discontinued)) {
			return false;
		}
		if (id != other.id) {
			return false;
		}
		if (introduced == null) {
			if (other.introduced != null) {
				return false;
			}
		} else if (!introduced.equals(other.introduced)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[id = " + id + ", "
				+ (name != null ? "Computer : " + name + ", " : "") 
				+ " introduced : " + introduced + ", discontinued : " 
				+ discontinued + ", company : " + companyName + "]";
	}	 
	 	

}
