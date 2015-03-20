package com.excilys.computerdb.dao;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class CompanyDAOTest {
	static final String PROPERTIES_FILE = "dao.properties";
	InputStream inputStreamMock;
	ClassLoader classLoader;
	Properties propertiesMock;

	@Before
	public void setUp() throws Exception {
		propertiesMock = Mockito.mock(Properties.class);
		//Mockito.when(propertiesMock.load(inputStreamMock)).
	}

	@Test
	public final void testCompanyDAO() {
		fail("Not yet implemented");
	}

}
