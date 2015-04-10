package com.excilys.computerdb.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.computerdb.dto.*;
import com.excilys.computerdb.model.*;
import com.excilys.computerdb.services.*;
import com.excilys.computerdb.utils.Util;
import com.excilys.computerdb.validators.ComputerValidator;

/**
 * Servlet implementation class AddComputer
 */
@WebServlet(description = "Adds a computer into database", urlPatterns = { "/AddComputer" })
public class AddComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ICompanyService companyService;
	@Autowired
	private IComputerService computerService;
	
	/**
	 * Logger initialisation
	 */
	private static final Logger logger = LoggerFactory.getLogger(AddComputer.class);
	
	/**
	 * INITIALISATION
	 */
	private static final String INITIALISATION = "AddComputer servlet called";
	
	/**
	 * doGet / doPost
	 */
	private static final String DO_GET_POST_STARTED = "method called";
	private static final String DO_GET_POST_CONTEXT_FAILURE = "Context ~ Request dispatcher failure";
	private static final String DO_GET_POST_SUCCEDED = "method succeded";
	
	/**
	 * doGet
	 */
	
	
	/**
	 * doPost
	 */
	//private static final String DO_POST_

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddComputer() {
		super();
		logger.info(INITIALISATION);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.info("doGet " + DO_GET_POST_STARTED);
		ServletContext scxt = this.getServletContext();
		// In the form, the user will have a dropdown list of all the companies
		// present in the database
		List<Company> listCompany = null;
		try {
			listCompany = companyService.getCompanies();
		} catch (SQLException e) {
			logger.error("doGet " + DO_GET_POST_CONTEXT_FAILURE);
			scxt.getRequestDispatcher("/WEB-INF/views/404.html").forward(
					request, response);
		}
		List<CompanyDTO> companiesDTO = new ArrayList<>();
		for (Company comp : listCompany) {
			companiesDTO.add(Util.fromCompanyToDTO(comp));
		}
		request.setAttribute("companies", companiesDTO);
		logger.info("doGet " + DO_GET_POST_SUCCEDED);
		scxt.getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(
				request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.info("doPost " + DO_GET_POST_STARTED);
		// Getting the DTO from user's form
		ComputerDTO c = Util.getComputerDTOFromHttpServlet(request);

		// Fabricating a computer service which is going to add the computer
		// to the database using a computerDAO
		if (ComputerValidator.isValide(c)) {
			Computer computer = Util.fromDTOToComputer(c);
			try {
				computerService.setComputer(computer);
			} catch (SQLException e) {
				logger.error("doPost " + DO_GET_POST_CONTEXT_FAILURE);
				this.getServletContext()
						.getRequestDispatcher("/WEB-INF/views/404.html")
						.forward(request, response);
			}
			response.sendRedirect("Dashboard");
			logger.info("doPost " + DO_GET_POST_SUCCEDED);
		} else {
			logger.error("doGPost " + DO_GET_POST_CONTEXT_FAILURE);
			this.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/404.html")
					.forward(request, response);
		}

	}

}
