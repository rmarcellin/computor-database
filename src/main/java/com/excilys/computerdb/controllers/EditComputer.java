package com.excilys.computerdb.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
import com.excilys.computerdb.services.*;
import com.excilys.computerdb.utils.Util;

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
	@Autowired
	private Validator computerValidator;
	
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
			@ModelAttribute("computerForm") final ComputerDTO computerDTO) {
		logger.info(EDIT_COMPUTER_SERVLET_CALLED);

		List<CompanyDTO> companiesDTO = allCompanies();
		model.addAttribute("companies", companiesDTO);
		model.addAttribute("computerDTO", computerDTO);

		logger.info("doGet " + DO_GET_POST_SUCCEDED);
		return "editComputer";
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@RequestMapping(method = RequestMethod.POST)
	protected String doPost(ModelMap model,
			@ModelAttribute("computerForm")
			@Valid final ComputerDTO computerDTO,
			BindingResult bindingResult) {
		logger.info("doPost " + DO_GET_POST_STARTED);
		
		logger.info(computerDTO.getIntroduced());
		
		if (bindingResult.hasErrors()) {
			logger.error("EditComputer.doPost " + DO_GET_POST_FORM_VALIDATION_PROBLEM);
			List<CompanyDTO> companiesDTO = allCompanies();
			model.addAttribute("companies", companiesDTO);
			model.addAttribute("computerDTO", computerDTO);
			return "editComputer";
		}
		
		Computer computer = Util.fromDTOToComputer(computerDTO);
		try {
			computerService.setComputer(computer);
		} catch (SQLException e) {
			logger.error("doPost " + DO_POST_EDIT_FAILURE);
			return "redirect:404";
		}
		logger.info("EditComputer.doPost " + DO_GET_POST_SUCCEDED);
		return "redirect:Dashboard";

	}
	
///////////////////// UTILS /////////////////////////
	private List<CompanyDTO> allCompanies () {
		List<Company> listCompany = null;
		try {
			listCompany = companyService.getCompanies();
		} catch (SQLException e) {
			logger.error("EditComputer.doGet : Failed to get all companies");
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
