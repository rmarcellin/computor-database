package com.excilys.computerdb.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.computerdb.exception.DAOException;
import com.excilys.computerdb.services.IComputerService;

/**
 * Servlet implementation class DeleteComputer
 */
@Controller
@RequestMapping("/DeleteComputer")
public class DeleteComputer {

	@Autowired
	IComputerService computerService;

	private static final Logger logger = LoggerFactory
			.getLogger(DeleteComputer.class);
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@RequestMapping(method = RequestMethod.POST)
	protected String doPost(
			@RequestParam(value = "selection") final String selection)
			throws ServletException, IOException {
		logger.info("doPost " + DO_GET_POST_STARTED);
		try {
			String[] ids = selection.split(",");
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
		logger.info("doPost " + DO_GET_POST_SUCCEDED);
		return "redirect:Dashboard";
	}

}
