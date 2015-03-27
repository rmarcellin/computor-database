package com.excilys.computerdb.services;

import java.sql.SQLException;
import java.util.List;

import com.excilys.computerdb.beans.Computer;
import com.excilys.computerdb.dao.ComputerDAO;
import com.excilys.computerdb.dao.DAOFactory;

public class ComputerService {
	
	private static DAOFactory factory;
	private static ComputerDAO computerDAO;
	
	public ComputerService() {
		factory = DAOFactory.getInstance();
		computerDAO = new ComputerDAO(factory);
	}
	
	public List<Computer> getComputers() throws SQLException {
		return computerDAO.getComputers();
	}
	
	public List<Computer> getComputersSearched(String criteria) throws SQLException {
		return computerDAO.getComputersSearched(criteria);
	}
	
	public Computer getComputerById(long id) throws SQLException {
		return computerDAO.getComputerById(id);
	}
	
	public Computer getComputerByName(String name) throws SQLException {
		return computerDAO.getComputerByName(name);
	}
	
	public void setComputer(Computer computer) throws SQLException {
		computerDAO.addComputer(computer);
	}
	
	public void deleteComputer(long computerId) throws SQLException {
		computerDAO.deleteComputer(computerId);
	}
	
	public void updateComputer(Computer computer) throws SQLException {
		computerDAO.updateComputer(computer);
	}

}
