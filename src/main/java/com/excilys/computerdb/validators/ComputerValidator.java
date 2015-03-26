package com.excilys.computerdb.validators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.LocalDate;

import com.excilys.computerdb.dto.ComputerDTO;
import com.excilys.computerdb.utils.*;

public class ComputerValidator {

	public static boolean isValide(ComputerDTO computer) {
		// TODO Log errors

		// COMPUTER NAME
		if (computer == null) {
			return false;
		}
		if (computer.getName() == null) {
			return false;
		}
		if (computer.getName().isEmpty()) {
			return false;
		}
		if (computer.getName().length() < 2) {
			return false;
		}

		// COMPUTER INTRODUCED
		if (computer.getIntroduced() != null) {
			String introDate = computer.getIntroduced();
			if (!Util.isDateValid(introDate + " 00:00:00")) {
				return false;
			}			
			LocalDate intro = Util.produceLocalDateFromString(introDate);
			if (!isValideMonth(intro)) {
				return false;
			}
		}

		// COMPUTER DISCONINUED
		if (computer.getDiscontinued() != null) {
			String discoDate = computer.getIntroduced();
			if (!Util.isDateValid(discoDate + " 00:00:00")) {
				return false;
			}
			LocalDate dico = Util.produceLocalDateFromString(discoDate);
			if (!isValideMonth(dico)) {
				return false;
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
