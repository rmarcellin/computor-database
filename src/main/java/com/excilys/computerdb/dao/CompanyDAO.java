package com.excilys.computerdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdb.beans.Company;
import com.excilys.computerdb.exception.DAOException;

// TODO: Auto-generated Javadoc
/**
 * The Class CompanyDAO.
 */
public class CompanyDAO {
	
	/** The repository. */
	private DAOFactory repository;
	
	/** The Constant SQL_SELECT_ALL_COMPANIES. */
	private static final String SQL_SELECT_ALL_COMPANIES = "SELECT * FROM company";

	/**
	 * Instantiates a new company dao.
	 *
	 * @param daoFactory the repository dao
	 */
	public CompanyDAO(DAOFactory daoFactory) {
		this.repository = daoFactory;
	}
	
	/**
	 * Gets the companies.
	 *
	 * @return the companies
	 * @throws SQLException the SQL exception
	 */
	public List<Company> getCompanies() throws SQLException {
		List<Company> companies = new ArrayList<>();
		Company company = null;
		ResultSet resultSet = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = repository.getConnection();
			preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_COMPANIES);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				company = new Company();
				company.setId(resultSet.getLong("id"));
				company.setName(resultSet.getString("name"));
				companies.add(company);
			}
			
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			connection.close();
			preparedStatement.close();
		}
		
		return companies;
	}

}
