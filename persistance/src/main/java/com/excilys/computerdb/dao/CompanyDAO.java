package com.excilys.computerdb.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdb.model.Company;
import com.excilys.computerdb.model.QCompany;
import com.mysema.query.jpa.hibernate.HibernateQuery;

/**
 * The Class CompanyDAO.
 */
@Repository
@Transactional
public class CompanyDAO implements ICompanyDAO {

	@Autowired
	private SessionFactory sessionFactory;

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
	public CompanyDAO() {
		super();
		logger.info(CMPYDAO_STARTED);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.excilys.computerdb.dao.ICompanyDAO#getCompanies()
	 */
	@Override
	public List<Company> getCompanies() throws SQLException {
		logger.info("CompanyDAO.getCompanies called");

		QCompany compa = QCompany.company;
		Session session = sessionFactory.getCurrentSession();

		return new HibernateQuery(session).from(compa).list(compa);
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

		QCompany compa = QCompany.company;
		Session session = sessionFactory.getCurrentSession();

		return new HibernateQuery(session).from(compa)
				.where(compa.name.contains(tmpCriteria)).list(compa);
	}
}
