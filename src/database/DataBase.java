package database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DataBase {
	private static Connection connection;
	private static DataBase instance;

	public DataBase() {
		connect();

//		// // clears and recreates the tables for when I was testing schema
//		clearDB();
//		initDB();
//		initProducts();
//		initStores();
//
//		// testing the database
//		addCustomer("mike", "monteral", "xxx boul", "t4w 4t4", "(412)-312-5675");
//		createOrder(1, "2014-04-22");
//		addProduct("bread", "chleb zytni", 2.00, "2014-04-22");
//		addProduct("bread", "chleb wieski", 3.00, "2014-04-22");
//		addToOrder(1, 1, 15);
//		addToOrder(1, 2, 25);
	}

	public static DataBase getInstance() {
		if (instance == null) {
			instance = new DataBase();
		}
		return instance;
	}

	public void addToOrder(int orderID, int productID, int quantity) {
		Statement statement;
		// TODO check if order exists, if it doesn't create new, before adding
		// to order.
		try {
			statement = connection.createStatement();
			statement.executeUpdate("insert into "
					+ "orderDetails(orderID, productID, quantity) " + "values("
					+ "'" + orderID + "', " + "'" + productID + "', " + "'"
					+ quantity + "'" + ")");

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	public void addProduct(String category, String name, double mtlPrice,
			String date) {
		Statement statement;
		try {
			statement = connection.createStatement();
			statement
					.executeUpdate("insert into "
							+ "products(category, name, montrealPrice, dateCreated, dateEffective) "
							+ "values(" + "'" + category + "', " + "'" + name
							+ "', " + "'" + mtlPrice + "', " + "'" + date
							+ "', " + "'" + date + "'" + ")");

		} catch (SQLException e) {
			System.err.println(e.getMessage());
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
	}

	public void createOrder(int customerID, String date) {
		Statement statement;
		// TODO check if order is already there, don't create a new.
		try {
			statement = connection.createStatement();
			statement.executeUpdate("insert into "
					+ "orders(customerID, orderDate) " + "values(" + "'"
					+ customerID + "', " + "'" + date + "'" + ")");

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

	}

	public ArrayList<String> getStoreNames(String city) {
		Statement statement;
		ArrayList<String> stores = new ArrayList<String>();
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from customers where city=\""+city+"\"");
			while (rs.next()) {
				String s = rs.getString("name");
				stores.add(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (String s: stores){
			System.out.println(s);
		} 
		return stores;
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
		DataLoader dl = new DataLoader(this);
		dl.importStores();
	}

	public void initProducts() {
		DataLoader dl = new DataLoader(this);
		dl.importProducts();
	}

	public static void connect() {
		// Create directory on first run
		String rosemontDir = System.getProperty("user.home")
				+ "/Patisserie Rosemont/";
		File rDir = new File(rosemontDir);
		if (!rDir.exists()) {
			boolean succesful = rDir.mkdir();
			if (succesful) {
				System.out.print("Created:" + rosemontDir);
			}
		}

		try {
			// create a database connection
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:"
					+ rosemontDir + "test.db");

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
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

	public boolean isOrderDetailsPresent(String date, String store) {
		Statement statement;
		boolean present = false;
		try {
			statement = connection.createStatement();
			System.out.println("isOrderDetailsPresent");
			ResultSet rs = statement.executeQuery("select * from orders where orderDate=\""+date+"\" and customerID=\""+store+"\"");
			while (rs.next()) {
				String s = rs.getString("orderDate");
				present = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return present;
	}

	public ArrayList<String> getProductList() {
		Statement statement;
		ArrayList<String> products = new ArrayList<String>();
		System.out.println("here");
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from products");
			while (rs.next()) {
				String p = rs.getString("name");
				products.add(p);
				System.out.println(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (String p: products){
			System.out.println(p);
		} 
		return products;
	}

	public void addToOrder(double quantity, String product, String date, String store) {
		System.out.println("MODIFY DB INSERT TO INSERT TO ORDER");
		// TODO finish this
	}
}
