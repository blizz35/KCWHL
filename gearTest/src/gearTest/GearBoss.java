package gearTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Scanner;


public class GearBoss {

	static final Scanner kb = new Scanner(System.in);
	
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		
		
		
		System.out.println("Welcome to Jurassic Park");
		
		mainLoop();
		
		kb.close();
	}
	
	public static void mainLoop() throws SQLException {
		
		String[] optionArray = {"1. Skater Options", "2. Gear Options", "3. Session Options", "4. Exit"};
		
		System.out.println();
		
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
				case 2:
					sessionMenu();
					break;
				case 3:
					System.exit(0);
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
		
		String[] skaterOptions = {"1. Add new skater", "2. Update existing skater", "3. View a skater", "4. Go back"};
		
		System.out.println();
		
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
				case 2:
					getSkater();
					break;
				case 3:
					mainLoop();
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
//			finally {
//			    // it is a good idea to release
//			    // resources in a finally{} block
//			    // in reverse-order of their creation
//			    // if they are no-longer needed
////			    if (rs != null) {
////			        try {
////			            rs.close();
////			        } catch (SQLException sqlEx) { } // ignore
////			        rs = null;
////			    }
//			    if (stmt != null) {
//			        try {
//			            stmt.close();
//			        } catch (SQLException sqlEx) { } // ignore
//			        stmt = null;
//			    }
//			}
			    
		} catch (SQLException ex) {
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
		
		return null;
	}
	
	public static void executeSproc(String statement) {
		
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
	
	public static String[] splitName(String name) {
		String[] nameSplit = new String[2];
		if (name.contains(" ")) {
			nameSplit = name.split("\s");
		}
		
		for (String subName : nameSplit) {
			subName = capitalizeFirstLetter(subName);
		}
		return nameSplit;
	}
	
	public static String capitalizeFirstLetter(String input) {
		if (input == null || input.isEmpty()) {
			return input;
		}
		
		return input.substring(0, 1).toUpperCase() + input.substring(1);
	}
	
	public static void getSkater() throws SQLException {
		
		System.out.println();
		
		System.out.print("Enter the skater's first and last name: ");
		
		String[] skaterName = splitName(kb.nextLine());
		
		String query = "SELECT * FROM skater where firstName = '" + skaterName[0] + "' and lastName = '" + skaterName[1] + "';";
		
		ResultSet rs = executeSelect(query);
		System.out.println(rs.getString(13));
		
		String activityStatus = "";
		if (rs.getString(13).equals("1")) {
			activityStatus = "Active";
		}
		else if (rs.getString(13).equals("2")) {
			activityStatus = "Inactive";
		}
		try {
			if (rs.next()) {
				
				System.out.println("First Name: " + rs.getString(2) + "\n" + "Last Name: " + rs.getString(3));
				System.out.println("Email: " + rs.getString(5));
				System.out.println("Phone Number: " + rs.getString(4));
				System.out.println("Preferred Position: " + rs.getString(9));
				System.out.println("Active: " + activityStatus);
			}
			else {
				System.out.println("Record not found");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.print("View another? (Y/N): ");
		if (kb.nextLine().toUpperCase().charAt(0) == 'Y') {
			getSkater();
		}
		else {
			mainLoop();
		} 
	}
	
	public static void updateSkater() throws SQLException {
		
		System.out.println();
		
		System.out.print("Enter the skater's first and last name: ");
		String updateName = kb.nextLine();
		String[] updateNameSplit = splitName(updateName);
		
		String nameQuery = "select skaterID from skater where firstName = '" + updateNameSplit[0] + "' and lastName = '" + updateNameSplit[1] + "';";
		
		System.out.print("Enter the field to update (firstName, lastName, email, phone, contacted, position, referredBy, status (active/inactive): ");
		String updateField = kb.nextLine();
		
		System.out.print("Enter the new value: ");
		String updateValue = kb.nextLine();
		
		String[] referName = new String[2];
		
		if (updateField == "contacted") {
			if (updateValue.toUpperCase() == "Y") {
				updateValue = "1";
			}
			else {
				updateValue = "0";
			}
		}
		if (updateField.equals("referredBy")) {
			if (updateValue.contains(" ")) {
				referName = splitName(updateValue);
			}
			else {
				referName[0] = "foo";
				referName[1] = "bar";
			}
		}
		
		String referQuery = "select skaterID from skater where firstName = '" + referName[0] + "' and lastName = '" + referName[1] + "';";
		int referID = 0;
		int skaterID = 0;
		
		try {
			ResultSet refer = executeSelect(referQuery);
			if (refer.isClosed() == false) {
				if (refer.next()) {
					referID = refer.getInt(1);
				}
			}
			
			ResultSet skater = executeSelect(nameQuery);
			if (skater.isClosed() == false) {
				if (skater.next()) {
					skaterID = skater.getInt(1);
				}
			}
				
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
		if (updateField.equals("referredBy")) {
			updateValue = Integer.toString(referID);
		}
		if (updateField.equals("status")) {
			updateField = "skaterStatusID";
			if (capitalizeFirstLetter(updateValue).equals("Active")) {
				updateValue = "1";
			}
			else if (capitalizeFirstLetter(updateValue).equals("Inactive")) {
				updateValue = "2";
			}
		}
		
		String query = "call updateSkater(" + skaterID + ", '" + updateField + "', '" + updateValue + "');";
			
		System.out.println("Updating skater " + updateName);
			
		executeSproc(query);
			
		System.out.println("Skater updated");
		
		System.out.println("Update another? (Y/N)");
		if (kb.nextLine().toUpperCase().charAt(0) == 'Y') {
			updateSkater();
		}
		else {
			mainLoop();
		}
	}
	
	public static void addSkater() throws SQLException {
		
		System.out.println();
		
		System.out.println("Enter the skater's information here");
		String[] skaterInfo = {"First name: ", "Last Name: ", "Email: ", "Phone Number: ", "Contacted (Y/N): ", "Preferred Position: ", "Referred by (First and Last Name): "};
		String[] skaterDetails = new String[7];
		
		for (int i = 0; i < skaterDetails.length; i++) {
			System.out.print(skaterInfo[i]);
			skaterDetails[i] = kb.nextLine();
		}
		
		System.out.println("Inserting record");
		
		String[] referName = new String[2];
		if (skaterDetails[6].contains(" ")) {
			referName = splitName(skaterDetails[6]);
		}
		else {
			referName[0] = "foo";
			referName[1] = "bar";
		}
		
		String referQuery = "select skaterID from skater where firstName = '" + referName[0] + "' and lastName = '" + referName[1] + "';";
		
		try {
			ResultSet refer = executeSelect(referQuery);
			int referID = 0;
			if (refer.isClosed() == false) {
				if (refer.next()) {
					referID = refer.getInt(1);
				}
			}
			
			if (skaterDetails[4].toUpperCase().charAt(0) == 'Y') {
				System.out.println("true");
				skaterDetails[4] = 	"1";
			}
			else {
				skaterDetails[4] = "0";
			}
			
			String query = "call insertSkater('" + skaterDetails[0] + "', '" + skaterDetails[1] + "', '" + skaterDetails[2] + "', '" + skaterDetails[3] + "', '" + skaterDetails[4] + "', '" + skaterDetails[5] + "', " + referID + ", 1);";
			
			executeSproc(query);
			
			System.out.println("Skater inserted");
			
			System.out.print("Insert another (Y/N): ");
			if (kb.nextLine().toUpperCase().charAt(0) == 'Y') {
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
