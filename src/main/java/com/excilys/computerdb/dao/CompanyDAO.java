package com.excilys.computerdb.dao;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.computerdb.mapper.CompanyMapper;
import com.excilys.computerdb.model.Company;

/**
 * The Class CompanyDAO.
 */
@Repository("companyDAO")
public class CompanyDAO implements ICompanyDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	/** The repository. */
	@Autowired
	private IDAOFactory daoFactory;

	public CompanyDAO() {
		super();
	}

	private static final Logger logger = LoggerFactory
			.getLogger(CompanyDAO.class);

	/**
	 * INSTATIATION MSG
	 */
	private static final String CMPYDAO_STARTED = "CompanyDAO called";

	/**
	 * Instantiates a new company dao.
	 *
	 * @param daoFactory
	 *            the repository dao
	 */
	public CompanyDAO(IDAOFactory daoFactory) {
		logger.info(CMPYDAO_STARTED);
		this.daoFactory = daoFactory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.excilys.computerdb.dao.ICompanyDAO#getCompanyIdByName(java.lang.String
	 * )
	 */
	@Override
	public Company getCompanyIdByName(String name) throws SQLException {
		logger.info("CompanyDAO.getCompanyIdByName called - Argument : {}",
				name);
		if (name == null) {
			logger.error("CompanyDAO.getCompanyIdByName - name null");
			throw new IllegalArgumentException();
		}

		String tmpName = name.trim();
		if (tmpName.isEmpty()) {
			logger.error("CompanyDAO.getCompanyIdByName - name empty");
			throw new IllegalArgumentException();
		}

		final String SQL_SELECT_BY_NAME = "SELECT * FROM company WHERE name = "
				+ tmpName;
		Company company = jdbcTemplate.query(SQL_SELECT_BY_NAME,
				new CompanyMapper()).get(0);

		// Removing the current connection from the ThreadLocal
		daoFactory.removeConnection();

		logger.info(
				"CompanyDAO.getCompanyIdByName \"{}\"retrieved successifuly",
				company.getName());

		return company;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.excilys.computerdb.dao.ICompanyDAO#getCompanies()
	 */
	@Override
	public List<Company> getCompanies() throws SQLException {
		logger.info("CompanyDAO.getCompanies called");
		List<Company> companies = null;
		final String SQL_SELECT_ALL_COMPANIES = "SELECT * FROM company";
		companies = jdbcTemplate.query(SQL_SELECT_ALL_COMPANIES,
				new CompanyMapper());

		// Removing the current connection from the ThreadLocal
		daoFactory.removeConnection();

		logger.info("CompanyDAO.getCompanies : All companies retrieved successifuly");
		return companies;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.excilys.computerdb.dao.ICompanyDAO#getCompaniesSearched(java.lang
	 * .String)
	 */
	@Override
	public List<Company> getCompaniesSearched(String criteria)
			throws SQLException {
		logger.info("CompanyDAO.getCompaniesSearched called");
		if (criteria == null) {
			logger.error("CompanyDAO.getCompaniesSearched : creteria null");
			return null;
		}

		String tmpCriteria = criteria.trim();
		if (tmpCriteria.isEmpty()) {
			logger.error("CompanyDAO.getCompaniesSearched : creteria empty");
			return null;
		}

		final String SQL_SEARCH_COMPANIES = "SELECT * FROM company WHERE UCASE(name) LIKE ?";
		List<Company> companies = jdbcTemplate.query(SQL_SEARCH_COMPANIES,
				new Object[] {"%" + tmpCriteria + "%"}, new CompanyMapper());
		
		// Removing the current connection from the ThreadLocal
		daoFactory.removeConnection();
		
		logger.info("CompanyDAO.getCompaniesSearched : All companies retrieved successifuly");

		return companies;
	}
}
