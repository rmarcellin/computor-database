package com.excilys.computerdb.services;

import java.sql.SQLException;
import java.util.List;

import com.excilys.computerdb.dao.*;
import com.excilys.computerdb.beans.*;
import com.excilys.computerdb.dao.DAOFactory;

public class CompanyService {
	private static DAOFactory factory;
	private static CompanyDAO companyDAO;
	
	public CompanyService() {
		factory = DAOFactory.getInstance();
		companyDAO = new CompanyDAO(factory);
	}
	
	public List<Company> getCompanies() throws SQLException {
		return companyDAO.getCompanies();
	}

}
