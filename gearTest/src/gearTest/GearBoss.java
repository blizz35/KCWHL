package gearTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class GearBoss {

	static final Scanner kb = new Scanner(System.in);
	
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		
		
		
		System.out.println("Welcome to Jurassic Park");
		
		mainLoop();
		
		kb.close();
	}
	
	public static void mainLoop() throws SQLException {
		
		String[] optionArray = {"1. Add/Update a skater", "2. Add/Update Gear", "3. Add/Update a session"};
		
		System.out.println("Gear Bossing v0.1");
		System.out.println("Please pick an option");
		for (String option : optionArray) {
			System.out.println(option);
		}

		System.out.print("Selection: ");
		String selected = kb.nextLine();

		try {
			int selection = Integer.parseInt(selected);
			selection -= 1;
			
			if (selection < optionArray.length) {
				switch (selection) {
				case 0:
					skaterMenu();
					break;
				case 1:
					gearMenu();
					break;
				case 3:
					sessionMenu();
					break;
				}
			}
			else {
				System.out.println("Invalid selction");
				mainLoop();
			}
		}
		catch (NumberFormatException ex){
			System.out.println("Invalid input");
			mainLoop();
		}
		
	}
	
	public static void skaterMenu() throws SQLException {
		
		String[] skaterOptions = {"1. Add new skater", "2. Update existing skater", "3. Deactivate existing skater", "4. Go back"};
		
		for (String option : skaterOptions) {
			System.out.println(option);
		}

		System.out.print("Selection: ");
		String selected = kb.nextLine();
		
		try {
			int selection = Integer.parseInt(selected);
			selection -= 1;
			
			if (selection < skaterOptions.length) {
				switch (selection) {
				case 0:
					addSkater();
					break;
				case 1:
					updateSkater();
					break;
				case 3:
					updateSkater();
					break;
				case 4:
					mainLoop();
				}
			}
			else {
				System.out.println("Invalid selction");
				mainLoop();
			}
		}
		catch (NumberFormatException ex){
			System.out.println("Invalid input");
			mainLoop();
		}
		
	}
	
	public static void gearMenu() {
		
	}
	
	public static void sessionMenu() {
		
	}
	
	public static ResultSet executeSelect(String queryString) {
		
		Connection conn = null;
		String url = "jdbc:mysql://192.168.1.169:3306/gearBoss";
		String user = "sara";
		String pass = "srowe9966";
		
		try {
		    conn = DriverManager.getConnection(url, user, pass);
		    
		    Statement stmt = null;
			ResultSet rs = null;
			
			try {
			    stmt = conn.createStatement();
			    rs = stmt.executeQuery(queryString);
			    
			    return rs;
			}
			catch (SQLException ex){
			    // handle any errors
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());
			}
			finally {
			    // it is a good idea to release
			    // resources in a finally{} block
			    // in reverse-order of their creation
			    // if they are no-longer needed
			    if (rs != null) {
			        try {
			            rs.close();
			        } catch (SQLException sqlEx) { } // ignore
			        rs = null;
			    }
			    if (stmt != null) {
			        try {
			            stmt.close();
			        } catch (SQLException sqlEx) { } // ignore
			        stmt = null;
			    }
			}
			    
		} catch (SQLException ex) {
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
		
		return null;
	}
	
	public static void executeInsert(String statement) {
		
		Connection conn = null;
		String url = "jdbc:mysql://192.168.1.169:3306/gearBoss";
		String user = "sara";
		String pass = "srowe9966";
		
		try {
		    conn = DriverManager.getConnection(url, user, pass);
		    
		    Statement stmt = null;
			int rs = 0;
			
			try {
			    stmt = conn.createStatement();
			    rs = stmt.executeUpdate(statement);
			}
			catch (SQLException ex){
			    // handle any errors
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());
			}
			finally {
			    // it is a good idea to release
			    // resources in a finally{} block
			    // in reverse-order of their creation
			    // if they are no-longer needed
				if (stmt != null) {
			        try {
			            stmt.close();
			        } catch (SQLException sqlEx) { } // ignore
			        stmt = null;
			    }
			}
			    
		} catch (SQLException ex) {
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
	
	public static void getSkater(String firstName, String lastName) {
		
		String query = "SELECT * FROM skater where firstName = " + firstName + " and lastName = " + lastName;
		ResultSet rs = executeSelect(query);
		
		try {
			if (rs.next()) {
				
				System.out.println("First Name: " + rs.getString(2) + "\n" + "Last Name: " + rs.getString(3));
			}
			else {
				System.out.println("Record not found");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateSkater() {
		
	}
	
	public static void addSkater() throws SQLException {
		
		System.out.println("Enter the skater's information here");
		String[] skaterInfo = {"First name: ", "Last Name: ", "Email: ", "Phone Number: ", "Contacted (Y/N): ", "Preferred Position: ", "Referred by (First and Last Name): "};
		String[] skaterDetails = new String[7];
		
		for (int i = 0; i < skaterDetails.length; i++) {
			System.out.print(skaterInfo[i]);
			skaterDetails[i] = kb.nextLine();
		}
		
		String[] referName = skaterDetails[6].split("\s");
		
		String referQuery = "select skaterID from skater where firstName = '" + referName[0] + "' and lastName = '" + referName[1] + "';";
		
		try {
			ResultSet refer = executeSelect(referQuery);
			int referID = 0;
			if (refer.isClosed() == false) {
				if (refer.next()) {
					referID = refer.getInt(0);
				}
			}
			
			if (skaterDetails[4] == "Y") {
				skaterDetails[4] = 	"1";
			}
			else {
				skaterDetails[4] = "0";
			}
		
			String query = "insert into skater (firstName, lastName, email, phone, contacted, position, referredBy, skaterStatusID) "
				+ "values ('" + skaterDetails[0] + "', '" + skaterDetails[1] + "', '" + skaterDetails[2] + "', '" + skaterDetails[3] + "', '" + skaterDetails[4] + "', '" + skaterDetails[5] + "', " + referID + ", 1);";
		
			System.out.println("Inserting record");
			
			executeInsert(query);
			
			System.out.println("Skater inserted");
			
			System.out.print("Insert another (Y/N): ");
			if (kb.nextLine() == "Y") {
				addSkater();
			}
			else {
				mainLoop();
			}
			
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
}
