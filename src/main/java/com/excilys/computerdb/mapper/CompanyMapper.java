package com.excilys.computerdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.computerdb.beans.Company;

public class CompanyMapper implements Mapper<Company> {

	@Override
	public Company map(ResultSet resultSet) throws SQLException {
		if (resultSet == null) {
			throw new IllegalArgumentException();
		}
		final Company c = new Company();
		c.setId(resultSet.getLong("id"));
		c.setName(resultSet.getString("name"));
		return c;
	}

}
