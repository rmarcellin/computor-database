package test.java.com.excilys.computerdb.dao;

import static org.junit.Assert.assertNotNull;

import java.io.InputStream;
import java.util.Properties;

import main.java.com.excilys.computerdb.dao.DAOFactory;
import main.java.com.excilys.computerdb.exception.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class DAOFactoryTest {
	
	private static final String PROPERTIES_FILE = "dao.properties";
	private static final String PROPERTY_URL = "url";	
	private static final String PROPERTY_DRIVER = "driver";
	private static final String PROPERTY_LOGIN = "login";
	private static final String PROPERTY_PASSWD = "passwd";
	
	private DAOFactory daoFactory;
	private Properties properties;
	private ClassLoader classLoaderMock;
	InputStream propertiesFile;

	@Before
	public void setUp() throws Exception {
		//propertiesFile = cl.getResourceAsStream(PROPERTIES_FILE);
		daoFactory = DAOFactory.getInstance();
	}
	
	@Test
	public void daoFactoryInstanceNotNull() {
		assertNotNull(daoFactory);
	}
	
	@Test
	public void singleDAOFactoryInstance() {
		DAOFactory daoFactory2 = DAOFactory.getInstance();
		Assert.assertNotEquals(daoFactory, daoFactory2);
	}
	
	@Test(expected = DAOConfException.class)
	public void getingDAOFactoryInstanceWithNoPropertiesFile() {
		
	}

}
