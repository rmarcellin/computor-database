package com.excilys.computerdb.services;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.computerdb.dao.*;
import com.excilys.computerdb.model.*;

@Service
public class CompanyService implements ICompanyService {
	
	@Autowired
	private ICompanyDAO companyDAO;
	
	private static final Logger logger = LoggerFactory.getLogger(CompanyService.class);
	
	/* (non-Javadoc)
	 * @see com.excilys.computerdb.services.ICompanyService#getCompanies()
	 */
	@Override
	public List<Company> getCompanies() throws SQLException {
		logger.info("CompanyService.getCompanies called");
		return companyDAO.getCompanies();
	}

}
