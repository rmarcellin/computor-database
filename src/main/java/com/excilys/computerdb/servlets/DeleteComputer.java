package com.excilys.computerdb.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdb.services.ComputerService;
import com.excilys.computerdb.exception.*;

/**
 * Servlet implementation class DeleteComputer
 */
@WebServlet("/DeleteComputer")
public class DeleteComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger(DeleteComputer.class);
	/**
	 * INITIALISATION
	 */
	private static final String INITIALISATION = "AddComputer servlet called";
	
	/**
	 * doGet / doPost
	 */
	private static final String DO_GET_POST_STARTED = "method called";
	private static final String DO_GET_POST_SUCCEDED = "method succeded";
	private static final String DO_POST_NBR_FORMAT_EXCEPTION = "Number format problem";
	private static final String DO_POST_DELETE_FAILURE = "deletion problem occured";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteComputer() {
        super();
        logger.info(INITIALISATION);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("doPost " + DO_GET_POST_STARTED);
		String id = request.getParameter("selection");
		ComputerService computerService = new ComputerService();
		try {
			String[] ids = id.split(",");
			for (String oneId : ids) {
				computerService.deleteComputer(Long.parseLong(oneId));
			}
		} catch (NumberFormatException e) {
			logger.error("doPost " + DO_POST_NBR_FORMAT_EXCEPTION);
			throw new NumberFormatException();
		} catch (SQLException e) {
			logger.error("doPost " + DO_POST_DELETE_FAILURE);
			throw new DAOException();
		}		
		response.sendRedirect("/computer-database/Dashboard");
		logger.info("doPost " + DO_GET_POST_SUCCEDED);
	}

}
