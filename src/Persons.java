import java.sql.*;

/**
 * The Persons class contains a method to view a customer's details relating to
 * a specific project in an easy-to-read format.
 * <p>
 * The method in this class is used in the Projects class to create a customer
 * object and display their details to generate an invoice.
 * 
 * @author Nhlakanipho Hlophe.
 */

public class Persons {

	/**
	 * This method enables a customer object to be displayed in an easy-to-read
	 * format.
	 * <p>
	 * It selects a customer's details from the 'Project_Person_Details' table in
	 * the external 'PoisePMS' database by locating a specific project number, and
	 * then displays the necessary information in a clear format to the user.
	 * <p>
	 * 
	 * @param statement statement object linked to connection to perform SQL
	 *                  commands
	 * @param proj_num  proj_num an integer entered by the user to locate a specific
	 *                  project object
	 * @throws SQLException occurs if there is an error accessing the database
	 *                      information
	 */
	public void displayCustomer(Statement statement, int proj_num) throws SQLException {

		ResultSet results1 = statement.executeQuery(
				"SELECT Name, Contact_Num, Physical_Address, Email_Address FROM Project_Person_Details WHERE Project_Num = "
						+ proj_num + " AND Person_Type = 'Customer'");

		while (results1.next()) { // Customer details displayed using iterator in table.
			System.out.println("\nCustomer Name: " + results1.getString("Name") + "\nContact Number: "
					+ results1.getInt("Contact_Num") + "\nPhysical Address: " + results1.getString("Physical_Address")
					+ "\nEmail Address: " + results1.getString("Email_Address"));

		}
	}
}
