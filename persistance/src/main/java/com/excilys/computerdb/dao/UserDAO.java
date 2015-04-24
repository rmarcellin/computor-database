package com.excilys.computerdb.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdb.model.QUser;
import com.excilys.computerdb.model.User;
import com.mysema.query.jpa.hibernate.HibernateQuery;

@Repository
@Transactional
public class UserDAO implements IUserDAO, UserDetailsService {

	@Autowired
	private SessionFactory sessionFactory;

	private static final Logger logger = LoggerFactory.getLogger(User.class);

	public UserDAO() {
		super();
		logger.info("UserDAO called");
	}

	@Override
	public User getUserByName(String name) {
		logger.info("UserDAO.getUsersById called");
		if (name == null) {
			logger.error("UserDAO.getUserById : userName null");
			throw new IllegalArgumentException();
		}
		String defName = name.trim();
		if (defName.isEmpty()) {
			logger.error("UserDAO.getUserById : userName empty");
			throw new IllegalArgumentException();
		}
		QUser user = QUser.user;
		Session session = sessionFactory.getCurrentSession();

		// System.out.println();
		
		return new HibernateQuery(session).from(user).where(user.login.eq(defName))
				.singleResult(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException { 
		User u = getUserByName(username);
		List<GrantedAuthority> listAuthoritie = new ArrayList<GrantedAuthority>(1);
		listAuthoritie.add(new SimpleGrantedAuthority(u.getRole()));
		
		return new UserDetails() {

			private static final long serialVersionUID = 5181329383980664841L;

			@Override
			public boolean isEnabled() {
				return true;
			}
			
			@Override
			public boolean isCredentialsNonExpired() {
				return true;
			}
			
			@Override
			public boolean isAccountNonLocked() {
				return true;
			}
			
			@Override
			public boolean isAccountNonExpired() {
				return true;
			}
			
			@Override
			public String getUsername() {
				return "jean@dupond";
			}
			
			@Override
			public String getPassword() {
				return "myname";
			}
			
			@Override
			public Collection<GrantedAuthority> getAuthorities() {
				return listAuthoritie;
			}
		};
	}

}
