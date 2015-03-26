package com.excilys.computerdb.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdb.services.ComputerService;
import com.excilys.computerdb.exception.*;

/**
 * Servlet implementation class DeleteComputer
 */
@WebServlet("/DeleteComputer")
public class DeleteComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteComputer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("selection");
		ComputerService computerService = new ComputerService();
		try {
			String[] ids = id.split(",");
			for (String oneId : ids) {
				computerService.deleteComputer(Long.parseLong(oneId));
			}
		} catch (NumberFormatException e) {
			// TODO Log the exception
			throw new NumberFormatException();
		} catch (SQLException e) {
			// TODO Log the exception
			throw new DAOException();
		}		
		response.sendRedirect("/computer-database/AllComputers");
	}

}
