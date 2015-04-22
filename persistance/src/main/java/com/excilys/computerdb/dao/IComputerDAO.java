package com.excilys.computerdb.dao;

import java.sql.SQLException;
import java.util.List;

import com.excilys.computerdb.model.Computer;

public interface IComputerDAO {

	/**
	 * Delete computer.
	 *
	 * @param compId
	 *            the comp id
	 * @return the computer
	 * @throws SQLException
	 *             the SQL exception
	 */
	void deleteComputer(long compId) throws SQLException;

	/**
	 * Adds the computer to db.
	 *
	 * @param comp
	 *            the comp
	 * @throws SQLException
	 *             the SQL exception
	 */
	void addOrUpdateComputer(Computer comp) throws SQLException;

	/**
	 * Gets the computer by id.
	 *
	 * @param criteria
	 *            the criteria
	 * @return the computer by id
	 * @throws SQLException
	 *             the SQL exception
	 */
	Computer getComputerById(long criteria) throws SQLException;

	/**
	 * Gets the computer by name.
	 *
	 * @param criteria
	 *            the criteria
	 * @return the computer by name
	 * @throws SQLException
	 *             the SQL exception
	 */
	Computer getComputerByName(String name) throws SQLException;

	/**
	 * Gets the computers.
	 *
	 * @return the computers
	 * @throws SQLException
	 *             the SQL exception
	 */
	List<Computer> getComputers(String key, String sortOrder)
			throws SQLException;

	List<Computer> getComputersSearched(String criteria)
			throws SQLException;

}