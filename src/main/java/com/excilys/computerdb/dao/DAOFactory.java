/**
 * 
 */
package com.excilys.computerdb.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.excilys.computerdb.exception.DAOConfException;
import com.jolbox.bonecp.BoneCPDataSource;


/**
 * The Class RepositoryDAO.
 *
 * @author excilys This class instanciate our various DAO
 */
@Repository
public class DAOFactory implements IDAOFactory {

	@Autowired
	@Qualifier("datasource")
	private BoneCPDataSource connectionPool;
	
	private static final Logger logger = LoggerFactory.getLogger(DAOFactory.class);
	private static final String CONNECTION_NUMBER = "Connection number : ";

	private static int connectionNbr = 0;
	
	private final ThreadLocal<Connection> CONNECTION = new ThreadLocal<Connection>() {
		@Override
        protected Connection initialValue()
        {
            try {
            	Connection connection = connectionPool.getConnection();
            	logger.debug("création de la connection " + connection.toString());
				return connection;
			} catch (SQLException e) {
				throw new DAOConfException(e);
			}
        }
	};
    
    /* (non-Javadoc)
	 * @see com.excilys.computerdb.dao.IDAOFactory#getConnection()
	 */
    @Override
	public Connection getConnection () throws SQLException {
    	connectionNbr++;
    	logger.info(CONNECTION_NUMBER + connectionNbr);
    	return CONNECTION.get();
    }
    
    public void removeConnection() {
    	logger.info(CONNECTION.toString() + " closed");
    	CONNECTION.remove();
    }

}
