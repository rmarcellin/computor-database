package com.excilys.computerdb.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.joda.time.LocalDate;

// TODO: Auto-generated Javadoc
/**
 * The Class Util.
 */
public class Util {
	
	/**
	 * Gets the time stamp from local date.
	 *
	 * @param ld the ld
	 * @return the time stamp from local date
	 * @pre ld != null
	 */
	public static Timestamp getTimeStampFromLocalDate(LocalDate ld) {
		return Timestamp.valueOf(
				ld.getYear() + "-" + 
				ld.getMonthOfYear() + "-" + 
				ld.getDayOfMonth() + 
				" 00:00:00.0");
	}
	
	/**
	 * Close ressources.
	 *
	 * @param conn the conn
	 * @param p the p
	 * @throws SQLException the SQL exception
	 * @pre conn != null
	 * 		p	 != null
	 */
	public static void closeRessources(Connection conn, PreparedStatement p) throws SQLException {
		if (conn == null || p == null) {
			throw new IllegalArgumentException();
		}
		conn.close();
		p.close();
	}
	
	
	/**
	 * Gets the local date from timestamp.
	 *
	 * @pre timestamp != null
	 * @param timestamp the timestamp
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
	
	public static LocalDate poduceLocalDateFromString(String localDate) {
		if (localDate == null) {
			throw new IllegalArgumentException();
		}
		String[] str = localDate.split("-");
		final LocalDate ld = new LocalDate();
		ld.withYear(Integer.parseInt(str[0]));
		ld.withMonthOfYear(Integer.parseInt(str[1]));
		ld.withDayOfMonth(Integer.parseInt(str[2]));
		
		return ld;
	}
	
	public static boolean dateValidator(String dateStr) {
		Pattern patern = Pattern.compile(
				"^\\d{4}[-]?\\d{1,2}[-]?\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}");
		return patern.matcher(dateStr).matches();
	}
	
	public static <T> void print (List<T> list, int pageSize, Scanner sc) {
		int listSize = list.size();
		int currPos = 0, fstIndex = 0, tmpIndex = 0;
		if (listSize <= pageSize) {
			System.out.println(list);
		} else {
			System.out.println(
					"Tapez \"n\" pour suivant et \"p\" pour précédent");
			restart:
			switch (sc.next()) {
			case "n":
				tmpIndex = listSize - currPos;
				if (pageSize < tmpIndex) {
					System.out.println(list.subList(currPos, pageSize));
					currPos += pageSize;
					break restart;
				} else {
					System.out.println(list.subList(currPos, tmpIndex + 1));
					//currPos += tmpIndex;
					break;
				}
			case "p" :
				if (currPos != 0) {
					fstIndex = currPos - pageSize;
					System.out.println(list.subList(fstIndex, pageSize));
					currPos -= pageSize;
					break restart;
				}
				break;
			default:
				System.out.println(
						"Tapez \"n\" pour suivant et \"p\" pour précédent");
				break restart;
			}
		}
	}

}
