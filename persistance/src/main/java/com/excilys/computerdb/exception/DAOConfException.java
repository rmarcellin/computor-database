package com.excilys.computerdb.exception;

@SuppressWarnings("serial")
public class DAOConfException extends RuntimeException {

	public DAOConfException () {
	}

	public DAOConfException (String msg) {
		super (msg);
	}

	public DAOConfException (Throwable throwable) {
		super (throwable);
	}
	
	public DAOConfException(String msg, Throwable throwable) {
		super (msg, throwable);
	}

}
