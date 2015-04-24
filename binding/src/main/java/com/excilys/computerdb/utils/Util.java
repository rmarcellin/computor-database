package com.excilys.computerdb.utils;

import java.sql.Timestamp;

import org.joda.time.LocalDate;
import org.slf4j.LoggerFactory;

import com.excilys.computerdb.dto.CompanyDTO;
import com.excilys.computerdb.dto.ComputerDTO;
import com.excilys.computerdb.model.Company;
import com.excilys.computerdb.model.Computer;
/**
 * The Class Util.
 */
public class Util {
	
	private static final String SORT_ORDER_NOT_SPECIFIED = 
			"Sort order not specified";

	/**
	 * Gets the time stamp from local date.
	 *
	 * @param ld
	 *            the ld
	 * @return the time stamp from local date
	 * @pre ld != null
	 */
	public static Timestamp getTimeStampFromLocalDate(LocalDate ld) {
		if (ld == null) {
			return null;
		}
		int year = ld.getYear();
		int day = ld.getDayOfMonth();
		int month = ld.getMonthOfYear();
		LoggerFactory.getLogger(Util.class).debug("YEAR : {} -> MONTH : {} -> DAY : {}", year, month, day);
		return Timestamp.valueOf(year + "-" + month + "-" + day + " 00:00:00.0");
	}

	/**
	 * Gets the local date from timestamp.
	 *
	 * @pre timestamp != null
	 * @param timestamp
	 *            the timestamp
	 * @return the local date from timestamp
	 */
	public static LocalDate getLocalDateFromTimestamp(Timestamp timestamp) {
		if (timestamp == null) {
			return null;
		}
		long timestampLong = timestamp.getTime();
		LocalDate localDate = new LocalDate(timestampLong);
		return localDate;
	}

	public static Computer fromDTOToComputer(ComputerDTO computer) {
		Computer comp = new Computer();
		if (computer.getId() != 0) {
			comp.setId(computer.getId());
		}
		comp.setName(computer.getName());
		if (computer.getIntroduced() != null) {
			String defIntro = computer.getIntroduced().trim();
			if (!defIntro.isEmpty()) {
				comp.setIntroduced(LocalDate.parse(computer
						.getIntroduced()));
			}
		}
		
		if (computer.getDiscontinued() != null) {
			String defDisco = computer.getDiscontinued().trim();
			if (!defDisco.isEmpty()) {
				comp.setDiscontinued(LocalDate.parse(computer
						.getDiscontinued()));
			}
		}
		
		comp.setCompany(new Company(computer.getCompanyId(), computer.getCompanyName()));
		return comp;
	}

	public static ComputerDTO fromComputerToDTO(Computer computer) {
		ComputerDTO comp = new ComputerDTO();
		comp.setId(computer.getId());
		comp.setName(computer.getName());
		if (computer.getIntroduced() == null) {
			comp.setIntroduced("");
		} else {
			comp.setIntroduced(computer.getIntroduced().toString());
		}
		if (computer.getDiscontinued() == null) {
			comp.setDiscontinued("");
		} else {
			comp.setDiscontinued(computer.getDiscontinued().toString());
		}
		if (computer.getCompany() != null) {
			comp.setCompanyId(computer.getCompany().getId());
			comp.setCompanyName(computer.getCompany().getName());
		}
		return comp;
	}

	public static CompanyDTO fromCompanyToDTO(Company company) {
		return new CompanyDTO(company.getId(), company.getName());
	}
	
	public static String getNextSortOrder (String currentOrder) {
		if (currentOrder == null) {
			throw new IllegalArgumentException(SORT_ORDER_NOT_SPECIFIED);
		} else {
			String tmp = currentOrder.trim();
			if (tmp.isEmpty()) {
				throw new IllegalArgumentException();
			}			
		}
		if (currentOrder.equals("default") || currentOrder.equals("desc")) {
			return "asc";
		} else {
			return "desc";
		}
	}
}
