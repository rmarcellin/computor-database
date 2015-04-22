package com.excilys.computerdb.services;

import java.sql.SQLException;
import java.util.List;

import com.excilys.computerdb.model.Company;

public interface ICompanyService {

	public abstract List<Company> getCompanies() throws SQLException;

}