import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * The Projects class contains all the methods used in the 'PoisedMenu' which
 * pertain to adding, editing, finalizing, or viewing various projects for the
 * Poised Management System. It inherits input check methods from the superclass
 * 'PoisedInputChecks' to validate user input.
 * <p>
 * 
 * @author Nhlakanipho Hlophe.
 */
public class Projects extends PoisedInputChecks {
	/**
	 * This method allows user to create a new project object, which is added to the
	 * 'main_project_info' table in the PoisePMS database.
	 * <p>
	 * It prompts users to enter various information related to a new project
	 * object, and then connects to the external database to update project
	 * information. The 'PoisedMenu' class calls on this method when the user
	 * chooses to add a new project to the system.
	 * <p>
	 * 
	 * @param statement statement object linked to connection to perform SQL
	 *                  commands
	 * @throws SQLException occurs if there is an error accessing the database
	 *                      information
	 */
	void addProject(Statement statement) throws SQLException {

		System.out.println("\nPlease add a new project number: ");
		int proj_num = intCheck("new project number");

		System.out.println("\nPlease add a new project name: ");
		String proj_name = stringCheck("new project name: ");

		System.out.println("\nPlease add a building type: ");
		String build_type = stringCheck("building type");

		System.out.println("\nPlease add a address for the project: ");
		String address = stringCheck("project address");

		System.out.println("\nPlease add an ERF number: ");
		String erf_num = stringCheck("ERF number");

		System.out.println("\nPlease add a total fee for the project: ");
		float total_fee = floatCheck("total fee");

		System.out.println("\nPlease add the current amount paid for the project: ");
		float amount_paid = floatCheck("amount paid");

		System.out.println("Please add a deadline for the project (e.g. 3-Dec-2020): ");
		String deadline = stringCheck("deadline");

		String finalise = "No";
		String comp_date = "None";

		/*
		 * The main_project_info table in the 'PoisePMS' database is then updated. The
		 * information inputed by the user is inserted into the appropriate columns,
		 * thus creating and storing a new project object.
		 */
		statement.executeUpdate("INSERT INTO main_project_info VALUES (" + proj_num + ", " + "'" + proj_name + "'"
				+ ", " + "'" + build_type + "'" + ", " + "'" + address + "'" + ", " + "'" + erf_num + "'" + ", "
				+ total_fee + ", " + amount_paid + ", " + "'" + deadline + "'" + ", " + "'" + finalise + "'" + ", "
				+ "'" + comp_date + "'" + ");");

		// A successful message is displayed and the user can then view the updated
		// project list.
		System.out.println("\nYour new project was successfully added. View updated projects below: \n");
		printAllFromTable(statement);

	}

	/**
	 * This method allows users to edit project information, relating to the project
	 * due date and total amount paid.
	 * <p>
	 * It displays a sub-menu to the user with two edit choices and executes the
	 * action depending on their choice. The edited information is then written
	 * under the corresponding column of the 'main_project_info' table in the
	 * external PoisePMS database.
	 * <p>
	 * 
	 * @param statement statement object linked to connection to perform SQL
	 *                  commands
	 * @throws SQLException occurs if there is an error accessing the database
	 *                      information
	 */
	void editProject(Statement statement) throws SQLException {

		// The user is prompted to enter a project number to edit.
		System.out.println("Please enter the number of the project you wish to update: \n");
		int proj = intCheck("project number");

		System.out.println("Would you like to:" + "\n1. Edit the project due date or"
				+ "\n2. Edit the total amount paid of the fee to date?" + // Edit options displayed.
				"\nChoose either 1 or 2");

		int editChoice = intCheck("edit choice");

		/*
		 * If the user selects option 1, they are prompted to enter a new deadline. The
		 * new value is then written to the main_project_info table with the
		 * executeUpdate() SQL statement.
		 */
		if (editChoice == 1) {
			System.out.println("Please enter a new project deadline: ");
			String newDate = stringCheck("new project deadline");

			statement.executeUpdate(
					"UPDATE main_project_info SET Deadline = '" + newDate + "'" + " WHERE Project_Num = " + proj);

			// A successful message is displayed to the user and then they are able to view
			// the list of updated projects.
			System.out.println("Your project info has been successfully updated. View projects below: ");
			printAllFromTable(statement);

			/*
			 * If the user selects option 2, they are prompted to enter a new amount paid.
			 * The new value is then written to the main_project_info table with the
			 * executeUpdate() SQL statement.
			 */
		} else if (editChoice == 2) {
			System.out.println("Please enter a new total amount paid: ");
			float newAmount = floatCheck("new amount paid");

			statement.executeUpdate(
					"UPDATE main_project_info SET Amount_Paid = " + newAmount + " WHERE Project_Num = " + proj);

			// A successful message is displayed to the user and then they are able to view
			// the list of updated projects.
			System.out.println("Your project info has been successfully updated. View projects below: ");
			printAllFromTable(statement);

		}
	}

