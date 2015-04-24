/**
 * 
 */
package com.excilys.computerdb.dao;

import com.excilys.computerdb.model.User;

/**
 * @author excilys
 *
 */
public interface IUserDAO {
	
	User getUserByName (String name);

}
