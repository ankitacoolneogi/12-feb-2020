package employee;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.Connection;

public class EmployeeMain {
	PreparedStatement pstmt;
	Connection dbCon;

	public EmployeeMain() {
		try {
			dbCon = DriverManager.getConnection("jdbc:mysql://localhost:3307/mydatabase?serverTimezone=UTC", "root",
					"");
		} catch (SQLException e) {
			System.out.println("Error connecting database..." + e.getMessage());
		}

	}

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		Boolean bool = true;
		int choice;

		EmployeeMain emp = new EmployeeMain();

		while (bool) {
			System.out.print(
					"Enter your choice..\n1..Add employee\n2..Display employee\n3..Update \n4..Delete employee\n\t\tEnter option:: ");
			choice = sc.nextInt();
			switch (choice) {
			case 1:
				emp.addEmployee();
				break;
			case 2:
				emp.displayEmployee();
				break;
			case 3:
				emp.updatEmployee();
				break;
			case 4:
				emp.deleteEmployee();
				break;

			default:
				System.out.println("Invalid input..");
				break;
			}

			System.out.print("Do you want to continue??..press 1 to continue..: ");
			if (sc.nextInt() != 1)
				bool = false;
		}
	}

	void addEmployee() {
		Scanner sc = new Scanner(System.in);
		String query = "Insert into employee(name,gender,occupation) values (?,?,?)";
		try {
			pstmt = this.dbCon.prepareStatement(query);
			System.out.print("Enter name of employee:: ");
			String name = sc.next();
			pstmt.setString(1, name);
			System.out.print("Enter Gender of employee:: ");
			String gen = sc.next();
			pstmt.setString(2, gen);
			System.out.print("Enter Occupation:: ");
			pstmt.setString(3, sc.next());
			if (pstmt.executeUpdate() > 0)
				System.out.println("Insertion successful!!..");
			;

		} catch (SQLException e) {
			System.out.println("Error" + e.getMessage());
		}
	}

	void displayEmployee() {
		String query = "Select * from employee";
		try {
			pstmt = this.dbCon.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				System.out.println("Name : " + rs.getString("name") + "; ID : " + rs.getInt("id") + "; Gender : "
						+ rs.getString("gender") + "; Occupation :" + rs.getString("occupation"));
			}

		} catch (SQLException e) {
			System.out.println("Error" + e.getMessage());
		}

	}

	void updatEmployee() {
		Scanner sc = new Scanner(System.in);
		int id, choice;
		String temp  , val ;
		System.out.println("Enter the id of employee to be updated: ");
		id = sc.nextInt();
		System.out.println("Enter item to update..\n1..name\n2..gender\n3..Occupation\n\t\tEnter your choice:: ");
		choice = sc.nextInt();
		try {
			switch (choice) {
			case 1:
				sc.nextLine();
				System.out.println("Enter update name : ");
				val = sc.nextLine();
				temp = "name";
				break;
			case 2:
				sc.nextLine();
				System.out.println("Enter update gender: ");
				val = sc.nextLine();
				temp = "gender";
				break;
			case 3:
				sc.nextLine();
				System.out.println("Enter update occupation: ");
				val = sc.nextLine();
				temp = "occupation";
				break;
			default: System.out.println("Wrong Input..");
				temp="name";
				val="nil";

			}
			String query = "UPDATE employee SET " + temp + "= ? WHERE id = ?";
			pstmt = this.dbCon.prepareStatement(query);

			pstmt.setInt(2, id);
			pstmt.setString(1, val);
			if (pstmt.executeUpdate() > 0)
				System.out.println("Update successful!!..");

		} catch (SQLException e) {
			System.out.println("Error:  " + e.getMessage());
		}

	}

	void deleteEmployee() {
		Scanner sc = new Scanner(System.in);
		String query = "DELETE from employee WHERE id = ?";
		int id;
		try {
			pstmt = this.dbCon.prepareStatement(query);
			System.out.println("Enter the id of employee to be updated: ");
			id = sc.nextInt();
			pstmt.setInt(1, id);
			if(!pstmt.execute() )
				System.out.println("Deletion successful!!.");
			
			
		} catch (SQLException e) {
			System.out.println("Error:  " + e.getMessage());

		}

		
	}

}
