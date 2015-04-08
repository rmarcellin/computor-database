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

import com.excilys.computerdb.dto.*;
import com.excilys.computerdb.services.*;
import com.excilys.computerdb.utils.Util;
import com.excilys.computerdb.validators.ComputerValidator;
import com.excilys.computerdb.beans.*;

/**
 * Servlet implementation class AddComputer
 */
@WebServlet(description = "Adds a computer into database", urlPatterns = { "/AddComputer" })
public class AddComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddComputer() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
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
		scxt.getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(
				request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// Getting the DTO from user's form
		ComputerDTO c = Util.getComputerDTOFromHttpServlet(request);

		// Fabricating a computer service which is going to add the computer
		// to the database using a computerDAO
		if (ComputerValidator.isValide(c)) {
			Computer computer = Util.fromDTOToComputer(c);
			ComputerService compService = new ComputerService();
			try {
				compService.setComputer(computer);
			} catch (SQLException e) {
				// TODO Add to log and then throw a 404 http error
				this.getServletContext()
						.getRequestDispatcher("/WEB-INF/views/404.html")
						.forward(request, response);
			}
			response.sendRedirect("AllComputers");
		} else {
			// TODO Log the error
			this.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/404.html")
					.forward(request, response);
		}

	}

}