	/**
	 * This method allows users to finalize a project located in the
	 * main_project_info table in the external 'PoisePMS' database.
	 * <p>
	 * The user is prompted to enter a project number to locate the project. If
	 * there is an outstanding amount on the project, an invoice is generated and
	 * displayed with customer details. Otherwise, the project is just marked as
	 * finalized and a completion date is added.
	 * <p>
	 * 
	 * @param statement statement object linked to connection to perform SQL
	 *                  commands
	 * @throws SQLException occurs if there is an error accessing the database
	 *                      information
	 */
	void finaliseProject(Statement statement) throws SQLException {

		// The user is prompted to enter a project number to finalize.
		System.out.println("Please enter the number of the project that you wish to finalise: ");
		int projNum = intCheck("project number");

		// Selecting the Total_Fee and Amount_Paid columns from the table.
		ResultSet results2 = statement
				.executeQuery("SELECT Total_Fee, Amount_Paid FROM main_project_info WHERE Project_Num = " + projNum);
		float totalFees = 0;
		float amountPaid = 0;

		// Iterating through the columns and storing the two float numbers into
		// corresponding variables.
		while (results2.next()) {
			totalFees = results2.getFloat("Total_Fee");
			amountPaid = results2.getFloat("Amount_Paid");

		}
		// If the project has been paid in full, the amount paid will equal the total
		// fee for the project.
		// This means no invoice needs to be generated.
		if (totalFees == amountPaid) {
			System.out.println("This project has already been paid in full. No invoice to be generated.");

			// The user is then prompted to enter a completion date, which is written into
			// the 'Completion_Date' column
			// in the main_project_info table with the executeUpdate() statement.
			System.out.println("Please add a completion date for the project: ");
			String compDate = stringCheck("completion date");

			// Completion date added to user's chosen project by project number.
			statement.executeUpdate("UPDATE main_project_info SET Completion_Date = " + "'" + compDate + "'"
					+ " WHERE Project_Num = " + projNum);

			// The project is then marked as finalized by writing 'Yes' to the finalize
			// column in the table.
			statement.executeUpdate("UPDATE main_project_info SET Finalised = 'Yes' WHERE Project_Num = " + projNum);

			// A successful message is displayed and the user is able to view the updated
			// project list.
			System.out.println("Your project has been successfully finalised. View projects below: ");
			printAllFromTable(statement);

			/*
			 * If there is still an amount outstanding on the project, an invoice will be
			 * generated. A 'Persons' object is then created to access the 'displayPerson()
			 * method from the Persons class. The customer details for the selected project
			 * are then displayed to the user for the invoice.
			 */
		} else if (totalFees != amountPaid) {
			System.out.println(
					"There is still an outstanding amount to be paid for this project. View your invoice below: \n");

			Persons customer = new Persons();
			customer.displayCustomer(statement, projNum);

			// Added to the customer info, is the amount owing on the project.
			System.out.println("\nAmount Outstanding: R" + (totalFees - amountPaid));

			// The user is then prompted to enter a completion dated for the project.
			System.out.println("\nPlease add a completion date for the project: ");
			String compDate = stringCheck("completion date");

			// The date entered by the user is written to the main_project_info table under
			// the 'Completion_Date' column.
			statement.executeUpdate("UPDATE main_project_info SET Completion_Date = " + "'" + compDate + "'"
					+ " WHERE Project_Num = " + projNum);

			// The project is then marked as finalized by writing 'Yes' to the finalize
			// column in the table.
			statement.executeUpdate("UPDATE main_project_info SET Finalised = 'Yes' WHERE Project_Num = " + projNum);

			// A successful message is displayed and the user is able to view the updated
			// project list.
			System.out.println("Your project has been successfully finalised. View projects below: ");
			printAllFromTable(statement);

		}
	}

