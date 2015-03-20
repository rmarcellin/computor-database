package com.excilys.computerdb.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdb.beans.Computer;
import com.excilys.computerdb.services.ComputerService;

/**
 * Servlet implementation class AllComputers
 */
@WebServlet(description = "Gets all computers from database", 
			urlPatterns = { "/AllComputers" })
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		ServletContext ctx = this.getServletContext();
		List<Computer> computers = null;
		ComputerService cs = new ComputerService();
		try {
			computers = cs.getComputers();
		} catch (SQLException e) {
			//e.printStackTrace();
			ctx.getRequestDispatcher("/WEB-INF/views/404.html").forward( request, response );
		}
		request.setAttribute("computers", computers);		
		ctx.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward( request, response );		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
