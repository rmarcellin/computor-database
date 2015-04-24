package com.excilys.computerdb.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdb.exception.DAOException;
import com.excilys.computerdb.model.Computer;
import com.excilys.computerdb.model.QCompany;
import com.excilys.computerdb.model.QComputer;

import com.mysema.query.jpa.hibernate.HibernateDeleteClause;
import com.mysema.query.jpa.hibernate.HibernateQuery;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.path.PathBuilder;
import com.mysema.query.types.path.StringPath;

/**
 * The Class ComputerDAO.
 */
@Repository
@Transactional
public class ComputerDAO implements IComputerDAO {

	@Autowired
	private SessionFactory sessionFactory;

	private static final Logger logger = LoggerFactory
			.getLogger(ComputerDAO.class);

	public ComputerDAO() {
		super();
		logger.info("ComputerDAO called");
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

		QComputer compu = QComputer.computer;
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.getTransaction();
		tx.begin();
		long status = new HibernateDeleteClause(session, compu)
				.where(compu.id.eq(compId)).execute();
		tx.commit();

		if (status == 0) {
			logger.error(
					"ComputerDAO.deleteComputer - Failed to delete computer : {}",
					compId);
			throw new DAOException("Failed to delete the computer");
		}

		logger.info(
				"ComputerDAO.deleteComputer - Computer \"{}\" eleted successifuly",
				compId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.excilys.computerdb.dao.IComputerDAO#addComputer(com.excilys.computerdb
	 * .model.Computer)
	 */
	@Override
	public void addOrUpdateComputer(Computer computer) throws SQLException {
		logger.info("ComputerDAO.addComputer called - Computer name : "
				+ computer.getName());
		if (computer.getName() == null) {
			logger.error("ComputerDAO.addComputer : Computer name is null");
			throw new IllegalArgumentException();
		}
		String name = computer.getName().trim();
		if (name.isEmpty()) {
			logger.error("ComputerDAO.addComputer : Computer name empty");
			throw new IllegalArgumentException();
		}
		sessionFactory.getCurrentSession().saveOrUpdate(computer);
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

		QComputer compu = QComputer.computer;
		Session session = sessionFactory.getCurrentSession();
		HibernateQuery query = new HibernateQuery(session);

		Computer computer = query.from(compu).where(compu.id.eq(criteria))
				.uniqueResult(compu);
		// Logging success info
		logger.info("Computer \"{}\" retrieved successifuly",
				computer.getName());

		return computer;
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

		QComputer compu = QComputer.computer;
		Session session = sessionFactory.getCurrentSession();
		HibernateQuery query = new HibernateQuery(session);

		Computer computer = query.from(compu).where(compu.name.eq(tmpName))
				.uniqueResult(compu);

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

		QComputer compu = QComputer.computer;
		QCompany compa = QCompany.company;
		Session session = sessionFactory.getCurrentSession();
		HibernateQuery query = new HibernateQuery(session);
		List<Computer> listComp = null;
		logger.debug("coucou1");
		if (key == null || sortOrder == null) {
			listComp = query.from(compu).leftJoin(compu.company, compa)
					.list(compu);
		} else {
			PathBuilder<Computer> computerPath = new PathBuilder<Computer>(
					Computer.class, "computer");
			StringPath defKey = computerPath.getString(key.trim());
			OrderSpecifier<String> orderSpecifier = null;
			QComputer.computer.name.asc();
			if (sortOrder.equals("asc")) {
				orderSpecifier = defKey.asc();
			} else {
				orderSpecifier = defKey.desc();
			}
			listComp = query.from(compu).leftJoin(compu.company, compa)
					.orderBy(orderSpecifier).list(compu);
		}

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

		QComputer compu = QComputer.computer;
		QCompany compa = QCompany.company;
		Session session = sessionFactory.getCurrentSession();
		HibernateQuery query = new HibernateQuery(session);

		List<Computer> listComp = query
				.from(compu)
				.leftJoin(compu.company, compa)
				.where(compu.name.contains(criteria).or(
						compa.name.contains(criteria))).list(compu);
		logger.info("ComputerDAO.getComputersSearched - All computers retrieved successifuly");

		return listComp;
	}

}
