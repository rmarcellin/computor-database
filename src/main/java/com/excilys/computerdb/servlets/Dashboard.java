package com.excilys.computerdb.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdb.beans.Computer;
import com.excilys.computerdb.dto.ComputerDTO;
import com.excilys.computerdb.services.ComputerService;
import com.excilys.computerdb.ui.Page;
import com.excilys.computerdb.utils.Util;

/**
 * Servlet implementation class Dashboard
 */
@WebServlet(description = "Gets all computers from database", urlPatterns = { "/Dashboard" })
public class Dashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static int PAGE_SIZE = 10;
	private static final String MY_PAGE = "page";
	
	private static final Logger logger = LoggerFactory.getLogger(Dashboard.class);
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
	 * This map contains the state of JSP's table columns. Its values tell
	 * if the column corresponding to each key is sorted and in witch order.
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
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.info("doGet " + DO_GET_POST_STARTED);
		ServletContext ctx = this.getServletContext();
		List<Computer> computers = null;
		ComputerService cs = new ComputerService();
		try {
			// Getting the column to be sorted and the order in witch it must be sorted
			// sortKey corresponds to the column to be sorted
			String sortKey = request.getParameter("sortKey");
			if (sortKey == null) {
				// Lets navigate through page footer keeping sorting order
				boolean orderNotFound = true;
				for (Map.Entry<String, String> entry : sortKeyOrder.entrySet()) {
					if (!entry.getValue().equals("default")) {
						computers = cs.getComputers(entry.getKey(), entry.getValue());
						orderNotFound = false;
						break;
					}
				}
				if (orderNotFound) {
					computers = cs.getComputers(null, null);
				}
			} else {
				String nextOrder = Util.getNextSortOrder(sortKeyOrder.get(sortKey));
				for (Map.Entry<String, String> entry : sortKeyOrder.entrySet()) {
					sortKeyOrder.replace(entry.getKey(), "default");
				}
				sortKeyOrder.replace(sortKey, nextOrder);
				computers = cs.getComputers(sortKey, nextOrder);
				//request.setAttribute("sortKey", sortKey);
			}			
		} catch (SQLException e) {
			logger.error("doGet " + DO_GET_POST_CONTEXT_FAILURE);
			ctx.getRequestDispatcher("/WEB-INF/views/404.html").forward(
					request, response);
		}
		// At this point, the computers list is going to be converted to DTOs list
		List<ComputerDTO> computersDTO = new ArrayList<ComputerDTO>();
		for (Computer comp : computers) {
			computersDTO.add(Util.fromComputerToDTO(comp));
		}
		int defaultPageNum = 1;
		int defaultPageNumFooter = 1;
		String pageNumStr = request.getParameter("pageNum");
		String pageNumFooterStr = request.getParameter("startP");
		if (pageNumStr != null) {
			defaultPageNum = Integer.parseInt(pageNumStr);
			defaultPageNumFooter = Integer.parseInt(pageNumFooterStr);
		}

		// A page containing a list of computers is sent to the user. 
		// The list will be shown
		Page<ComputerDTO> page = new Page<ComputerDTO>(computersDTO,
				defaultPageNum, defaultPageNumFooter, PAGE_SIZE);
		request.setAttribute(MY_PAGE, page);
		ctx.getRequestDispatcher("/WEB-INF/views/dashboard.jsp")
			.forward(request, response);
		logger.info("doGet " + DO_GET_POST_SUCCEDED);
	}

}