	/**
	 * This method allows users to view all project objects that are incomplete
	 * (i.e. not finalized and no completion date added) in the main_project_info
	 * table in the external 'PoisePMS' database.
	 * <p>
	 * 
	 * @param statement statement object linked to connection to perform SQL
	 *                  commands
	 * @throws SQLException occurs if there is an error accessing the database
	 *                      information
	 */
	void viewIncomplete(Statement statement) throws SQLException {

		System.out.println("\nPlease view all incomplete projects below: \n");

		ResultSet results3 = statement
				.executeQuery("SELECT * FROM main_project_info WHERE Finalised = 'No' AND Completion_Date = 'None'");

		// All incomplete projects are displayed using a table iterator.
		while (results3.next()) {
			System.out.println("Project Number: \t" + results3.getInt("Project_Num") + "\nProject Name: \t"
					+ results3.getString("Project_Name") + "\nBuilding Type: \t" + results3.getString("Building_Type")
					+ "\nPhysical Address: " + results3.getString("Address") + "\nERF Number: \t"
					+ results3.getString("ERF_Num") + "\nTotal Fee: \tR" + results3.getFloat("Total_Fee")
					+ "\nAmount Paid: \t" + results3.getFloat("Amount_Paid") + "\nDeadline: \t"
					+ results3.getString("Deadline") + "\nFinalised: \t" + results3.getString("Finalised")
					+ "\nCompletion Date: " + results3.getString("Completion_Date") + "\n");
		}
	}

