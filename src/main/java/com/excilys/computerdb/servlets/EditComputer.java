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
import com.excilys.computerdb.dto.CompanyDTO;
import com.excilys.computerdb.dto.ComputerDTO;
import com.excilys.computerdb.model.Company;
import com.excilys.computerdb.model.Computer;
import com.excilys.computerdb.services.*;
import com.excilys.computerdb.utils.Util;
import com.excilys.computerdb.validators.ComputerValidator;

/**
 * Servlet implementation class EditComputer
 */
@WebServlet("/EditComputer")
public class EditComputer extends SpringHttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	@Autowired
	private ICompanyService companyService;
	@Autowired
	private IComputerService computerService;
	
	private static final Logger logger = LoggerFactory.getLogger(AddComputer.class);
	/**
	 * INITIALISATION
	 */
	private static final String INITIALISATION = "AddComputer servlet called";
	
	/**
	 * doGet / doPost
	 */
	private static final String DO_GET_POST_STARTED = "method called";
	private static final String DO_GET_POST_SUCCEDED = "method succeded";
	private static final String DO_GET_POST_DISPLAY_COMPANIES = "Retrieving companies to be displayed";
	private static final String DO_POST_EDIT_FAILURE = "edit problem occured";
	private static final String DO_GET_POST_FORM_VALIDATION_PROBLEM = "form validation problem";
	
	private static final String EDIT_COMPUTER_SERVLET_CALLED = 
			"Edit computer servlet called";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditComputer() {
		super();
		logger.info(INITIALISATION);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.info(EDIT_COMPUTER_SERVLET_CALLED);
		ServletContext scxt = this.getServletContext();

		// In the form, the user will have a dropdown list of all the companies
		// present in the database
		List<Company> listCompany = null;
		try {
			logger.info("doGet " + DO_GET_POST_DISPLAY_COMPANIES);
			listCompany = companyService.getCompanies();
		} catch (SQLException e) {
			logger.error("doGet " + DO_POST_EDIT_FAILURE);
			scxt.getRequestDispatcher("/WEB-INF/views/404.html").forward(
					request, response);
		}
		List<CompanyDTO> companiesDTO = new ArrayList<>();
		for (Company comp : listCompany) {
			companiesDTO.add(Util.fromCompanyToDTO(comp));
		}
		request.setAttribute("companies", companiesDTO);

		// /////////////////////////////
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String intro = request.getParameter("intro");
		String disco = request.getParameter("disco");
		String companyN = request.getParameter("companyName");

		request.setAttribute("id", id);
		request.setAttribute("name", name);
		request.setAttribute("intro", intro);
		request.setAttribute("disco", disco);
		request.setAttribute("companyName", companyN);

		scxt.getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(
				request, response);
		logger.info("doGet " + DO_GET_POST_SUCCEDED);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.info("doPost " + DO_GET_POST_STARTED);
		ComputerDTO dto = Util.getComputerDTOFromHttpServlet(request);
		if (ComputerValidator.isValide(dto)) {
			Computer computerBean = Util.fromDTOToComputer(dto);
			try {
				computerService.updateComputer(computerBean);
			} catch (SQLException e) {
				logger.error("doPost " + DO_POST_EDIT_FAILURE);
				this.getServletContext()
						.getRequestDispatcher("/WEB-INF/views/404.html")
						.forward(request, response);
				return;
			}
			response.sendRedirect("Dashboard");
			logger.info("doPost " + DO_GET_POST_SUCCEDED);
		} else {
			logger.error("doPost " + DO_GET_POST_FORM_VALIDATION_PROBLEM);
			this.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/404.html")
					.forward(request, response);
		}

	}

}
