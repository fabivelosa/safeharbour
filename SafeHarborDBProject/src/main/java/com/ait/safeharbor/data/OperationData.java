package com.ait.safeharbor.data;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.ait.safeharbor.dto.AccidentBean;
import com.ait.safeharbor.dto.AccidentVehicleBean;
import com.ait.safeharbor.dto.AddressBean;
import com.ait.safeharbor.dto.MessageBean;
import com.ait.safeharbor.resource.DatabaseConnection;

public class OperationData {

	// Connection DB
	private Connection con = null;
	private Statement stmt = null;
	private PreparedStatement stmtp = null;
	MessageBean message = new MessageBean();

	public int getAccidentId() {
		int accidentID = 0;
		ResultSet rs = null;
		String cmd = "select max(reportid)from accident;";
		try {
			con = new DatabaseConnection().connect();
			stmt = con.prepareStatement(cmd);
			stmt.executeQuery(cmd);
			rs = stmt.executeQuery(cmd);
			System.out.println(cmd);

			while (rs.next()) {
				accidentID = rs.getInt(1);
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		System.out.println(accidentID);
		return accidentID;
	};

	// Add operation
	public MessageBean addRecord(String location, String accidentDate, String accidentTime) {

		String cmd = "{call add_Accident (?,?,?,?)}";
		// Try call this query on the SQL database
		try {
			SimpleDateFormat date1 = new SimpleDateFormat("dd/mm/yyyy");
			java.util.Date date2 = date1.parse(accidentDate);
			con = new DatabaseConnection().connect();
			stmtp = con.prepareStatement(cmd);
			stmtp.setString(1, location);
			stmtp.setDate(2, new java.sql.Date(date2.getTime()));
			stmtp.setString(3, accidentTime);
			stmtp.setString(4, con.getMetaData().getUserName());
			System.out.println(stmtp.toString());
			stmtp.executeUpdate();
			message.setMessage("Success: Accident report " + getAccidentId() + "  created!");
		} catch (Exception e1) {
			message.setMessage("Fail: accident NOT created!");
			e1.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return message;
	}

	// Search operation
	public AccidentBean searchRecord(String recordId) {

		ResultSet rs = null;
		String cmd = "select CONCAT(address,' - ' ,city,' - ' ,county), accidentDate, accidentTime from accident "
				+ "inner join address on accident.location = address.addressid where reportId = " + recordId + ";";

		AccidentBean accident = null;
		System.out.println(cmd);
		// Try call this query on the SQL database
		try {
			con = new DatabaseConnection().connect();
			stmt = con.createStatement();
			rs = stmt.executeQuery(cmd);
			System.out.print(cmd);
			while (rs.next()) {
				accident = new AccidentBean();
				accident.setLocation(rs.getString(1));
				accident.setAccidentDate(rs.getDate(2));
				System.out.println(rs.getDate(2));
				accident.setAccidentTime(rs.getString(3));
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return accident;
	}

	public List<String> loadLocation() {

		ResultSet rs = null;
		String cmd = "select addressID, CONCAT(address,' - ' ,city,' - ' ,county) as FullAddress from address;";

		AddressBean address = new AddressBean();
		List<String> addressList = new ArrayList<String>();
		System.out.println(cmd);
		// Try call this query on the SQL database
		try {
			con = new DatabaseConnection().connect();
			stmt = con.createStatement();
			rs = stmt.executeQuery(cmd);
			System.out.print(cmd);
			while (rs.next()) {
				addressList.add(rs.getString(2));
				address.setAddressID(rs.getInt(1));
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return addressList;
	}

	// Search detail operation
	public AccidentVehicleBean searchRecordDetail(String recordId) {

		ResultSet rs = null;
		String cmd = "select registration, licenceId, amount  "
				+ "FROM vehicle_accident where reportId = " + recordId + ";";

		AccidentVehicleBean accidentDetail = null;
		System.out.println(cmd);
		// Try call this query on the SQL database
		try {
			con = new DatabaseConnection().connect();
			stmt = con.createStatement();
			rs = stmt.executeQuery(cmd);
			while (rs.next()) {
				accidentDetail = new AccidentVehicleBean();
				accidentDetail.setRegistration(rs.getString(1));
				accidentDetail.setLicenceId(rs.getString(2));
				accidentDetail.setAmount(rs.getDouble(3));
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return accidentDetail;
	}

	// Add/Save operation
	public MessageBean saveRecord(String registration, String licenceId, Double amount, String recordId) {

		String cmd = "insert into vehicle_accident(registration,licenceId,reportId, amount, userUpdate )  values (?,?,?,?,?);";

		// Try call this query on the SQL database
		try {
			con = new DatabaseConnection().connect();
			stmtp = con.prepareStatement(cmd);
			stmtp.setString(1, registration);
			stmtp.setString(2, licenceId);
			stmtp.setString(3, recordId);
			stmtp.setDouble(4, amount);
			stmtp.setString(5, con.getMetaData().getUserName());
			System.out.println(stmtp.toString());
			stmtp.executeUpdate();
			message.setMessage("Success: Accident details created on Accident " + recordId + " !");
		} catch (Exception e1) {
			message.setMessage("Fail: accident NOT created! Enter a valid Licence and Registration!");
			e1.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return message;
	}

	// Update operation
	public MessageBean updateRecord(String registration, String licenceId, Double amount, String recordId) {

		String cmd = " update vehicle_accident set registration = ?, licenceId = ?, amount  = ?, \n"
				+ "userUpdate = ?  where reportId = ?;";

		// Try call this query on the SQL database
		try {
			con = new DatabaseConnection().connect();
			stmtp = con.prepareStatement(cmd);
			stmtp.setString(1, registration);
			stmtp.setString(2, licenceId);
			stmtp.setDouble(3, amount);
			stmtp.setString(4, con.getMetaData().getUserName());
			stmtp.setString(5, recordId);
			System.out.println(stmtp.toString());
			stmtp.executeUpdate();
			message.setMessage("Success: Accident report " + recordId + "  updated!");
		} catch (Exception e1) {
			message.setMessage("Fail: accident NOT updated! Enter a valid Licence and Registration!");
			e1.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return message;
	}

	// Delete operation (parent table Accident)
	public MessageBean deleteRecord(String recordId) {
		String cmd = "delete from accident where reportId = " + recordId + ";";

		// Try call this query on the SQL database
		try {
			con = new DatabaseConnection().connect();
			stmt = con.createStatement();
			stmt.executeUpdate(cmd);
			message.setMessage("Success: Accident report " + recordId + "  deleted!");
		} catch (Exception e1) {
			message.setMessage("Fail: accident NOT deleted!");
			e1.printStackTrace();
		}
		return message;
	}

	// Delete operation (Child table Accident_Vehicle)
	public MessageBean deleteRecordC(String registration, String licenceId, String amount, String recordId) {
		String cmd = "delete from vehicle_accident where reportId = " + recordId + ";";

		// Try call this query on the SQL database
		try {
			con = new DatabaseConnection().connect();
			stmt = con.createStatement();
			stmt.executeUpdate(cmd);
			message.setMessage("Success: Accident details - report " + recordId + "  deleted!");
		} catch (Exception e1) {
			message.setMessage("Fail: accident NOT deleted!");
			e1.printStackTrace();
		}
		return message;
	}

	// list all records
	public MessageBean exportRecords() {
		// view all_accidents
		String cmd = "select * from accidents_vehicle_details;";

		// Try call this query on the SQL database
		try {
			con = new DatabaseConnection().connect();
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(cmd);
			writeToFile(rs);
			message.setMessage("Success: File AccidentReport.csv created!");
		} catch (Exception e1) {
			message.setMessage("Fail: file NOT created!");
			e1.printStackTrace();
		}
		return message;
	}

	// Write information to a file based on a list of records
	private void writeToFile(ResultSet rs) {
		try {
			// What does this do?
			FileWriter outputFile = new FileWriter("AccidentReport.csv");
			PrintWriter printWriter = new PrintWriter(outputFile);
			ResultSetMetaData rsmd = rs.getMetaData();
			int numColumns = rsmd.getColumnCount();

			// What does this do?
			for (int i = 0; i < numColumns; i++) {
				printWriter.print(rsmd.getColumnLabel(i + 1) + ",");
			}
			printWriter.print("\n");

			// What does this do?
			while (rs.next()) {
				for (int i = 0; i < numColumns; i++) {
					printWriter.print(rs.getString(i + 1) + ",");
				}
				printWriter.print("\n");
				printWriter.flush();
			}
			// What does this do?
			printWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<String[]> createReport(int userChoice) {
		// choose the query for report
		String cmd = null;
		List<String[]> searchList = new ArrayList<String[]>();
		switch (userChoice) {
		case 1: // Average AMOUNT by County
			cmd = "SELECT county , ROUND(AVG(amount),2),  '' as colum3 FROM vehicle_accident  \n"
					+ "INNER JOIN accident ON vehicle_accident.reportid = accident.reportid\n"
					+ "INNER JOIN address ON accident.location = address.addressid\n" + "GROUP BY county"
							+ " order by 2 desc;";

			break;
		case 2: // Sum AMOUNT by City/County with rollup
			cmd = "SELECT  county, city , round(sum(amount),2) FROM vehicle_accident\n"
					+ "   INNER JOIN accident ON vehicle_accident.reportid = accident.reportid\n"
					+ "   INNER JOIN address ON accident.location = address.addressid\n" + "GROUP BY county, city \n"
					+ "WITH ROLLUP;";

			break;
		case 3: // AVERAGE AGE by Gender
			cmd = "SELECT ROUND(AVG(YEAR(NOW()) - YEAR(driver.dateofBirth))) AS 'AvgAge', gender\n, '' as colum3 "
					+ "FROM vehicle_accident\n" + "INNER JOIN driver ON vehicle_accident.licenceId = driver.licenceId\n"
					+ "GROUP BY gender;";
		}

		System.out.print(cmd);

		// Try call this query on the SQL database
		try {
			con = new DatabaseConnection().connect();
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(cmd);
			String[] item = null;
			while (rs.next()) {
				if (rs != null) {
					item = new String[3];
					item[0] = rs.getString(1);
					item[1] = rs.getString(2);
					item[2] = rs.getString(3);
					searchList.add(item);
				}
			}
			message.setMessage("Success: report created!");
		} catch (Exception e1) {
			message.setMessage("Fail: report NOT created!");
			e1.printStackTrace();
		}
		System.out.println(searchList.size());
		return (List<String[]>) searchList;
	}

}