	/**
	 * This method allows users to view all project objects that are overdue in the
	 * main_prject_info table in the external 'PoisePMS' database.
	 * <p>
	 * When called on, the method runs through all deadlines of incomplete projects
	 * and compares the deadline date with the current date. If overdue, the project
	 * is displayed in an easy-to-read format. If no overdue projects are present,
	 * an appropriate error message is displayed to the user.
	 * <p>
	 * 
	 * @param statement statement object linked to connection to perform SQL
	 *                  commands
	 * @throws SQLException   occurs if there is an error accessing the database
	 *                        information
	 * @throws ParseException occurs if a date string is in the wrong format to be
	 *                        parsed
	 */
	void viewOverdue(Statement statement) throws SQLException, ParseException {

		boolean projCheck = false;
		String[] info;
		int[] months = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
		String[] monthsofYear = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
		int monthNum = 0;

		// Overdue projects will be incomplete, therefore only the deadline date info
		// from columns of incomplete projects are located.
		ResultSet results4 = statement.executeQuery(
				"SELECT Deadline FROM main_project_info WHERE Finalised = 'No' AND Completion_Date = 'None'");

		// Iterating through the deadline dates in the incomplete projects to check if
		// they are overdue.
		while (results4.next()) {

			// The deadline date in the project is stored in the string variable
			// 'date_info'.
			// This variable is then split into an array called 'info' by removing the dash
			// character '-' from the date (e.g. 5-Dec-2021)
			// The first indexed value of 'info' is then parsed and stored into an integer
			// variable called 'day'.
			String dateInfo = results4.getString("Deadline");
			info = dateInfo.split("-");
			int day = Integer.parseInt(info[0]);

			/*
			 * The second indexed value from the info array is stored in a variable called
			 * 'monthInfo'. monthInfo is then split further to store only three letters of
			 * the month name into string variable 'month' (e.g. 'Dec'). A year variable is
			 * also created and assigned the parsed value from the third index in 'info'
			 * array.
			 */
			String monthInfo = info[1];
			String month = (monthInfo.substring(0, 2));
			int year = Integer.parseInt(info[2]);

			/*
			 * A for loop is then used to compare the substring 'month' with the
			 * monthsofYear string array. Once matched with an abbreviated month of the
			 * year, the corresponding number from the integer array 'months' is stored in
			 * the 'monthNum' variable, for use as date info.
			 */
			for (int index = 0; index < monthsofYear.length; index++) {
				if (month.equalsIgnoreCase(monthsofYear[index])) {
					monthNum = months[index];

				}
			}
			// Getting the current date and storing it as a string.
			String current = "" + java.time.LocalDate.now();

			// Creating a new simple date format object.
			SimpleDateFormat dateObj = new SimpleDateFormat("yyyy-MM-dd");

			// Dates d1 and d2 are then created by parsing string info from 'current' date
			// and date info gathered from the file above.
			java.util.Date d1 = dateObj.parse(current);

			java.util.Date d2 = dateObj.parse(day + "-" + monthNum + "-" + year);

			// If the current date has passed the deadline for the project, it is overdue.
			// The proj_Check is set to 'true' and all of the columns for that project are
			// selected and displayed.
			if (d1.compareTo(d2) < 0) {
				projCheck = true;

				System.out.println("\nPlease view all overdue projects below: \n");
				ResultSet results5 = statement
						.executeQuery("SELECT * from main_project_info WHERE Deadline = '" + dateInfo + "'");

				// Iterating and displaying all info related to the overdue project.
				while (results5.next()) {
					System.out.println("Project Number: \t" + results5.getInt("Project_Num") + "\nProject Name: \t"
							+ results5.getString("Project_Name") + "\nBuilding Type: \t"
							+ results5.getString("Building_Type") + "\nPhysical Address: "
							+ results5.getString("Address") + "\nERF Number: \t" + results5.getString("ERF_Num")
							+ "\nTotal Fee: \tR" + results5.getFloat("Total_Fee") + "\nAmount Paid: \t"
							+ results5.getFloat("Amount_Paid") + "\nDeadline: \t" + results5.getString("Deadline")
							+ "\nFinalised: \t" + results5.getString("Finalised") + "\nCompletion Date: "
							+ results5.getString("Completion_Date") + "\n");
				}
				// However, if there are no overdue projects, proj_Check is set to 'false'.
			} else {
				projCheck = false;
			}
			// If proj_Check is set at false after the projects are all checked, an
			// appropriate message is displayed to the user.
		}
		if (projCheck == false) {
			System.out.println("There are no overdue projects listed on the system.");
		}
	}

