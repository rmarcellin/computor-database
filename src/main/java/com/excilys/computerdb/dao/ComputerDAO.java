package com.excilys.computerdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.computerdb.exception.DAOException;
import com.excilys.computerdb.mapper.ComputerMapper;
import com.excilys.computerdb.model.Computer;
import com.excilys.computerdb.utils.Util;

/**
 * The Class ComputerDAO.
 */
@Repository
public class ComputerDAO implements IComputerDAO {

	/** The repository. */
	@Autowired
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

	private static final String SQL_SEARCH_COMPUTERS = "SELECT * FROM computer "
			+ "LEFT OUTER JOIN company "
			+ "ON computer.company_id = company.id WHERE UCASE(computer.name) "
			+ "LIKE ? or UCASE(company.name) LIKE ?";
	
	private static final Logger logger = LoggerFactory.getLogger(ComputerDAO.class);
	
	/**
	 * INSTATIATION
	 */
	private static final String CMPTDAO_STARTED = "ComputerDAO called";
	
	/**
	 * COMPUTERDAO DELETE METHOD
	 */
	private static final String CMPTDAO_DELETION_FAILURE = "Computer deletion failed";	
	private static final String CMPTDAO_DELETED = "Computer deleted successifuly";
	private static final String CMPTDAO_START_DELETE = "Computer delete started"; 
	
	/**
	 * COMPUTERDAO UPDATING METHOD
	 */
	private static final String CMPTDAO_UPDATE_STARTED = "Computer update started";
	private static final String CMPTDAO_UPDATE_FAILURE = "Computer update failed";
	private static final String CMPTDAO_UPDATED = "Computer updated successifuly";
	
	/**
	 * COMPUTERDAO ADDING METHOD
	 */
	private static final String CMPTDAO_ADD_STARTED = "Computer add started";
	private static final String CMPTDAO_ADD_FAILURE = "Computer add failed";
	private static final String CMPTDAO_ADDED = "Computer added successifuly";
	
	/**
	 * COMPUTERDAO GETTING METHOD
	 */
	private static final String CMPTDAO_GET_STARTED = "Computer get started";
	private static final String CMPTDAO_GET_FAILURE = "Computer get failed";
	private static final String CMPTDAO_FOUNDED = "Computer founded successifuly";
	
	/**
	 * COMPUTERDAO SEARCHING METHOD
	 */
	private static final String CMPTDAO_SEARCH_STARTED = "Computer search started";
	private static final String CMPTDAO_SEARCH_FAILURE = "Computer search failed";
	private static final String CMPTDAO_SEARCH_FOUNDED = "Computer searched successifuly";

	/**
	 * Instantiates a new computer dao.
	 *
	 * @param daoFactory
	 *            the repository dao
	 */
	@Autowired
	public ComputerDAO(DAOFactory daoFactory) {
		logger.info(CMPTDAO_STARTED);
		this.daoFactory = daoFactory;
	}

