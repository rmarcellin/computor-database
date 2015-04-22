package com.excilys.computerdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.computerdb.model.Company;

public class CompanyMapper implements RowMapper<Company> {

	@Override
	public Company mapRow(ResultSet resultSet, int arg1) throws SQLException {
		final Company company = new Company();
		company.setId(resultSet.getLong("id"));
		company.setName(resultSet.getString("name"));
		return company;
	}

}
