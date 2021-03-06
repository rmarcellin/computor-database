package com.excilys.computerdb.dto;

public class CompanyDTO {
	private String name;
	private long id;
	
	public CompanyDTO() {
		super();
	}

	public CompanyDTO(long id, String name) {
		this.id = id;
		this.name = name;
	}

	public long getId () {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setId (long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
}
