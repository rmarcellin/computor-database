package com.excilys.computerdb.dao;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DAOFactoryTest {
		
	@Autowired
	DAOFactory daoFactory;

	@Before
	public void setUp() throws Exception {
		//daoFactory = DAOFactory.getInstance();
	}
	
	@Test
	public void daoFactoryInstanceNotNull() {
		assertNotNull(daoFactory);
	}
	
	@Test
	public void singleDAOFactoryInstance() {
		//DAOFactory daoFactory2 = DAOFactory.getInstance();
		//Assert.assertNotEquals(daoFactory, daoFactory2);
	}
}
