package database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {
	private Connection connection;

	public DataBase() {

		// Create directory on first run
		String rosemontDir = System.getProperty("user.home")
				+ "/Patisserie Rosemont";
		File rDir = new File(rosemontDir);
		if (!rDir.exists()) {
			boolean succesful = rDir.mkdir();
			if (succesful) {
				System.out.print("Created:" + rosemontDir);
			}
		}

		try {
			// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:"
					+ rosemontDir + "/test.db");

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		// initDB();
		addCustomer("mike", "monteral", "xxx boul", "t4w 4t4", "(412)-312-5675");
	}

	public void close() {
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			// connection close failed.
			System.err.println(e);
		}
	}

	public void addCustomer(String name, String city, String address,
			String postal, String phone) {
		Statement statement;
		try {
			statement = connection.createStatement();
			statement.executeUpdate("insert into "
					+ "customers(name, city, address, postalcode, phone) "
					+ "values(" + "'" + name + "', " + "'" + city + "', " + "'"
					+ address + "', " + "'" + postal + "', " + "'" + phone
					+ "'" + ")");

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		close();
	}

	public void clearDB() {
		Statement statement;
	
		try {
			statement = connection.createStatement();
			statement.executeUpdate("drop table if exists customers");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void initDB() {
		Statement statement;
		try {
			statement = connection.createStatement();
			
			statement.executeUpdate("create table customers ("
					+ "customerID integer primary key autoincrement, "
					+ "name varchar(100) not null, "
					+ "city varchar(50) not null, " + "address varchar(200), "
					+ "postalcode varchar(7), " + "phone varchar(14) " + ")");
			
			
			
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
