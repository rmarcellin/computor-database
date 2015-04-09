/**
 * 
 */
package com.excilys.computerdb.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdb.exception.DAOConfException;
import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;


/**
 * The Class RepositoryDAO.
 *
 * @author excilys This class instanciate our various DAO
 */
public class DAOFactory {

	/** The Constant PROPERTIES_FILE. */
	private static final String PROPERTIES_FILE = "dao.properties";
	
	/** The Constant PROPERTY_URL. */
	private static final String PROPERTY_URL = "url";
	
	/** The Constant PROPERTY_DRIVER. */
	private static final String PROPERTY_DRIVER = "driver";
	
	/** The Constant PROPERTY_LOGIN. */
	private static final String PROPERTY_LOGIN = "login";
	
	/** The Constant PROPERTY_PASSWD. */
	private static final String PROPERTY_PASSWD = "passwd";
	
	BoneCP connectionPool = null;
	
	private static final Logger logger = LoggerFactory.getLogger(DAOFactory.class);
	private static final String DAO_FACTORY_CALLED = "DAOFactory called";
	private static final String PROPERTIES_FILE_NOT_FOUND = "Can't find properties' file";
	private static final String CONNECTION_NUMBER = "Connection number : ";
	private static final String DATA_SOURCE_INIT = "Initialising datasource...";
	private static final String DATA_SOURCE_INITIALIZED = "Datasource initialized successifuly";
	private static final String DATA_SOURCE_FAILED = "Datasource initialisation failed";
	
	private static int connectionNbr = 0;
	    
	/**
	 * Instantiates a new repository dao.
	 */
	private DAOFactory(BoneCP connectionPool) {
		this.connectionPool = connectionPool;
	}
    
    /**
     * Gets the single instance of RepositoryDAO.
     *
     * @return single instance of RepositoryDAO
     * @throws DAOConfException the DAO conf exception
     */
    public static DAOFactory getInstance() throws DAOConfException {
    	logger.info(DAO_FACTORY_CALLED);
    	BoneCP connectionPool = null;
    	String url, login, passwd, driver;
    	int boneCPPartition, boneCPMinConnection, boneCPMaxConnection;
    	Properties properties = new Properties();
    	
    	ClassLoader cl = Thread.currentThread().getContextClassLoader();
    	InputStream propertiesFile = cl.getResourceAsStream(PROPERTIES_FILE);
    	
    	if (propertiesFile == null) {
    		logger.error(PROPERTIES_FILE_NOT_FOUND + " : " + PROPERTIES_FILE);
    		throw new DAOConfException("Can't find " + PROPERTIES_FILE + " file");
    	}
    	
    	try {
    		properties.load(propertiesFile);
    		url = properties.getProperty(PROPERTY_URL);
    		login = properties.getProperty(PROPERTY_LOGIN);
    		passwd = properties.getProperty(PROPERTY_PASSWD);
    		driver = properties.getProperty(PROPERTY_DRIVER);
    		boneCPPartition = Integer.parseInt(properties.getProperty("boneCP_partition"));
    		boneCPMinConnection = Integer.parseInt(properties.getProperty("boneCP_min_connection"));
    		boneCPMaxConnection = Integer.parseInt(properties.getProperty("boneCP_max_connection"));
    	} catch (IOException e) {
    		logger.error(PROPERTIES_FILE_NOT_FOUND + " : " + PROPERTIES_FILE);
    		throw new DAOConfException
    				("Can't load " + PROPERTIES_FILE + " properties file");
    	}
    	
    	try {
    		Class.forName(driver);
    	} catch (ClassNotFoundException e) {
    		logger.error(PROPERTIES_FILE_NOT_FOUND + " : " + PROPERTIES_FILE, e);
    		throw new DAOConfException
    				("Can't load file properties " + PROPERTIES_FILE, e);
    	}
    	
    	try {
    		logger.info(DATA_SOURCE_INIT);
			BoneCPConfig config = new BoneCPConfig();
			// Setting BoneCP login properties
			config.setJdbcUrl(url);
			config.setUsername(login);
			config.setPassword(passwd);
			
			// Setting connection pool size
			config.setPartitionCount(boneCPPartition);
			config.setMinConnectionsPerPartition(boneCPMinConnection);
			config.setMaxConnectionsPerPartition(boneCPMaxConnection);
			
			// Initialising BoneCP connection pool
			connectionPool = new BoneCP(config);
			logger.info(DATA_SOURCE_INITIALIZED);
		} catch (SQLException e) {
			logger.error(DATA_SOURCE_FAILED);
			throw new DAOConfException();
		}
    	
    	DAOFactory factory = new DAOFactory(connectionPool);
    	return factory;
    }
    
    /**
     * Gets the connection.
     *
     * @return the connection
     * @throws SQLException the SQL exception
     */
    Connection getConnection () throws SQLException {
    	connectionNbr++;
    	logger.info(CONNECTION_NUMBER + connectionNbr);
    	return connectionPool.getConnection();
    }
    
    /**
     * Gets the computer dao.
     *
     * @return the computer dao
     */
    public ComputerDAO getComputerDAO() {
    	return new ComputerDAO(this);
    }
    
    /**
     * Gets the company dao.
     *
     * @return the company dao
     */
    public CompanyDAO getCompanyDAO() {
    	return new CompanyDAO(this);
    }

}
