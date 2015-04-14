package com.excilys.computerdb.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
@Controller
@RequestMapping("/EditComputer")
public class EditComputer {

	@Autowired
	private ICompanyService companyService;
	@Autowired
	private IComputerService computerService;

	private static final Logger logger = LoggerFactory
			.getLogger(AddComputer.class);
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

	private static final String EDIT_COMPUTER_SERVLET_CALLED = "Edit computer servlet called";

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
	@RequestMapping(method = RequestMethod.GET)
	protected String doGet(
			ModelMap model,
			@RequestParam(value = "id", required = false) final long id,
			@RequestParam(value = "name", required = true) final String name,
			@RequestParam(value = "intro", required = false) final String intro,
			@RequestParam(value = "disco", required = false) final String disco,
			@RequestParam(value = "companyName", required = false) final String companyName)
			throws ServletException, IOException {
		logger.info(EDIT_COMPUTER_SERVLET_CALLED);

		// In the form, the user will have a dropdown list of all the companies
		// present in the database
		List<Company> listCompany = null;
		try {
			logger.info("doGet " + DO_GET_POST_DISPLAY_COMPANIES);
			listCompany = companyService.getCompanies();
		} catch (SQLException e) {
			logger.error("doGet " + DO_POST_EDIT_FAILURE);
			return "redirect:404";
		}
		List<CompanyDTO> companiesDTO = new ArrayList<>();
		for (Company comp : listCompany) {
			companiesDTO.add(Util.fromCompanyToDTO(comp));
		}
		model.addAttribute("companies", companiesDTO);

		// /////////////////////////////
		model.addAttribute("id", id);
		model.addAttribute("name", name);
		model.addAttribute("intro", intro);
		model.addAttribute("disco", disco);
		model.addAttribute("companyName", companyName);

		logger.info("doGet " + DO_GET_POST_SUCCEDED);
		return "editComputer";
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@RequestMapping(method = RequestMethod.POST)
	protected String doPost(ModelMap model,
			@ModelAttribute("computerForm") final ComputerDTO computerDTO)
			throws ServletException, IOException {
		logger.info("doPost " + DO_GET_POST_STARTED);
		
		if (ComputerValidator.isValide(computerDTO)) {
			Computer computer = Util.fromDTOToComputer(computerDTO);
			try {
				computerService.updateComputer(computer);
			} catch (SQLException e) {
				logger.error("doPost " + DO_POST_EDIT_FAILURE);
				return "redirect:404";
			}
			logger.info("doPost " + DO_GET_POST_SUCCEDED);
			return "redirect:Dashboard";
			
		} else {
			logger.error("doPost " + DO_GET_POST_FORM_VALIDATION_PROBLEM);
			return "redirect:404";
		}

	}

}
