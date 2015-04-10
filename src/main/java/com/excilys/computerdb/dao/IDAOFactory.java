package com.excilys.computerdb.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface IDAOFactory {

	/**
	 * Gets the connection.
	 *
	 * @return the connection
	 * @throws SQLException the SQL exception
	 */
	public abstract Connection getConnection() throws SQLException;
	
	public abstract void removeConnection();

}