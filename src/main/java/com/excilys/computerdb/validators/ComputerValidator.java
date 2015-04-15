package com.excilys.computerdb.validators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.excilys.computerdb.dto.ComputerDTO;
import com.excilys.computerdb.utils.Util;

public class ComputerValidator implements Validator {
	private static Logger logger = LoggerFactory.getLogger(ComputerValidator.class);
	
	/**
	 * FIELDS
	 */
	private static final String NAME = "name";
	
	private static final String DISCO_NOT_VALID_DATE = "Invalid \"Discontinued\" date";
	
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
	
	private boolean isValidLocalDate (String localdate) {
		String tmpLocaldate = localdate.trim();
		if (!tmpLocaldate.isEmpty()) {
			if (!Util.isDateValid(tmpLocaldate + " 00:00:00")) {
				logger.error(DISCO_NOT_VALID_DATE);
				return false;
			}
			LocalDate dico = Util.produceLocalDateFromString(tmpLocaldate);
			if (!isValideMonth(dico)) {
				logger.error(DISCO_NOT_VALID_DATE);
				return false;
			}
		}		
		
		return true;
	}

	@Override
	public boolean supports(Class<?> paramClass) {
		return ComputerDTO.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, NAME, "compuName.required");
		ComputerDTO compu = (ComputerDTO) obj;
		if (compu.getIntroduced() != null) {
			if (!isValidLocalDate(compu.getIntroduced())) {
				errors.rejectValue("introduced", "badDate", new Object[]{"'introduced'"}, "Bad \"Introduced\" date");
			}
		}		
		
		if (compu.getDiscontinued() != null) {
			if (!isValidLocalDate(compu.getDiscontinued())) {
				errors.rejectValue("discontinued", "badDate", new Object[]{"'discontinued'"}, "Bad \"Discontinued\" date");
			}
		}
		
	}
}
