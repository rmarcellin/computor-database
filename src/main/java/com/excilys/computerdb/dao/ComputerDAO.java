package com.excilys.computerdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import com.excilys.computerdb.beans.Computer;
import com.excilys.computerdb.exception.DAOException;
import com.excilys.computerdb.mapper.ComputerMapper;
import com.excilys.computerdb.utils.Util;

// TODO: Auto-generated Javadoc
/**
 * The Class ComputerDAO.
 */
public class ComputerDAO {

	/** The repository. */
	private DAOFactory daoFactory;

	/** The Constant SQL_SELECT_ALL_COMPUTERS. */
	private static final String SQL_SELECT_ALL_COMPUTERS = "SELECT * FROM computer";

	/** The Constant SQL_SELECT_ONE_COMPUTER_BY_ID. */
	private static final String SQL_SELECT_ONE_COMPUTER_BY_ID = "SELECT * FROM computer WHERE id = ?";

	/** The Constant SQL_SELECT_ONE_COMPUTER_BY_NAME. */
	private static final String SQL_SELECT_ONE_COMPUTER_BY_NAME = "SELECT * FROM computer WHERE name = ?";

	/** The Constant SQL_CREATE_COMPUTER. */
	private static final String SQL_CREATE_COMPUTER = "INSERT INTO computer (name, introduced, discontinued, company_id) "
			+ "VALUES (?, ?, ?, ?)";

	/** The Constant SQL_UPDATE_COMPUTER. */
	private static final String SQL_UPDATE_COMPUTER = "UPDATE computer SET name = ?, introduced = ?, "
			+ "discontinued = ?, company_id = ? WHERE id = ?";

	/** The Constant SQL_DELETE_COMPUTER. */
	private static final String SQL_DELETE_COMPUTER = "DELETE FROM computer WHERE id = ?";

	private static final String SQL_SEARCH = "SELECT * FROM computer WHERE name LIKE '% ? %'";

	// select * from computer comp, company camp where comp.name like '%LL%' and
	// camp.name like '%pple%' group by comp.name;

	/**
	 * Instantiates a new computer dao.
	 *
	 * @param daoFactory
	 *            the repository dao
	 */
	public ComputerDAO(DAOFactory daoFactory) {
		// TODO Auto-generated constructor stub
		this.daoFactory = daoFactory;
	}

	/**
	 * Delete computer.
	 *
	 * @param compId
	 *            the comp id
	 * @return the computer
	 * @throws SQLException
	 *             the SQL exception
	 */
	public Computer deleteComputer(long compId) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Computer deletedComp = this.getComputerById(compId);

		try {
			connection = daoFactory.getConnection();
			preparedStatement = connection
					.prepareStatement(SQL_DELETE_COMPUTER);

			preparedStatement.setLong(1, new Long(compId));

			int status = preparedStatement.executeUpdate();
			if (status == 0) {
				throw new DAOException("Failed to delete the computer");
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			Util.closeRessources(connection, preparedStatement, null);
		}

		return deletedComp;

	}

	/**
	 * Update computer db.
	 *
	 * @param compId
	 *            the comp id
	 * @param values
	 *            the values
	 * @return the computer
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void updateComputer(Computer computer) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = daoFactory.getConnection();
			preparedStatement = connection
					.prepareStatement(SQL_UPDATE_COMPUTER);

			// UPDATING
			preparedStatement.setString(1, computer.getName());
			preparedStatement.setTimestamp(2,
					Util.getTimeStampFromLocalDate(computer.getIntroduced()));
			preparedStatement.setTimestamp(3,
					Util.getTimeStampFromLocalDate(computer.getDiscontinued()));
			preparedStatement.setLong(4, computer.getCompanyId());
			preparedStatement.setLong(5, computer.getId());

			int status = preparedStatement.executeUpdate();
			if (status == 0) {
				throw new DAOException("Failed to update computer : "
						+ computer);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			Util.closeRessources(connection, preparedStatement, null);
		}
	}

	/**
	 * Adds the computer to db.
	 *
	 * @param comp
	 *            the comp
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void addComputer(Computer comp) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = daoFactory.getConnection();
			preparedStatement = connection
					.prepareStatement(SQL_CREATE_COMPUTER);
			if (comp.getName() == null) {
				throw new DAOException("Computer name should not be empty ");
			}
			preparedStatement.setString(1, comp.getName());

			Timestamp introduced = Util.getTimeStampFromLocalDate(comp
					.getIntroduced());
			preparedStatement.setTimestamp(2, introduced);

			Timestamp discontinued = Util.getTimeStampFromLocalDate(comp
					.getDiscontinued());
			preparedStatement.setTimestamp(3, discontinued);

			preparedStatement.setLong(4, comp.getCompanyId());

			int status = preparedStatement.executeUpdate();
			if (status == 0) {
				throw new DAOException("Failed to add the new computer");
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			Util.closeRessources(connection, preparedStatement, null);
		}

	}

	/**
	 * Gets the computer by id.
	 *
	 * @param criteria
	 *            the criteria
	 * @return the computer by id
	 * @throws SQLException
	 *             the SQL exception
	 */
	public Computer getComputerById(long criteria) throws SQLException {
		Computer comp = null;
		ResultSet resultSet = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String companyName = "";

		try {
			connection = daoFactory.getConnection();
			preparedStatement = connection
					.prepareStatement(SQL_SELECT_ONE_COMPUTER_BY_ID);
			preparedStatement.setLong(1, new Long(criteria));
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			comp = new ComputerMapper().mapResultSet(resultSet);
			companyName = Util.getCompanyNameById(comp.getCompanyId(),
					connection);
			comp.setCompanyName(companyName);
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			Util.closeRessources(connection, preparedStatement, resultSet);
		}

		return comp;
	}

	/**
	 * Gets the computer by name.
	 *
	 * @param criteria
	 *            the criteria
	 * @return the computer by name
	 * @throws SQLException
	 *             the SQL exception
	 */
	public Computer getComputerByName(String name) throws SQLException {
		Computer comp = null;
		ResultSet resultSet = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String companyName = null;

		try {
			connection = daoFactory.getConnection();
			preparedStatement = connection
					.prepareStatement(SQL_SELECT_ONE_COMPUTER_BY_NAME);
			preparedStatement.setString(1, name);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			comp = new ComputerMapper().mapResultSet(resultSet);
			companyName = Util.getCompanyNameById(comp.getCompanyId(),
					connection);
			comp.setCompanyName(companyName);
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			Util.closeRessources(connection, preparedStatement, resultSet);
		}

		return comp;
	}

	/**
	 * Gets the computers.
	 *
	 * @return the computers
	 * @throws SQLException
	 *             the SQL exception
	 */
	public List<Computer> getComputers() throws SQLException {
		List<Computer> listComp = new ArrayList<>();
		Computer computer = null;
		ResultSet resultSet = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = daoFactory.getConnection();
			preparedStatement = connection
					.prepareStatement(SQL_SELECT_ALL_COMPUTERS);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String companyName = "";
				computer = new ComputerMapper().mapResultSet(resultSet);
				long companyId = computer.getCompanyId();
				if (companyId != 0) {
					companyName = Util
							.getCompanyNameById(companyId, connection);
					computer.setCompanyName(companyName);
				}
				listComp.add(computer);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			Util.closeRessources(connection, preparedStatement, resultSet);
		}

		return listComp;
	}

}
