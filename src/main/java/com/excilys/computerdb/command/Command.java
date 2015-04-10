package com.excilys.computerdb.command;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.computerdb.services.CompanyService;
import com.excilys.computerdb.services.ComputerService;


public enum Command {
	GET_ALL_COMPUTERS ("allComputers") {
		@Override
		public void execute(CompanyService companyService) throws SQLException {
			// TODO method
			
		}

		@Override
		public void execute(ComputerService computerService)
				throws SQLException {
			// TODO method
			
		}
	},
	GET_ALL_COMPANIES ("allCompanies") {

		@Override
		public void execute(CompanyService companyService) throws SQLException {
			// TODO method
			
		}

		@Override
		public void execute(ComputerService computerService)
				throws SQLException {
			// TODO method
			
		}
	},
	GET_ONE_COMPUTER ("oneComputer") {

		@Override
		public void execute(CompanyService companyService) throws SQLException {
			// TODO method
			
		}

		@Override
		public void execute(ComputerService computerService)
				throws SQLException {
			// TODO method
			
		}
	},
	CREATE_COMPUTER ("createComputer") {

		@Override
		public void execute(CompanyService companyService) throws SQLException {
			// TODO method
			
		}

		@Override
		public void execute(ComputerService computerService)
				throws SQLException {
			// TODO method
			
		}
	},
	UPDATE_COMPUTER ("updateComputer") {

		@Override
		public void execute(CompanyService companyService) throws SQLException {
			// TODO method
			
		}

		@Override
		public void execute(ComputerService computerService)
				throws SQLException {
			// TODO method
			
		}
	},
	DELETE_COMPUTER ("deleteComputer") {

		@Override
		public void execute(CompanyService companyService) throws SQLException {
			// TODO method
			
		}

		@Override
		public void execute(ComputerService computerService)
				throws SQLException {
			// TODO method
			
		}
	},
	EXIT ("exit") {

		@Override
		public void execute(CompanyService companyService) throws SQLException {
			// TODO method
			
		}

		@Override
		public void execute(ComputerService computerService)
				throws SQLException {
			// TODO method
			
		}
	};
	
	@Autowired
	private CompanyService companyService;
	@Autowired
	private ComputerService computerService;
	
	
	private final String command;
	private static Map<String, Command> commands;
	static {
		commands = new HashMap<>();
		for (Command com : Command.values()) {
			commands.put(com.command, com);
		}
	}
	
	private Command(String commande) {
		this.command = commande;
	}
	
	public static Command getCommand (String command) {
		return commands.get(command);
	}
	
	// Agit comme interface, et doit être implémanter dans chaque constante de
	// l'enum. Voir plus haut
	public abstract void execute(CompanyService companyService) throws SQLException;
	public abstract void execute(ComputerService computerService) throws SQLException;
}
