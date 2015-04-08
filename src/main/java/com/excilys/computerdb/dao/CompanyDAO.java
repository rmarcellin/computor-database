package com.excilys.computerdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdb.beans.Company;
import com.excilys.computerdb.mapper.CompanyMapper;
import com.excilys.computerdb.exception.DAOException;
import com.excilys.computerdb.utils.Util;

/**
 * The Class CompanyDAO.
 */
public class CompanyDAO {
	
	/** The repository. */
	private DAOFactory repository;
	
	/** The Constant SQL_SELECT_ALL_COMPANIES. */
	private static final String SQL_SELECT_ALL_COMPANIES = "SELECT * FROM company";
	private static final String SQL_SELECT_BY_NAME = "SELECT * FROM company WHERE name = ?";
	private static final String SQL_SEARCH_COMPANIES = "SELECT * FROM company WHERE name LIKE '% ? %'";

	/**
	 * Instantiates a new company dao.
	 *
	 * @param daoFactory the repository dao
	 */
	public CompanyDAO(DAOFactory daoFactory) {
		this.repository = daoFactory;
	}
	
	public long getCompanyIdByName (String name) throws SQLException {
		ResultSet resultSet = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		long id;
		try {
			connection = repository.getConnection();
			preparedStatement = connection.prepareStatement(SQL_SELECT_BY_NAME);
			preparedStatement.setString(1, name);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			id = new CompanyMapper().mapResultSet(resultSet).getId();			
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			Util.closeRessources(connection, preparedStatement, resultSet);
		}
		
		return id;
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
			Util.closeRessources(connection, preparedStatement, resultSet);
		}
		
		return companies;
	}
	
	public List<Company> getCompaniesSearched(String criteria) throws SQLException {
		if (criteria == null || criteria.isEmpty()) {
			return null;
		}
		
		List<Company> companies = new ArrayList<>();
		Company company = null;
		ResultSet resultSet = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = repository.getConnection();
			preparedStatement = connection.prepareStatement(SQL_SEARCH_COMPANIES);
			preparedStatement.setString(1, criteria);
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
			Util.closeRessources(connection, preparedStatement, resultSet);
		}
		
		return companies;
	}
}
