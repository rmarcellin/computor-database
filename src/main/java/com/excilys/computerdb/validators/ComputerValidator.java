package com.excilys.computerdb.validators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdb.dto.ComputerDTO;
import com.excilys.computerdb.utils.*;

public class ComputerValidator {
	private static Logger logger = LoggerFactory.getLogger(ComputerValidator.class);
	
	private static final String COMPUTER_NULL = "Computer object null";
	private static final String COMPUTER_NAME_EMPTY = "Computer name empty";
	private static final String COMPUTER_NAME_NULL = "Computer name null";
	private static final String COMPUTER_NAME_LENGTH = "Computer name's length too short";
	private static final String INTRO_NOT_VALID_DATE = "Invalid \"Introduced\" date";
	private static final String DISCO_NOT_VALID_DATE = "Invalid \"Discontinued\" date";

	public static boolean isValide(ComputerDTO computer) {

		// COMPUTER NAME
		if (computer == null) {
			logger.error(COMPUTER_NULL);
			return false;
		}
		if (computer.getName() == null) {
			logger.error(COMPUTER_NAME_NULL);
			return false;
		}
		if (computer.getName().isEmpty()) {
			logger.error(COMPUTER_NAME_EMPTY);
			return false;
		}
		if (computer.getName().length() < 2) {
			logger.error(COMPUTER_NAME_LENGTH);
			return false;
		}

		// COMPUTER INTRODUCED
		if (computer.getIntroduced() != null) {
			String introDate = computer.getIntroduced();
			if (!introDate.isEmpty()) {
				if (!Util.isDateValid(introDate + " 00:00:00")) {
					logger.error(INTRO_NOT_VALID_DATE);
					return false;
				}			
				LocalDate intro = Util.produceLocalDateFromString(introDate);
				if (!isValideMonth(intro)) {
					logger.error(INTRO_NOT_VALID_DATE);
					return false;
				}
			}
		}

		// COMPUTER DISCONINUED
		if (computer.getDiscontinued() != null) {
			String discoDate = computer.getIntroduced();
			if (!discoDate.isEmpty()) {
				if (!Util.isDateValid(discoDate + " 00:00:00")) {
					logger.error(DISCO_NOT_VALID_DATE);
					return false;
				}
				LocalDate dico = Util.produceLocalDateFromString(discoDate);
				if (!isValideMonth(dico)) {
					logger.error(DISCO_NOT_VALID_DATE);
					return false;
				}
			}
		}

		return true;
	}

	private static boolean isValideMonth(LocalDate ld) {
		List<Integer> months30 = new ArrayList<Integer>(Arrays.asList(4, 6, 9,
				11));
		if (months30.contains(ld.getMonthOfYear()) && ld.getDayOfMonth() > 30) {
			return false;
		}
		List<Integer> months31 = new ArrayList<Integer>(Arrays.asList(1, 3, 5,
				7, 8, 10, 12));
		if (months31.contains(ld.getMonthOfYear()) && ld.getDayOfMonth() > 31) {
			return false;
		}
		if (ld.getDayOfMonth() == 2) {
			int year = ld.getYear();
			if (year % 4 != 0) {
				if (year % 100 != 0) {
					return false;
				} else {
					if (year % 400 != 0) {
						return false;
					}
				}
			}
		}
		return true;
	}
}
