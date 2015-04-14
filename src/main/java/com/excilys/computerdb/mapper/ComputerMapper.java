package com.excilys.computerdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.computerdb.model.Computer;
import com.excilys.computerdb.utils.Util;

public class ComputerMapper implements RowMapper<Computer> {
	private static final String COMP_ID = "id";
	private static final String COMP_NAME = "name";
	private static final String COMP_INTRO = "introduced";
	private static final String COMP_DISCO = "discontinued";
	private static final String COMP_COMPANY_ID = "company_id";

	@Override
	public Computer mapRow(ResultSet resultSet, int arg1) throws SQLException {
		final Computer computer = new Computer();
		computer.setId(resultSet.getLong(COMP_ID));
		computer.setName(resultSet.getString(COMP_NAME));
		Timestamp tmp1 = resultSet.getTimestamp(COMP_INTRO);
		computer.setIntroduced(Util.getLocalDateFromTimestamp(tmp1));

		Timestamp tmp2 = resultSet.getTimestamp(COMP_DISCO);
		computer.setDiscontinued(Util.getLocalDateFromTimestamp(tmp2));

		computer.setCompanyId(resultSet.getLong(COMP_COMPANY_ID));
		return computer;
	}
}
