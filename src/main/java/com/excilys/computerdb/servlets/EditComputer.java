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

import com.excilys.computerdb.beans.Company;
import com.excilys.computerdb.beans.Computer;
import com.excilys.computerdb.dto.CompanyDTO;
import com.excilys.computerdb.dto.ComputerDTO;
import com.excilys.computerdb.services.*;
import com.excilys.computerdb.utils.Util;
import com.excilys.computerdb.validators.ComputerValidator;

/**
 * Servlet implementation class EditComputer
 */
@WebServlet("/EditComputer")
public class EditComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger(EditComputer.class);
	private static final String EDIT_COMPUTER_SERVLET_CALLED = 
			"Edit computer servlet called";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditComputer() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.info(EDIT_COMPUTER_SERVLET_CALLED);
		// TODO Validate before saving computer into database
		// TODO Log errors if any
		ServletContext scxt = this.getServletContext();

		// In the form, the user will have a dropdown list of all the companies
		// present in the database
		CompanyService cs = new CompanyService();
		List<Company> listCompany = null;
		try {
			listCompany = cs.getCompanies();
		} catch (SQLException e) {
			// At this point, a log will be done
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ComputerDTO dto = Util.getComputerDTOFromHttpServlet(request);
		if (ComputerValidator.isValide(dto)) {
			Computer computerBean = Util.fromDTOToComputer(dto);
			ComputerService compService = new ComputerService();
			try {
				compService.updateComputer(computerBean);
			} catch (SQLException e) {
				this.getServletContext()
						.getRequestDispatcher("/WEB-INF/views/404.html")
						.forward(request, response);
			}
			response.sendRedirect("Dashboard");
		} else {
			// TODO Log the error
			this.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/404.html")
					.forward(request, response);
		}

	}

}
