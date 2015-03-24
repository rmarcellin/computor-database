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

import com.excilys.computerdb.beans.Computer;
import com.excilys.computerdb.dto.ComputerDTO;
import com.excilys.computerdb.services.ComputerService;
import com.excilys.computerdb.ui.Page;
import com.excilys.computerdb.utils.Util;

/**
 * Servlet implementation class AllComputers
 */
@WebServlet(description = "Gets all computers from database", urlPatterns = { "/AllComputers" })
public class AllComputers extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AllComputers() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ServletContext ctx = this.getServletContext();
		List<Computer> computers = null;
		ComputerService cs = new ComputerService();
		try {
			computers = cs.getComputers();
		} catch (SQLException e) {
			// e.printStackTrace();
			ctx.getRequestDispatcher("/WEB-INF/views/404.html").forward(
					request, response);
		}
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

		Page<ComputerDTO> page = new Page<ComputerDTO>(computersDTO,
				defaultPageNum, defaultPageNumFooter);
		request.setAttribute("page", page);
		ctx.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(
				request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
