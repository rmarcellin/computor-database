package com.excilys.computerdb.dto;

public class ComputerDTO {
	
	private long id;
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

	public long getId () {
		return this.id;
	}
	
	public void setId (long id) {
		this.id = id;
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

	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}

	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}

	public void setCompanyName (String companyName) {
		this.companyName = companyName;
	}
	
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
}
