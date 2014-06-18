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
		instance = this;
		orderModel = new OrderModel(connection);
		customerModel = new CustomerModel(connection);
		productModel = new ProductModel(connection);
		// if running first time then uncomment the init()
//		init();
	}

	public static DataBase getInstance() {
		if (instance == null) {
			instance = new DataBase();
		}
		return instance;
	}
	
	public void init() {
		init = new DataInitialization(this, connection);
		
		init.clearDB(); // clears and recreates the tables
		init.runScript("DB_schema.sql"); // init the database schema
		
		// Load random datasets for testing.
		init.runScript("random_customers.sql");
		init.runScript("random_products.sql");
		init.runScript("random_order.sql");
		init.runScript("random_placed_order.sql");
		init.runScript("random_contained.sql");
//		init.initProducts();
//		init.initStores();
	}
	
	public void createOrder(String store, String date) {
		orderModel.createOrder(store, date);
	}
	
	public String getOrderID(String store, String date) {
		return orderModel.getOrderID(store, date);
	}
	
	public void addToOrder(double quantity, String product, String date, String store) {
		orderModel.addToOrder(quantity, product, date, store);
	}

	public void addProduct(String category, String name, double mtlPrice,
			String date) {
		productModel.addProduct(category, name, mtlPrice, date);
	}
	
	public String getProductID(String product) {
		return productModel.getProductID(product);
	}
	
	public String getProductName(String pID) {
		return productModel.getProductName(pID);
	}

	public void addCustomer(String name, String city, String address,
			String postal, String phone) {
		customerModel.addCustomer(name, city, address, postal, phone);
	}
	
	public String getCustomerID(String store) {
		return customerModel.getCustomerID(store);
	}
	
	public String getCustomerPriceSet(String store) {
		return customerModel.getCustomerPriceSet(store);
	}

	public ArrayList<String> getStoreNames(String city) {
		return customerModel.getStoreNames(city);
	}
	
	public boolean isOrderPresent(String date, String store) {
		return orderModel.isOrderPresent(date, store);
	}
	
	public boolean isProductPresent(String orderID, String productID) {
		return productModel.isProductPresent(orderID, productID);
	}
	
	public ArrayList<String> getProductList() {
		return productModel.getProductList();
	}

	public ArrayList<String> getOrdersList(String date) {
		return orderModel.getOrdersList(date);
	}

	public ArrayList<OrderDetails> getOrderDetails(String store, String date, String orderBy) {
		return orderModel.getOrderDetails(store, date, orderBy);
	}
	
	public void deleteOrder(String store, String date) {
		orderModel.deleteOrder(store, date);
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
					+ rosemontDir + "patis_rosemont.db");

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

	

}
