package com.excilys.computerdb.validators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.LocalDate;

import com.excilys.computerdb.beans.*;
import com.excilys.computerdb.utils.*;

public class ComputerValidator {

	public static boolean isValide(Computer computer) {
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

		if (computer.getName().contains("<") || computer.getName().contains(">")
				|| computer.getName().contains("/") || computer.getName().contains(":")) {
			return false;
		}

		// COMPUTER INTRODUCED
		if (computer.getIntroduced() != null) {
			LocalDate intro = computer.getIntroduced();
			if (!isValideMonth(intro)) {
				return false;
			}
		}

		// COMPUTER DISCONINUED
		if (computer.getDiscontinued() != null) {
			LocalDate dico = computer.getDiscontinued();
			if (!isValideMonth(dico)) {
				return false;
			}
		}

		return true;
	}

	private static boolean isValideMonth(LocalDate ld) {
		if (!Util.isDateValid(ld.toString())) {
			return false;
		}
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
		return true;
	}
}
