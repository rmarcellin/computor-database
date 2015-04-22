package com.excilys.computerdb.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

/**
 * The Class Computer.
 */
@Entity
@Table(name = "computer")
public class Computer implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1228204836119941849L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;
	
	/** The name. */
	@Column(name = "name")
	private String name;
	
	/** The introduced. */
	@Column(name = "introduced")
	@Type(type = "com.excilys.computerdb.model.LocalDateSerializer")
	private LocalDate introduced;
	
	/** The discontinued. */
	@Column(name= "discontinued")
	@Type(type = "com.excilys.computerdb.model.LocalDateSerializer")
	private LocalDate discontinued;
	
	/** The company id. */
	@OneToOne
	@JoinColumn(name = "company_id")
	private Company company;
	
	/**
	 * Instantiates a new computer.
	 */
	public Computer() {}
	
	

	public Computer(long id, String name, LocalDate introduced,
			LocalDate discontinued, Company company) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}



	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public LocalDate getIntroduced() {
		return introduced;
	}

	public LocalDate getDiscontinued() {
		return discontinued;
	}

	public Company getCompany() {
		return company;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}

	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
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
		if (company == null) {
			if (other.company != null) {
				return false;
			}
		} else if (!company.equals(other.company)) {
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

	@Override
	public String toString() {
		return "Computer [id="
				+ id
				+ ", "
				+ (name != null ? "name=" + name + ", " : "")
				+ (introduced != null ? "introduced=" + introduced + ", " : "")
				+ (discontinued != null ? "discontinued=" + discontinued + ", "
						: "") + (company != null ? "company=" + company : "")
				+ "]";
	}	
	
}
