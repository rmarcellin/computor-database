package test.java.com.excilys.computerdb.dao;

import static org.junit.Assert.assertNotNull;

import main.java.com.excilys.computerdb.dao.DAOFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DAOFactoryTest {
		
	DAOFactory daoFactory;

	@Before
	public void setUp() throws Exception {
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
}
