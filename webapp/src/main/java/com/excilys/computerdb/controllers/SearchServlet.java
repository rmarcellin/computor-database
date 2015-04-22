package com.excilys.computerdb.controllers;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.computerdb.dto.ComputerDTO;
import com.excilys.computerdb.exception.DAOException;
import com.excilys.computerdb.model.Computer;
import com.excilys.computerdb.services.IComputerService;
import com.excilys.computerdb.ui.Page;
import com.excilys.computerdb.utils.Util;

/**
 * Servlet implementation class SearchServlet
 */
@Controller
@RequestMapping("/SearchServlet")
public class SearchServlet {

	@Autowired
	private IComputerService computerService;

	private static final Logger logger = LoggerFactory
			.getLogger(SearchServlet.class);
	/**
	 * INITIALISATION
	 */
	private static final String INITIALISATION = "SearchServlet servlet called";

	/**
	 * doGet / doPost
	 */
	private static final String DO_GET_POST_STARTED = "method called";
	private static final String DO_GET_POST_SUCCEDED = "method succeded";
	private static final String DO_POST_SEARCH_FAILURE = "search problem occured";

	private static final String SEARCH_CRITERIA_EMPTY = "Search criteria should not be empty";
	private static final String MY_PAGE = "page";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchServlet() {
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
			@RequestParam(value = "search", required = true) final String criteria)
			throws ServletException, IOException {
		logger.info("doGet " + DO_GET_POST_STARTED);
		List<String> errors = new ArrayList<String>();
		if (criteria != null) {
			String trimedCriteria = criteria.trim();
			if (!trimedCriteria.isEmpty()) {
				List<Computer> listComp = null;
				try {
					listComp = computerService
							.getComputersSearched(trimedCriteria);
				} catch (SQLException e) {
					logger.error("doGet " + DO_POST_SEARCH_FAILURE);
					throw new DAOException("In search servlet");
				}

				List<ComputerDTO> computersDTO = new ArrayList<ComputerDTO>();
				for (Computer comp : listComp) {
					computersDTO.add(Util.fromComputerToDTO(comp));
				}

				// A page containing a list of computers is sent the user. The
				// list will be
				// shown
				int pageSize = computersDTO.size();
				Page<ComputerDTO> page = new Page<ComputerDTO>(computersDTO, 1,
						1, pageSize);
				model.addAttribute(MY_PAGE, page);

				logger.info("doGet " + DO_GET_POST_SUCCEDED);
				return "dashboard";
			}
		}
		logger.error("doGet " + SEARCH_CRITERIA_EMPTY);
		errors.add(SEARCH_CRITERIA_EMPTY);
		model.addAttribute("errors", errors);
		model.addAttribute("errorsNbr", errors.size());
		return "dashboard";

	}

}
