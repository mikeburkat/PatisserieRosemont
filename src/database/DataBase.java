package database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import orders.OrderDetails;

public class DataBase {
	private static Connection connection;
	private static DataBase instance;
	private DataInitialization init;
	private Statement statement;
	
	private OrderModel orderModel;
	private CustomerModel customerModel;
	private ProductModel productModel;

	private DataBase() {
		connect();
		orderModel = new OrderModel(connection);
		customerModel = new CustomerModel(connection);
		productModel = new ProductModel(connection);
		
	}

	public static DataBase getInstance() {
		if (instance == null) {
			instance = new DataBase();
		}
		return instance;
	}
	
	public void init() {
		init = new DataInitialization(this, connection);
		// // clears and recreates the tables
		init.clearDB();
//		init.initDB();
		init.initNewDB();
//		init.initProducts();
//		init.initStores();
	}
	
	public void createOrder(String store, String date) {
		Statement statement;
		String customerID = getCustomerID(store);
		System.out.println("insert into "
					+ "orders(customerID, orderDate) " + "values(" + "'"
					+ customerID + "', " + "'" + date + "'" + ")");
		try {
			statement = connection.createStatement();
			statement.executeUpdate("insert into "
					+ "orders(customerID, orderDate) " + "values(" + "'"
					+ customerID + "', " + "'" + date + "'" + ")");

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		
	}
	
	public String getOrderID(String store, String date) {
		String orderID = "";
		String customerID = getCustomerID(store);
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from orders where orderDate=\""+date+"\" and customerID=\""+customerID+"\"");
			while (rs.next()) {
				orderID = rs.getString("orderID");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		return orderID;
	}

	private void addToOrder(String orderID, String productID, double quantity) {
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
	
	public void addToOrder(double quantity, String product, String date, String store) {
		boolean oExists = isOrderPresent(date, store); // check if order exists
		System.out.println("exists " + oExists);
		String productID = getProductID(product);
		System.out.println("pID " + productID);
		if (!oExists) {
			createOrder(store, date);
		}
		String orderID = getOrderID(store, date);
		System.out.println("orderID " + orderID);
		
		boolean pExists = isProductPresent(orderID, productID);
		if (!pExists) {
			addToOrder(orderID, productID, quantity);
			System.out.println("product was not found, adding new");
		} else if (quantity == 0.0){
			deleteFromOrder(orderID, productID);
			System.out.println("product was found, deleting");
		} else {
			modifyOrder(orderID, productID, quantity);
			System.out.println("product was found, modifying");
		}
	}

	private void deleteFromOrder(String orderID, String productID) {
		try {
			statement = connection.createStatement();
			
			System.out.println("delete from orderDetails where orderID='"
							+ orderID + "' and productID='" + productID + "'");
			
			statement.executeUpdate("delete from orderDetails where orderID='"
							+ orderID + "' and productID='" + productID + "'");

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	private void modifyOrder(String orderID, String productID, double quantity) {
		try {
			statement = connection.createStatement();
			
			System.out.println("update orderDetails set quantity='" + quantity + "' "
					+ "where orderID='" + orderID + "' and where productID='" + productID + "'");
			
			statement.executeUpdate("update orderDetails set quantity='" + quantity + "' "
					+ "where orderID='" + orderID + "' and productID='" + productID + "'");

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
	
	public String getProductID(String product) {
		String ProductID = "";
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from products where name=\""+product+"\"");
			while (rs.next()) {
				ProductID = rs.getString("productID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(ProductID);
		return ProductID;
	}
	
	private String getProductName(String pID) {
		String name = "";
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from products where productID=\""+pID+"\"");
			while (rs.next()) {
				name = rs.getString("name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return name;
	}

	public void addCustomer(String name, String city, String address,
			String postal, String phone) {
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
	
	public String getCustomerID(String store) {
		String customerID = "";
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from customers where name=\""+store+"\"");
			while (rs.next()) {
				customerID = rs.getString("customerID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(customerID);
		return customerID;
	}

	public ArrayList<String> getStoreNames(String city) {
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

	public boolean isOrderPresent(String date, String store) {
		String customerID = getCustomerID(store);
		boolean present = false;
		try {
			statement = connection.createStatement();
			System.out.println("isOrderPresent");
			ResultSet rs = statement.executeQuery(""
					+ "select * from orders where orderDate=\""+date+"\" and customerID=\""+customerID+"\"");
			while (rs.next()) {
				present = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return present;
	}
	
	public boolean isProductPresent(String orderID, String productID) {
		boolean present = false;
		try {
			statement = connection.createStatement();
			System.out.println("isOrderPresent");
			ResultSet rs = statement.executeQuery("select * from orderDetails where orderID=\""+orderID+"\" and productID=\""+productID+"\"");
			while (rs.next()) {
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
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (String p: products){
			System.out.println(p);
		} 
		return products;
	}


	public ArrayList<String> getOrdersList(String date) {
		ArrayList<String> stores = new ArrayList<String>();
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from orders where orderDate=\""+date+"\"");
			while (rs.next()) {
				String s = rs.getString("customerID");
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

	public ArrayList<OrderDetails> getOrderDetails(String store, String date) {
		String orderID = getOrderID(store, date);
		ArrayList<OrderDetails> od = new ArrayList<OrderDetails>();
		
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from orderDetails where orderID=\""+orderID+"\"");
			while (rs.next()) {
				
				String pID = rs.getString("productID");
				String pName = getProductName(pID);
				String q = rs.getString("quantity");
				Double qd = Double.parseDouble(q);
				
				System.out.println("pID " + pID + " q " + q);
				od.add(new OrderDetails(qd, pName));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return od;
	}

	
}
