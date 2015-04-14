package com.excilys.computerdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
	private IDAOFactory daoFactory;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final Logger logger = LoggerFactory
			.getLogger(ComputerDAO.class);

	/**
	 * Instantiates a new computer dao.
	 *
	 * @param daoFactory
	 *            the repository dao
	 */
	@Autowired
	public ComputerDAO(DAOFactory daoFactory) {
		logger.info("ComputerDAO called");
		this.daoFactory = daoFactory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.excilys.computerdb.dao.IComputerDAO#deleteComputer(long)
	 */
	@Override
	public void deleteComputer(long compId) throws SQLException {
		logger.info("ComputerDAO.deleteComputer called - ComputerId : {}",
				compId);
		if (compId == 0) {
			logger.error("ComputerDAO.deleteComputer - Computer Id null");
			throw new IllegalArgumentException();
		}

		final String SQL_DELETE_COMPUTER = "DELETE FROM computer WHERE id = "
				+ compId;
		int status = jdbcTemplate.update(SQL_DELETE_COMPUTER);

		if (status == 0) {
			logger.error(
					"ComputerDAO.deleteComputer - Failed to delete computer : {}",
					compId);
			throw new DAOException("Failed to delete the computer");
		}

		// Removing the current connection from the ThreadLocal
		daoFactory.removeConnection();

		logger.info(
				"ComputerDAO.deleteComputer - Computer \"{}\" eleted successifuly",
				compId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.excilys.computerdb.dao.IComputerDAO#updateComputer(com.excilys.computerdb
	 * .model.Computer)
	 */
	@Override
	public void updateComputer(Computer computer) throws SQLException {
		logger.info("ComputerDAO.updateComputer : {} called",
				computer.getName());
		if (computer.getName() == null) {
			logger.error("ComputerDAO.updateComputer - computer name null");
			throw new IllegalArgumentException();
		}

		final String SQL_UPDATE_COMPUTER = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";

		int status = jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection
						.prepareStatement(SQL_UPDATE_COMPUTER);

				ps.setString(1, computer.getName());

				if (computer.getIntroduced() != null) {
					Timestamp introduced = Util
							.getTimeStampFromLocalDate(computer.getIntroduced());
					ps.setTimestamp(2, introduced);
				} else {
					ps.setNull(2, Types.TIMESTAMP);
				}

				if (computer.getDiscontinued() != null) {
					Timestamp discontinued = Util
							.getTimeStampFromLocalDate(computer
									.getDiscontinued());
					ps.setTimestamp(3, discontinued);
				} else {
					ps.setNull(3, Types.TIMESTAMP);
				}

				if (computer.getCompanyId() != 0) {
					ps.setLong(4, computer.getCompanyId());
				} else {
					ps.setNull(4, Types.LONGVARCHAR);
				}

				ps.setLong(5, computer.getId());

				return ps;
			}
		});

		// Removing the current connection from the ThreadLocal
		daoFactory.removeConnection();

		if (status == 0) {
			logger.error(
					"ComputerDAO.updateComputer : {} - Failed [DAOException]",
					computer.getName());
			throw new DAOException("Failed to update computer" + computer);
		}

		logger.info("ComputerDAO.updateComputer \"{}\" updated successifuly",
				computer.getName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.excilys.computerdb.dao.IComputerDAO#addComputer(com.excilys.computerdb
	 * .model.Computer)
	 */
	@Override
	public void addComputer(Computer comp) throws SQLException {
		logger.info("ComputerDAO.addComputer called - Computer name : "
				+ comp.getName());
		if (comp.getName() == null) {
			logger.error("ComputerDAO.addComputer : Computer name is null");
			throw new IllegalArgumentException();
		}

		String name = comp.getName().trim();
		if (name.isEmpty()) {
			logger.error("ComputerDAO.addComputer : Computer name empty");
			throw new IllegalArgumentException();
		}

		final String SQL_CREATE_COMPUTER = "INSERT INTO computer (name, introduced, discontinued, company_id) "
				+ "VALUES (?, ?, ?, ?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();
		int status = jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection
						.prepareStatement(SQL_CREATE_COMPUTER);

				ps.setString(1, name);

				if (comp.getIntroduced() != null) {
					Timestamp introduced = Util.getTimeStampFromLocalDate(comp
							.getIntroduced());
					ps.setTimestamp(2, introduced);
				} else {
					ps.setNull(2, Types.TIMESTAMP);
				}

				if (comp.getDiscontinued() != null) {
					Timestamp discontinued = Util
							.getTimeStampFromLocalDate(comp.getDiscontinued());
					ps.setTimestamp(3, discontinued);
				} else {
					ps.setNull(3, Types.TIMESTAMP);
				}

				if (comp.getCompanyId() != 0) {
					ps.setLong(4, comp.getCompanyId());
				} else {
					ps.setNull(4, Types.LONGVARCHAR);
				}

				return ps;
			}
		}, keyHolder);

		if (status == 0) {
			logger.error(
					"ComputerDAO.addComputer : {} - Failed [DAOException]",
					comp.getName());
			throw new DAOException("Failed to update computer" + comp);
		}

		comp.setId((long) keyHolder.getKey());

		logger.info(
				"ComputerDAO.addComputer - Computer : {} created successifuly - Generated Id : {}",
				comp.getName(), comp.getId());

		// Removing the current connection from the ThreadLocal
		daoFactory.removeConnection();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.excilys.computerdb.dao.IComputerDAO#getComputerById(long)
	 */
	@Override
	public Computer getComputerById(long criteria) throws SQLException {
		logger.info("ComputerDAO.getComputerById called - ComputerId : "
				+ criteria);
		if (criteria == 0) {
			logger.error("ComputerDAO.getComputerById : computerId null");
			throw new IllegalArgumentException();
		}

		final String SQL_SELECT_ONE_COMPUTER_BY_ID = "SELECT * FROM computer WHERE id = "
				+ criteria;
		Computer comp = jdbcTemplate.query(SQL_SELECT_ONE_COMPUTER_BY_ID,
				new ComputerMapper()).get(0);
		// Removing the current connection from the ThreadLocal
		daoFactory.removeConnection();
		// Logging success info
		logger.info("Computer \"{}\" retrieved successifuly", comp.getName());

		return comp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.excilys.computerdb.dao.IComputerDAO#getComputerByName(java.lang.String
	 * )
	 */
	@Override
	public Computer getComputerByName(String name) throws SQLException {
		logger.info("ComputerDAO.getComputerByName called - Argument : " + name);
		if (name == null) {
			logger.error("ComputerDAO.getComputerByName : Search criteria null");
			throw new IllegalArgumentException("Search criteria null");
		}

		String tmpName = name.trim();
		if (tmpName.isEmpty()) {
			logger.error("ComputerDAO.getComputerByName : Search criteria empty");
			throw new IllegalArgumentException("Search criteria empty");
		}

		final String SQL_SELECT_ONE_COMPUTER_BY_NAME = "SELECT * FROM computer WHERE name = "
				+ tmpName;
		Computer computer = jdbcTemplate.query(SQL_SELECT_ONE_COMPUTER_BY_NAME,
				new ComputerMapper()).get(0);

		// Removing the current connection from the ThreadLocal
		daoFactory.removeConnection();

		// Logging success info
		logger.info("Computer \"{}\" retrieved successifuly",
				computer.getName());

		return computer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.excilys.computerdb.dao.IComputerDAO#getComputers(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<Computer> getComputers(String key, String sortOrder)
			throws SQLException {
		logger.info("ComputerDAO.getComputers called - Arguments : {}, {}",
				key, sortOrder);
		List<Computer> listComp = null;

		if (key == null || sortOrder == null) {
			final String SQL_SELECT_ALL_COMPUTERS = "SELECT * FROM computer";
			listComp = jdbcTemplate.query(SQL_SELECT_ALL_COMPUTERS,
					new ComputerMapper());
		} else {
			final String SQL_SELECT_ALL_COMPUTERS_SORTED = "SELECT * FROM computer ORDER BY "
					+ key + " " + sortOrder;
			listComp = jdbcTemplate.query(SQL_SELECT_ALL_COMPUTERS_SORTED,
					new ComputerMapper());
		}
		// Removing the current connection from the ThreadLocal
		daoFactory.removeConnection();

		// Logging success info
		logger.info("All computers retrieved successifuly");

		return listComp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.excilys.computerdb.dao.IComputerDAO#getComputersSearched(java.lang
	 * .String)
	 */
	@Override
	public List<Computer> getComputersSearched(String criteria)
			throws SQLException {
		logger.info("ComputerDAO.getComputersSearched called - Crteria : {}",
				criteria);
		if (criteria == null || criteria.isEmpty()) {
			logger.info("ComputerDAO.getComputersSearched - Criteria null");
			return null;
		}
		List<Computer> listComp = new ArrayList<>();

		final String SQL_SEARCH_COMPUTERS = "SELECT * FROM computer LEFT OUTER JOIN company "
				+ "ON computer.company_id = company.id WHERE UCASE(computer.name) "
				+ "LIKE ? or UCASE(company.name) LIKE ?";

		listComp = jdbcTemplate.query(SQL_SEARCH_COMPUTERS, new Object[] {
				"%" + criteria + "%", "%" + criteria + "%" },
				new ComputerMapper());

		// Removing the current connection from the ThreadLocal
		daoFactory.removeConnection();

		logger.info("ComputerDAO.getComputersSearched - All computers retrieved successifuly");

		return listComp;
	}

}