	/**
	 * This method allows users to find project objects from the main_project_info
	 * table in the external 'PoisePMS' database by either entering the project name
	 * or number.
	 * <p>
	 * Using either name or number, the project is then located from the external
	 * database and displayed in an easy-to-read-format.
	 * <p>
	 * 
	 * @param statement statement object linked to connection to perform SQL
	 *                  commands
	 * @throws SQLException occurs if there is an error accessing the database
	 *                      information
	 */
	void findProject(Statement statement) throws SQLException {

		System.out.println(
				"Would you like to search for your project by 1.) project number or 2.) project name? \nPlease select either 1 or 2.");
		int search_Choice = intCheck("Number search option");

		/*
		 * If they choose option 1, they are prompted to enter the project number. Once
		 * the number has been entered, the program selects all info related to that
		 * project to display to the user.
		 */
		if (search_Choice == 1) {
			System.out.println("\nPlease enter the number of the project you wish to locate: ");
			int projNum = intCheck("project number");

			System.out.println("\nPlease view your project details below: \n");

			ResultSet results6 = statement
					.executeQuery("SELECT * from main_project_info WHERE Project_Num = " + projNum);

			// Iterating through project info by column of the project selected by the user.
			while (results6.next()) {
				System.out.println("Project Number: \t" + results6.getInt("Project_Num") + "\nProject Name: \t"
						+ results6.getString("Project_Name") + "\nBuilding Type: \t"
						+ results6.getString("Building_Type") + "\nPhysical Address: " + results6.getString("Address")
						+ "\nERF Number: \t" + results6.getString("ERF_Num") + "\nTotal Fee: \tR"
						+ results6.getFloat("Total_Fee") + "\nAmount Paid: \t" + results6.getFloat("Amount_Paid")
						+ "\nDeadline: \t" + results6.getString("Deadline") + "\nFinalised: \t"
						+ results6.getString("Finalised") + "\nCompletion Date: "
						+ results6.getString("Completion_Date") + "\n");
			}
			/*
			 * If the user selects option 2, they are prompted to enter the project name.
			 * Once entered, the program selects all info related to that project to display
			 * to the user.
			 */
		} else if (search_Choice == 2) {
			System.out.println("\nPlease enter the name of the project you wish to locate: ");
			String projName = stringCheck("project name");

			System.out.println("\nPlease view your project details below: \n");

			ResultSet results7 = statement
					.executeQuery("SELECT * from main_project_info WHERE Project_Name = '" + projName + "'");

			// Iterating through project info by column of the project selected by the user.
			while (results7.next()) {
				System.out.println("Project Number: \t" + results7.getInt("Project_Num") + "\nProject Name: \t"
						+ results7.getString("Project_Name") + "\nBuilding Type: \t"
						+ results7.getString("Building_Type") + "\nPhysical Address: " + results7.getString("Address")
						+ "\nERF Number: \t" + results7.getString("ERF_Num") + "\nTotal Fee: \tR"
						+ results7.getFloat("Total_Fee") + "\nAmount Paid: \t" + results7.getFloat("Amount_Paid")
						+ "\nDeadline: \t" + results7.getString("Deadline") + "\nFinalised: \t"
						+ results7.getString("Finalised") + "\nCompletion Date: "
						+ results7.getString("Completion_Date") + "\n");
			}
		}
	}

	/**
	 * This method displays all information from the main_project_info table in the
	 * PoisePMS database when called on.
	 * <p>
	 * 
	 * @param statement statement object linked to connection to perform SQL
	 *                  commands
	 * @throws SQLException occurs if there is an error accessing the database
	 *                      information
	 */
	void printAllFromTable(Statement statement) throws SQLException {

		// Selecting all information (i.e. all rows) from the main_project_info table.
		ResultSet results = statement.executeQuery("SELECT * FROM main_project_info");

		// Iterating through info in each column to display to the user.
		while (results.next()) {
			System.out.println("Project Number: \t" + results.getInt("Project_Num") + "\nProject Name: \t"
					+ results.getString("Project_Name") + "\nBuilding Type: \t" + results.getString("Building_Type")
					+ "\nPhysical Address: " + results.getString("Address") + "\nERF Number: \t"
					+ results.getString("ERF_Num") + "\nTotal Fee: \tR" + results.getFloat("Total_Fee")
					+ "\nAmount Paid: \t" + results.getFloat("Amount_Paid") + "\nDeadline: \t"
					+ results.getString("Deadline") + "\nFinalised: \t" + results.getString("Finalised")
					+ "\nCompletion Date: " + results.getString("Completion_Date") + "\n");
		}
	}

}
