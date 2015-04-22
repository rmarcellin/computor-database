package com.excilys.computerdb.exception;

@SuppressWarnings("serial")
public class DAOException extends RuntimeException {
	
	public DAOException () {
	}

	public DAOException (String msg) {
		super (msg);
	}

	public DAOException (Throwable throwable) {
		super (throwable);
	}
	
	public DAOException (String msg, Throwable throwable) {
		super (msg, throwable);
	}

}
