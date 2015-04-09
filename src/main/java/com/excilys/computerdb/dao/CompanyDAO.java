package com.excilys.computerdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	
	private static final Logger logger = LoggerFactory.getLogger(CompanyDAO.class);
	
	/**
	 * INSTATIATION
	 */
	private static final String CMPYDAO_STARTED = "CompanyDAO called";
	
	/**
	 * COMPANYDAO GETTING METHOD
	 */
	private static final String CMPYDAO_GET_STARTED = "Company get started";
	private static final String CMPYDAO_GET_FAILURE = "Company get failed";
	private static final String CMPYDAO_FOUNDED = "Company founded successifuly";
	
	/**
	 * COMPANYDAO SEARCHING METHOD
	 */
	private static final String CMPYDAO_SEARCH_STARTED = "Company search started";
	private static final String CMPYDAO_SEARCH_FAILURE = "Company search failed";
	private static final String CMPYDAO_SEARCH_FOUNDED = "Company searched successifuly";

	/**
	 * Instantiates a new company dao.
	 *
	 * @param daoFactory the repository dao
	 */
	public CompanyDAO(DAOFactory daoFactory) {
		logger.info(CMPYDAO_STARTED);
		this.repository = daoFactory;
	}
	
	public long getCompanyIdByName (String name) throws SQLException {
		logger.info(name + " : " + CMPYDAO_GET_STARTED);
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
			logger.error(name + " : " + CMPYDAO_GET_FAILURE);
			throw new DAOException(e);
		} finally {
			Util.closeRessources(connection, preparedStatement, resultSet);
		}
		logger.info(name + " : " + CMPYDAO_FOUNDED);
		return id;
	}
	
	/**
	 * Gets the companies.
	 *
	 * @return the companies
	 * @throws SQLException the SQL exception
	 */
	public List<Company> getCompanies() throws SQLException {
		logger.info("All companies : " + CMPYDAO_GET_STARTED);
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
			logger.error("All companies : " + CMPYDAO_GET_FAILURE);
			throw new DAOException(e);
		} finally {
			Util.closeRessources(connection, preparedStatement, resultSet);
		}
		logger.info("All companies : " + CMPYDAO_FOUNDED);
		return companies;
	}
	
	public List<Company> getCompaniesSearched(String criteria) throws SQLException {
		logger.info(CMPYDAO_SEARCH_STARTED);
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
			logger.error(criteria + " : " + CMPYDAO_SEARCH_FAILURE);
			throw new DAOException(e);
		} finally {
			Util.closeRessources(connection, preparedStatement, resultSet);
		}
		logger.info("All companies : " + CMPYDAO_SEARCH_FOUNDED);
		return companies;
	}
}
