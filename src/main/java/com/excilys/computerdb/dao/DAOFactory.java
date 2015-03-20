/**
 * 
 */
package com.excilys.computerdb.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.excilys.computerdb.exception.DAOConfException;


// TODO: Auto-generated Javadoc
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
	
	/** The url. */
	private String url;
    
    /** The login. */
    private String login;
    
    /** The passwd. */
    private String passwd;
    
	/**
	 * Instantiates a new repository dao.
	 *
	 * @param url : mysql db url
	 * @param login : login to access the db
	 * @param passwd : password to access the db
	 */
	private DAOFactory(String url, String login, String passwd) {
		this.url = url;
		this.login = login;
		this.passwd = passwd;
	}
    
    /**
     * Gets the single instance of RepositoryDAO.
     *
     * @return single instance of RepositoryDAO
     * @throws DAOConfException the DAO conf exception
     */
    public static DAOFactory getInstance() throws DAOConfException {
    	String url, login, passwd, driver;
    	Properties properties = new Properties();
    	
    	ClassLoader cl = Thread.currentThread().getContextClassLoader();
    	InputStream propertiesFile = cl.getResourceAsStream(PROPERTIES_FILE);
    	
    	if (propertiesFile == null) {
    		throw new DAOConfException("Can't find " + PROPERTIES_FILE + " file");
    	}
    	
    	try {
    		properties.load(propertiesFile);
    		url = properties.getProperty(PROPERTY_URL);
    		login = properties.getProperty(PROPERTY_LOGIN);
    		passwd = properties.getProperty(PROPERTY_PASSWD);
    		driver = properties.getProperty(PROPERTY_DRIVER);
    	} catch (IOException e) {
    		throw new DAOConfException
    				("Can't load " + PROPERTIES_FILE + " properties file");
    	}
    	
    	try {
    		Class.forName(driver);
    	} catch (ClassNotFoundException e) {
    		throw new DAOConfException
    				("Can't load file properties " + PROPERTIES_FILE, e);
    	}
    	
    	DAOFactory factory = new DAOFactory(url, login, passwd);
    	return factory;
    }
    
    /**
     * Gets the connection.
     *
     * @return the connection
     * @throws SQLException the SQL exception
     */
    Connection getConnection () throws SQLException {
    	return DriverManager.getConnection(url, login, passwd);
    }
    
    // TODO Construct getters for my DAOs
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
