package com.excilys.computerdb.services;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.computerdb.dao.IComputerDAO;
import com.excilys.computerdb.model.Computer;

@Service
public class ComputerService implements IComputerService {
	
	@Autowired
	private IComputerDAO computerDAO;
	
	private static final Logger logger = LoggerFactory.getLogger(ComputerService.class);
	
	/* (non-Javadoc)
	 * @see com.excilys.computerdb.services.IComputerService#getComputers(java.lang.String, java.lang.String)
	 */
	@Override
	public List<Computer> getComputers(String key, String sortOrder) throws SQLException {
		logger.info("ComputerService.getComputers called");
		return computerDAO.getComputers(key, sortOrder);
	}
	
	/* (non-Javadoc)
	 * @see com.excilys.computerdb.services.IComputerService#getComputersSearched(java.lang.String)
	 */
	@Override
	public List<Computer> getComputersSearched(String criteria) throws SQLException {
		logger.info("ComputerService.getComputersSearched called");
		return computerDAO.getComputersSearched(criteria);
	}
	
	/* (non-Javadoc)
	 * @see com.excilys.computerdb.services.IComputerService#getComputerById(long)
	 */
	@Override
	public Computer getComputerById(long id) throws SQLException {
		logger.info("ComputerService.getComputerById called");
		return computerDAO.getComputerById(id);
	}
	
	/* (non-Javadoc)
	 * @see com.excilys.computerdb.services.IComputerService#getComputerByName(java.lang.String)
	 */
	@Override
	public Computer getComputerByName(String name) throws SQLException {
		logger.info("ComputerService.getComputerByName called");
		return computerDAO.getComputerByName(name);
	}
	
	/* (non-Javadoc)
	 * @see com.excilys.computerdb.services.IComputerService#setComputer(com.excilys.computerdb.model.Computer)
	 */
	@Override
	public void setComputer(Computer computer) throws SQLException {
		logger.info("ComputerService.setComputer called");
		computerDAO.addComputer(computer);
	}
	
	/* (non-Javadoc)
	 * @see com.excilys.computerdb.services.IComputerService#deleteComputer(long)
	 */
	@Override
	public void deleteComputer(long computerId) throws SQLException {
		logger.info("ComputerService.deleteComputer called");
		computerDAO.deleteComputer(computerId);
	}
	
	/* (non-Javadoc)
	 * @see com.excilys.computerdb.services.IComputerService#updateComputer(com.excilys.computerdb.model.Computer)
	 */
	@Override
	public void updateComputer(Computer computer) throws SQLException {
		logger.info("ComputerService.updateComputer called");
		computerDAO.updateComputer(computer);
	}

}
