package com.excilys.computerdb.servlets;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Servlet implementation class SpringHttpServlet
 */
@WebServlet("/SpringHttpServlet")
public class SpringHttpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		try {
			super.init(config);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	    SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

}
