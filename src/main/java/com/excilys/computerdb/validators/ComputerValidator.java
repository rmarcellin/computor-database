package com.excilys.computerdb.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.excilys.computerdb.dto.ComputerDTO;

public class ComputerValidator implements Validator {
	
	@Autowired
	private MessageSource messageSource;
	
	/**
	 * FIELDS
	 */
	private static final String NAME = "name";

	@Override
	public boolean supports(Class<?> paramClass) {
		return ComputerDTO.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, NAME, "compuName.required");
				
		ComputerDTO compu = (ComputerDTO) obj;
		Pattern pattern = Pattern.compile(messageSource.getMessage("date.pattern", null, LocaleContextHolder.getLocale()));
		Matcher introMarcher = pattern.matcher(compu.getIntroduced());
		Matcher discoMatcher = pattern.matcher(compu.getDiscontinued());
		
		if (compu.getIntroduced() != null) {
			if (!compu.getIntroduced().isEmpty()) {
				if (!introMarcher.matches()) {
					errors.rejectValue("introduced", "invalid.date", new Object[]{"'introduced'"}, "Bad \"Introduced\" date");
				} else {
					validateYearLogic(introMarcher, errors, "introduced");
				}
			}
		}
		
		if (compu.getDiscontinued() != null) {
			if (!compu.getDiscontinued().isEmpty()) {
				if (!discoMatcher.matches()) {
					errors.rejectValue("discontinued", "invalid.date", new Object[]{"'discontinued'"}, "Bad \"Discontinued\" date");
				} else {
					validateYearLogic(discoMatcher, errors, "discontinued");
				}
			}
		}
	}
	
	
	////////////////////////////////////////////////////////////////////	
	private void validateYearLogic(final Matcher matcher, Errors errors, String field) {
		matcher.reset();
		if (matcher.find()) {
			String day = matcher.group(1);
			String month = matcher.group(2);
			int year = Integer.parseInt(matcher.group(3));

			if (day.equals("31")
					&& (month.equals("4") || month.equals("6")
							|| month.equals("9") || month.equals("11")
							|| month.equals("04") || month.equals("06") || month
								.equals("09"))) { // only 1,3,5,7,8,10,12 has 31 days
				errors.rejectValue(field, "invalid.date.days", new Object[]{"'" + month  + "'", "30"}, "");
			} else if (month.equals("2") || month.equals("02")) {
				// leap year
				if (year % 4 == 0) {
					if (day.equals("30") || day.equals("31")) {
						errors.rejectValue(field, "invalid.date.days", new Object[]{"'" + month  + "'", "29"}, "");
					}
				} else {
					if (day.equals("29") || day.equals("30")
							|| day.equals("31")) {
						errors.rejectValue(field, "invalid.date.days", new Object[]{"'" + month  + "'", "28"}, "");
					}
				}
			}
		}
	}
	
	
	
	
}