	/* (non-Javadoc)
	 * @see com.excilys.computerdb.dao.IComputerDAO#deleteComputer(long)
	 */
	@Override
	public Computer deleteComputer(long compId) throws SQLException {
		logger.info(compId + " : " + CMPTDAO_START_DELETE);
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
				logger.error(compId + " : " + CMPTDAO_DELETION_FAILURE);
				throw new DAOException("Failed to delete the computer");
			}

		} catch (SQLException e) {
			logger.error(compId + " : " + CMPTDAO_DELETION_FAILURE);
			throw new DAOException(e);
		} finally {
			Util.closeRessources(connection, preparedStatement, null);
			daoFactory.removeConnection();
		}
		logger.info(compId + " : " + CMPTDAO_DELETED);
		return deletedComp;

	}

	/* (non-Javadoc)
	 * @see com.excilys.computerdb.dao.IComputerDAO#updateComputer(com.excilys.computerdb.model.Computer)
	 */
	@Override
	public void updateComputer(Computer computer) throws SQLException {
		logger.info(computer.getName() + " : " + CMPTDAO_UPDATE_STARTED);
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
				logger.error(computer.getName() + " : " + CMPTDAO_UPDATE_FAILURE);
				throw new DAOException("Failed to update computer : "
						+ computer);
			}

		} catch (SQLException e) {
			logger.error(computer.getName() + " : " + CMPTDAO_UPDATE_FAILURE);
			throw new DAOException(e);
		} finally {
			Util.closeRessources(connection, preparedStatement, null);
			daoFactory.removeConnection();
		}
		logger.info(computer.getName() + " : " + CMPTDAO_UPDATED);
	}

	/* (non-Javadoc)
	 * @see com.excilys.computerdb.dao.IComputerDAO#addComputer(com.excilys.computerdb.model.Computer)
	 */
	@Override
	public void addComputer(Computer comp) throws SQLException {
		logger.info(comp.getName() + " : " + CMPTDAO_ADD_STARTED);
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = daoFactory.getConnection();
			preparedStatement = connection
					.prepareStatement(SQL_CREATE_COMPUTER);
			if (comp.getName() == null) {
				logger.error(comp.getName() + " : " + CMPTDAO_ADD_FAILURE);
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
				logger.error(comp.getName() + " : " + CMPTDAO_ADD_FAILURE);
				throw new DAOException("Failed to add the new computer");
			}

		} catch (SQLException e) {
			logger.error(comp.getName() + " : " + CMPTDAO_ADD_FAILURE);
			throw new DAOException(e);
		} finally {
			Util.closeRessources(connection, preparedStatement, null);
			daoFactory.removeConnection();
		}
		logger.info(comp.getName() + " : " + CMPTDAO_ADDED);
	}

	/* (non-Javadoc)
	 * @see com.excilys.computerdb.dao.IComputerDAO#getComputerById(long)
	 */
	@Override
	public Computer getComputerById(long criteria) throws SQLException {
		logger.info(criteria + " : " + CMPTDAO_GET_STARTED);
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
			logger.error(criteria + " : " + CMPTDAO_GET_FAILURE);
			throw new DAOException(e);
		} finally {
			Util.closeRessources(connection, preparedStatement, resultSet);
			daoFactory.removeConnection();
		}
		logger.info(comp.getName() + " : " + CMPTDAO_FOUNDED);
		return comp;
	}

	/* (non-Javadoc)
	 * @see com.excilys.computerdb.dao.IComputerDAO#getComputerByName(java.lang.String)
	 */
	@Override
	public Computer getComputerByName(String name) throws SQLException {
		logger.info(name + " : " + CMPTDAO_GET_STARTED);
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
			logger.error(name + " : " + CMPTDAO_GET_FAILURE);
			throw new DAOException(e);
		} finally {
			Util.closeRessources(connection, preparedStatement, resultSet);
			daoFactory.removeConnection();
		}
		logger.info(name + " : " + CMPTDAO_FOUNDED);
		return comp;
	}

	/* (non-Javadoc)
	 * @see com.excilys.computerdb.dao.IComputerDAO#getComputers(java.lang.String, java.lang.String)
	 */
	@Override
	public List<Computer> getComputers(String key, String sortOrder)
			throws SQLException {
		logger.info("All computers : " + CMPTDAO_GET_STARTED);
		List<Computer> listComp = new ArrayList<>();
		Computer computer = null;
		ResultSet resultSet = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = daoFactory.getConnection();
			// Test if non sort order and key were given
			if (key == null || sortOrder == null) {
				preparedStatement = connection
						.prepareStatement(SQL_SELECT_ALL_COMPUTERS);
			} else {
				preparedStatement = connection
						.prepareStatement(SQL_SELECT_ALL_COMPUTERS
								+ " ORDER BY " + key + " " + sortOrder);
			}
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
			logger.error("All computers : " + CMPTDAO_GET_FAILURE);
			throw new DAOException(e);
		} finally {
			Util.closeRessources(connection, preparedStatement, resultSet);
			daoFactory.removeConnection();
		}
		logger.info("All computers : " + CMPTDAO_FOUNDED);
		return listComp;
	}

	/* (non-Javadoc)
	 * @see com.excilys.computerdb.dao.IComputerDAO#getComputersSearched(java.lang.String)
	 */
	@Override
	public List<Computer> getComputersSearched(String criteria)
			throws SQLException {
		logger.info(criteria + " : " + CMPTDAO_SEARCH_STARTED);
		if (criteria == null || criteria.isEmpty()) {
			logger.info("criteria null : " + CMPTDAO_SEARCH_FAILURE);
			return null;
		}

		List<Computer> listComp = new ArrayList<>();
		Computer computer = null;
		ResultSet resultSet = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = daoFactory.getConnection();
			preparedStatement = connection
					.prepareStatement(SQL_SEARCH_COMPUTERS);
			preparedStatement.setString(1, "%" + criteria + "%");
			preparedStatement.setString(2, "%" + criteria + "%");
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
			logger.error(criteria + " : " + CMPTDAO_SEARCH_FAILURE);
			throw new DAOException(e);
		} finally {
			Util.closeRessources(connection, preparedStatement, resultSet);
			daoFactory.removeConnection();
		}
		logger.info("search : " + CMPTDAO_SEARCH_FOUNDED);
		return listComp;
	}

}
