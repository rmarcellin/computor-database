package com.excilys.computerdb.services;

import java.sql.SQLException;
import java.util.List;

import com.excilys.computerdb.model.Computer;

public interface IComputerService {

	List<Computer> getComputers(String key, String sortOrder)
			throws SQLException;

	List<Computer> getComputersSearched(String criteria)
			throws SQLException;

	Computer getComputerById(long id) throws SQLException;

	Computer getComputerByName(String name) throws SQLException;

	void setComputer(Computer computer) throws SQLException;

	void deleteComputer(long computerId) throws SQLException;

	void updateComputer(Computer computer) throws SQLException;

}