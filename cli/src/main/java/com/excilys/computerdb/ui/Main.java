package com.excilys.computerdb.ui;

import java.sql.SQLException;



public class Main {
	

	public static void main(String[] args) throws SQLException {
		//DAOFactory rep = DAOFactory.getInstance();
		//CompanyDAO companyDAO = rep.getCompanyDAO();
		//ComputerDAO computerDAO = rep.getComputerDAO();
		//List<Computer> listComputer = computerDAO.getComputers();
		//Page paging = new Page(listComputer);
		
		System.out.println("+---------------------------------------------------------------+");
		System.out.println("|     Pour interagir avec le CL-UI, suivez les instructions     |");
		System.out.println("|     1 => pour afficher les \"Companies\"                        |");
		System.out.println("|     2 => pour afficher les \"Computers\"                        |");
		System.out.println("|     3 => pour afficher les détails sur un computer            |");
		System.out.println("|     4 => pour ajouter un computer                             |");
		System.out.println("|     5 => pour modifier les informations d'un computer         |");
		System.out.println("|     6 => pour supprimer un computer                           |");
		System.out.println("|    /!\\ Pour les étapes 4, 5, et 6, suivez les instructions    |");
		System.out.println("+---------------------------------------------------------------+");
		
		ComputerService cs = new ComputerService();
		System.out.println(cs.getComputers(null, null));
		
	}

}
