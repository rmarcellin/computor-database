package com.excilys.computerdb.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.excilys.computerdb.model.Computer;
import com.excilys.computerdb.services.IComputerService;
import com.excilys.computerdb.ui.Page;
import com.excilys.computerdb.utils.Util;

/**
 * Servlet implementation class Dashboard
 */
@Controller
@RequestMapping("/Dashboard")
public class Dashboard {

	@Autowired
	private IComputerService computerService;

	private static int PAGE_SIZE = 10;
	private static final String MY_PAGE = "page";

	private static final Logger logger = LoggerFactory
			.getLogger(Dashboard.class);
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
	 * This map contains the state of JSP's table columns. Its values tell if
	 * the column corresponding to each key is sorted and in witch order.
	 */
	private static Map<String, String> sortKeyOrder = new HashMap<String, String>();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Dashboard() {
		super();
		logger.info(INITIALISATION);
		sortKeyOrder.put("name", "default");
		sortKeyOrder.put("introduced", "default");
		sortKeyOrder.put("discontinued", "default");
		sortKeyOrder.put("company_id", "default");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@RequestMapping(method = RequestMethod.GET)
	protected String doGet(ModelMap model,
			@RequestParam(value = "sortKey", required = false) String sortKey,
			@RequestParam(value = "pageNum", required = false) String pageNum,
			@RequestParam(value = "startP", required = false) String startP)
			throws ServletException, IOException {
		logger.info("doGet " + DO_GET_POST_STARTED);
		List<Computer> computers = null;
		try {
			// Getting the column to be sorted and the order in witch it must be
			// sorted
			// sortKey corresponds to the column to be sorted
			if (sortKey == null) {
				// Lets navigate through page footer keeping sorting order
				boolean orderNotFound = true;
				for (Map.Entry<String, String> entry : sortKeyOrder.entrySet()) {
					if (!entry.getValue().equals("default")) {
						computers = computerService.getComputers(
								entry.getKey(), entry.getValue());
						orderNotFound = false;
						break;
					}
				}
				if (orderNotFound) {
					computers = computerService.getComputers(null, null);
				}
			} else {
				String nextOrder = Util.getNextSortOrder(sortKeyOrder
						.get(sortKey));
				for (Map.Entry<String, String> entry : sortKeyOrder.entrySet()) {
					sortKeyOrder.replace(entry.getKey(), "default");
				}
				sortKeyOrder.replace(sortKey, nextOrder);
				computers = computerService.getComputers(sortKey, nextOrder);
				// request.setAttribute("sortKey", sortKey);
			}
		} catch (SQLException e) {
			logger.error("doGet " + DO_GET_POST_CONTEXT_FAILURE);
			return "404";
		}
		// At this point, the computers list is going to be converted to DTOs
		// list
		List<ComputerDTO> computersDTO = new ArrayList<ComputerDTO>();
		for (Computer comp : computers) {
			computersDTO.add(Util.fromComputerToDTO(comp));
		}
		int defaultPageNum = 1;
		int defaultPageNumFooter = 1;
		if (pageNum != null && !pageNum.isEmpty()) {
			defaultPageNum = Integer.parseInt(pageNum);
			defaultPageNumFooter = Integer.parseInt(startP);
		}

		// A page containing a list of computers is sent to the user.
		// The list will be shown
		Page<ComputerDTO> page = new Page<ComputerDTO>(computersDTO,
				defaultPageNum, defaultPageNumFooter, PAGE_SIZE);
		model.addAttribute(MY_PAGE, page);
		logger.info("doGet " + DO_GET_POST_SUCCEDED);
		return "dashboard";
	}

}
