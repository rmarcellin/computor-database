package com.excilys.computerdb.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.LocalDate;

import com.excilys.computerdb.beans.*;
import com.excilys.computerdb.dto.CompanyDTO;
import com.excilys.computerdb.dto.ComputerDTO;
import com.excilys.computerdb.exception.DAOException;
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
		return Timestamp.valueOf(ld.getYear() + "-" + ld.getMonthOfYear() + "-"
				+ ld.getDayOfMonth() + " 00:00:00.0");
	}

	/**
	 * Close ressources.
	 *
	 * @param conn
	 *            the conn
	 * @param p
	 *            the p
	 * @throws SQLException
	 *             the SQL exception
	 * @pre conn != null p != null
	 */
	public static void closeRessources(Connection conn, PreparedStatement p,
			ResultSet resultSet) throws SQLException {
		if (conn != null) {
			conn.close();
		}
		if (p != null) {
			p.close();
		}
		if (resultSet != null) {
			resultSet.close();
		}
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

	public static LocalDate produceLocalDateFromString(String localDate) {
		if (localDate == null) {
			return null;
		}
				
		String[] str = localDate.split("-");
		int year = Integer.parseInt(str[0]);
		int month = Integer.parseInt(str[1]);
		int day = Integer.parseInt(str[2]);
		
		return new LocalDate(year, month, day);
	}

	public static boolean isDateValid(String dateStr) {
		Pattern patern = Pattern
				.compile("^\\d{4}[-]?\\d{1,2}[-]?\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}");
		return patern.matcher(dateStr).matches();
	}

	public static <T> void print(List<T> list, int pageSize, Scanner sc) {
		int listSize = list.size();
		int currPos = 0, fstIndex = 0, tmpIndex = 0;
		if (listSize <= pageSize) {
			System.out.println(list);
		} else {
			System.out
					.println("Tapez \"n\" pour suivant et \"p\" pour précédent");
			restart: switch (sc.next()) {
			case "n":
				tmpIndex = listSize - currPos;
				if (pageSize < tmpIndex) {
					System.out.println(list.subList(currPos, pageSize));
					currPos += pageSize;
					break restart;
				} else {
					System.out.println(list.subList(currPos, tmpIndex + 1));
					// currPos += tmpIndex;
					break;
				}
			case "p":
				if (currPos != 0) {
					fstIndex = currPos - pageSize;
					System.out.println(list.subList(fstIndex, pageSize));
					currPos -= pageSize;
					break restart;
				}
				break;
			default:
				System.out
						.println("Tapez \"n\" pour suivant et \"p\" pour précédent");
				break restart;
			}
		}
	}

	public static String getCompanyNameById(long companyId, Connection c)
			throws SQLException {
		String SQL_SELECT_COMPANY = "SELECT * FROM company WHERE id = "
				+ companyId;
		String companyName = "";
		Statement p = null;
		ResultSet resultSet = null;

		try {
			p = c.createStatement();
			resultSet = p.executeQuery(SQL_SELECT_COMPANY);
			resultSet.next();
			companyName = resultSet.getString("name");
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			p.close();
		}

		return companyName;
	}

	public static Computer fromDTOToComputer(ComputerDTO computer) {
		Computer comp = new Computer();
		if (computer.getId() != 0) {
			comp.setId(computer.getId());
		}
		comp.setName(computer.getName());
		comp.setIntroduced(Util.produceLocalDateFromString(computer
				.getIntroduced()));
		comp.setDiscontinued(Util.produceLocalDateFromString(computer
				.getDiscontinued()));
		comp.setCompanyId(computer.getCompanyId());
		comp.setCompanyName(computer.getCompanyName());
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
		comp.setCompanyId(computer.getCompanyId());
		comp.setCompanyName(computer.getCompanyName());
		return comp;
	}

	public static CompanyDTO fromCompanyToDTO(Company company) {
		return new CompanyDTO(company.getId(), company.getName());
	}

	public static ComputerDTO getComputerDTOFromHttpServlet(
			HttpServletRequest request) {
		ComputerDTO computerDTO = new ComputerDTO();
		String compId = request.getParameter("id");
		if (compId != null) {
			computerDTO.setId(Long.parseLong(compId));
		}
		computerDTO.setName(request.getParameter("computerName"));
		computerDTO.setIntroduced(request.getParameter("introduced"));
		computerDTO.setDiscontinued(request.getParameter("discontinued"));
		computerDTO.setCompanyId(Long.parseLong(request.getParameter("companyId")));

		return computerDTO;
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
