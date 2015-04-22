package com.excilys.computerdb.dao;

import java.sql.SQLException;
import java.util.List;

import com.excilys.computerdb.model.Company;

public interface ICompanyDAO {

	public abstract Company getCompanyIdByName(String name) throws SQLException;

	/**
	 * Gets the companies.
	 *
	 * @return the companies
	 * @throws SQLException the SQL exception
	 */
	List<Company> getCompanies() throws SQLException;

	List<Company> getCompaniesSearched(String criteria)
			throws SQLException;

}