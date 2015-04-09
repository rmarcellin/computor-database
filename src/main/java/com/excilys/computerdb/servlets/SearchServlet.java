package com.excilys.computerdb.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
import com.excilys.computerdb.exception.*;

/**
 * Servlet implementation class SearchServlet
 */
@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger(SearchServlet.class);
	/**
	 * INITIALISATION
	 */
	private static final String INITIALISATION = "AddComputer servlet called";
	
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
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.info("doGet " + DO_GET_POST_STARTED);
		List<String> errors = new ArrayList<String>();
		String criteria = request.getParameter("search");
		if (criteria != null) {
			String trimedCriteria = criteria.trim();
			if (!trimedCriteria.isEmpty()) {
				ComputerService cs = new ComputerService();
				List<Computer> listComp = null;
				try {
					listComp = cs.getComputersSearched(trimedCriteria);
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
				request.setAttribute(MY_PAGE, page);

				this.getServletContext()
						.getRequestDispatcher("/WEB-INF/views/dashboard.jsp")
						.forward(request, response);
				logger.info("doGet " + DO_GET_POST_SUCCEDED);
				return;
			}
		}
		logger.error("doGet " + SEARCH_CRITERIA_EMPTY);
		errors.add(SEARCH_CRITERIA_EMPTY);
		request.setAttribute("errors", errors);
		request.setAttribute("errorsNbr", errors.size());
		this.getServletContext()
				.getRequestDispatcher("/WEB-INF/views/dashboard.jsp")
				.forward(request, response);

	}

}
