package com.excilys.computerdb.command;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.excilys.computerdb.dao.DAOFactory;


public enum Command {
	GET_ALL_COMPUTERS ("allComputers") {
		@Override
		public void execute(DAOFactory daoFactory) throws SQLException {
			if (daoFactory == null) {
				throw new IllegalArgumentException();
			}
			System.out.println(daoFactory.getComputerDAO().getComputers(null, null));
		}
	},
	GET_ALL_COMPANIES ("allCompanies") {
		@Override
		public void execute(DAOFactory daoFactory) throws SQLException {
			if (daoFactory == null) {
				throw new IllegalArgumentException();
			}
			System.out.println(daoFactory.getCompanyDAO().getCompanies());			
		}
	},
	GET_ONE_COMPUTER ("oneComputer") {
		@Override
		public void execute(DAOFactory daoFactory) {
			
		}
	},
	CREATE_COMPUTER ("createComputer") {
		@Override
		public void execute(DAOFactory daoFactory) {
			
		}
	},
	UPDATE_COMPUTER ("updateComputer") {
		@Override
		public void execute(DAOFactory daoFactory) {
			
		}
	},
	DELETE_COMPUTER ("deleteComputer") {
		@Override
		public void execute(DAOFactory daoFactory) {
			
		}
	},
	EXIT ("exit") {
		@Override
		public void execute(DAOFactory daoFactory) {
			
		}
	};
	
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
	public abstract void execute(DAOFactory daoFactory) throws SQLException;
}
