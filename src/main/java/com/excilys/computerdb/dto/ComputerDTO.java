package com.excilys.computerdb.dto;

import org.joda.time.LocalDate;

public class ComputerDTO {
	
	private String name;
	private String introduced;
	private String discontinued;
	private String companyName;
	private long companyId;
	
	public ComputerDTO() {}

	public ComputerDTO(String name, String introduced, String discontinued,
			long companyId) {
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyName = "";
		this.companyId = companyId;
	}

	public String getName() {
		return name;
	}

	public String getIntroduced() {
		return introduced;
	}

	public String getDiscontinued() {
		return discontinued;
	}
	
	public String getCompanyName () {
		return companyName;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIntroduced(LocalDate introduced) {
		String intro = "";
		if (introduced != null) {
			intro = introduced.toString();
		}
		this.introduced = intro;
	}

	public void setDiscontinued(LocalDate discontinued) {
		String disco = "";
		if (discontinued != null) {
			disco = discontinued.toString();
		}
		this.discontinued = disco;
	}

	public void setCompanyName (String companyName) {
		this.companyName = companyName;
	}
	
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
}
