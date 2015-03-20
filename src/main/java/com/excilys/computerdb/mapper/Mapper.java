package com.excilys.computerdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Mapper<Type> {
	
	/**
	 * Maps the result set into an object f type Type.
	 * @pre resultSet != null
	 * @param resultSet the result set to be mapped
	 * @return the object corresponding to the result set
	 * @throws SQLException the SQL exception
	 */
	Type map(ResultSet resultSet) throws SQLException; 

}
