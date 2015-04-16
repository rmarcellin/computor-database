package com.excilys.computerdb.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.computerdb.dto.CompanyDTO;
import com.excilys.computerdb.dto.ComputerDTO;
import com.excilys.computerdb.model.Company;
import com.excilys.computerdb.model.Computer;
import com.excilys.computerdb.services.ICompanyService;
import com.excilys.computerdb.services.IComputerService;
import com.excilys.computerdb.utils.Util;

/**
 * Servlet implementation class AddComputer
 */
@Controller
@RequestMapping("/AddComputer")
public class AddComputer {

	@Autowired
	private ICompanyService companyService;
	@Autowired
	private IComputerService computerService;
	@Autowired
	private Validator computerValidator;

	/**
	 * Logger initialisation
	 */
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
	private static final String DO_GET_POST_CONTEXT_FAILURE = "Context ~ Request dispatcher failure";
	private static final String DO_GET_POST_SUCCEDED = "method succeded";

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
	@RequestMapping(method = RequestMethod.GET)
	protected String doGet(Model model) throws ServletException, IOException {
		logger.info("doGet " + DO_GET_POST_STARTED);
		// In the form, the user will have a dropdown list of all the companies
		// present in the database
		List<CompanyDTO> companiesDTO = allCompanies();
		model.addAttribute("companies", companiesDTO);
		logger.info("doGet " + DO_GET_POST_SUCCEDED);
		return "addComputer";
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@RequestMapping(method = RequestMethod.POST)
	protected String doPost(Model model,
			@ModelAttribute("computerForm") 
			@Valid final ComputerDTO computerDTO,
			BindingResult bindingResult) {
		logger.info("AddComputer.doPost " + DO_GET_POST_STARTED);
		
		if (bindingResult.hasErrors()) {
			logger.error("AddComputer.doPost : errors in form");
			List<CompanyDTO> companiesDTO = allCompanies();
			model.addAttribute("companies", companiesDTO);
			return "addComputer";
		}
		// Fabricating a computer service which is going to add the computer
		// to the database using a computerDAO
		Computer computer = Util.fromDTOToComputer(computerDTO);
		try {
			computerService.setComputer(computer);
		} catch (SQLException e) {
			logger.error("AddComputer.doPost "
					+ DO_GET_POST_CONTEXT_FAILURE);
			return "redirect:404";
		}
		logger.info("AddComputer.doPost " + DO_GET_POST_SUCCEDED);
		return "redirect:Dashboard";

	}
	
	///////////////////// UTILS /////////////////////////
	private List<CompanyDTO> allCompanies () {
		List<Company> listCompany = null;
		try {
			listCompany = companyService.getCompanies();
		} catch (SQLException e) {
			logger.error("doGet " + DO_GET_POST_CONTEXT_FAILURE);
		}
		List<CompanyDTO> companiesDTO = new ArrayList<>();
		for (Company comp : listCompany) {
			companiesDTO.add(Util.fromCompanyToDTO(comp));
		}
		return companiesDTO;
	}
	
	@InitBinder
	private void initBinder (WebDataBinder binder) {
		binder.setValidator(computerValidator);
	}

}
