package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DataInitialization {
	
	private Connection connection;
	private DataBase db;
	
	public DataInitialization (DataBase database, Connection conn) {
		db = database;
		connection = conn;
	}
	
	public void initDB() {
		Statement statement;
		try {
			statement = connection.createStatement();

			// creates the customers table
			statement.executeUpdate("create table customers ("
					+ "customerID integer primary key autoincrement, "
					+ "name varchar(100) not null, "
					+ "city varchar(50) not null, " + "address varchar(200), "
					+ "postalcode varchar(7), " + "phone varchar(14) " + ")");

			// creates the orders table
			statement.executeUpdate("create table orders ("
					+ "orderID integer primary key autoincrement, "
					+ "customerID integer references customers, "
					+ "orderDate date not null, " + "total double, "
					+ "paid boolean default false" + ")");

			// creates the products table
			statement.executeUpdate("create table products ("
					+ "productID integer primary key autoincrement, "
					+ "name varchar(75) not null, "
					+ "category varchar(50) not null, "
					+ "montrealPrice double, " + "ottawaPrice double, "
					+ "kosciolPrice double, " + "krazyKrisPrice double, "
					+ "cecilPrice double, " + "rosemontStorePrice double, "
					+ "updated boolean default false, "
					+ "dateCreated date not null, "
					+ "dateEffective date not null, " + "dateEnd date, "
					+ "dateReplaced date, " + "originalID integer" + ")");

			// creates the order details table
			statement.executeUpdate("create table orderDetails ("
					+ "orderID integer references orders, "
					+ "productID integer references products, "
					+ "quantity double, " + "subtotal double" + ")");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void initStores() {
		DataLoader dl = new DataLoader(db);
		dl.importStores();
	}

	public void initProducts() {
		DataLoader dl = new DataLoader(db);
		dl.importProducts();
	}
	
	public void clearDB() {
		Statement statement;
		try {
			statement = connection.createStatement();
			statement.executeUpdate("drop table if exists customers");
			statement.executeUpdate("drop table if exists orders");
			statement.executeUpdate("drop table if exists orderDetails");
			statement.executeUpdate("drop table if exists products");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
