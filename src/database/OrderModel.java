package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import orders.OrderDetails;

public class OrderModel {
	
	private Connection connection;
	private Statement statement;
	private DataBase database;
	
	public OrderModel(DataBase db, Connection conn) {
		connection = conn;
		database = db;
	}
	
	public boolean isOrderPresent(String date, String store) {
		boolean present = false;
		String customerID = database.getCustomerID(store);
		String query = "select oid from placed_order "
						+ "where orderDate='"+date+"' and cid='"+customerID+"'";
		try {
			statement = connection.createStatement();
			System.out.println("isOrderPresent");
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				present = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return present;
	}
	
	public void createOrder(String store, String date) {
		String query = "begin transaction; "
				+ "insert into orders(total) values(0.0); "
				+ "insert into placed_order(cid, oid, order_date) "
					+ "values((select cid from customers where name='"+store+"'), "
					+ "last_insert_rowid(), '"+ date +"'); "
				+ "commit;";
		System.out.println(query);
		try {
			statement = connection.createStatement();
			statement.executeUpdate(query);

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		
	}
	
	public String getOrderID(String store, String date) {
		String orderID = "";
		String customerID = database.getCustomerID(store);
		String getID = "select oid from placed_order "
						+ "where cid='" + customerID + "' "
						+ "and order_date='" + date + "'";
		System.out.println(getID);
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(getID);
			while (rs.next()) {
				orderID = rs.getString("orderID");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orderID;
	}
	
	public void addToOrder(double quantity, String product, String date, String store) {
		boolean oExists = isOrderPresent(date, store); // check if order exists
		System.out.println("exists " + oExists);
		if (!oExists) {
			createOrder(store, date);
		}
		String productID = database.getProductID(product);
		System.out.println("pID " + productID);
		
		String orderID = getOrderID(store, date);
		System.out.println("orderID " + orderID);
		
		boolean pExists = database.isProductPresent(orderID, productID);
		if (!pExists) {
			addProductToOrder(orderID, productID, quantity);
			System.out.println("product was not found, adding new");
		} else if (quantity == 0.0){
			deleteFromOrder(orderID, productID);
			System.out.println("product was found, deleting");
		} else {
			modifyOrder(orderID, productID, quantity);
			System.out.println("product was found, modifying");
		}
	}
	
	private void addProductToOrder(String orderID, String productID, double quantity) {
		String query = "insert into orderDetails(oid, pid, quantity) " 
						+ "values('" + orderID + "', '" + productID + "', '" + quantity + "'" + ")";
		System.out.println(query);
		try {
			statement = connection.createStatement();
			statement.executeUpdate(query);

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	private void deleteFromOrder(String orderID, String productID) {
		String query = "delete from contained "
				+ "where oid='"+orderID+ "' "
				+ "and pid='" +productID+ "'";
		System.out.println(query);
		try {
			statement = connection.createStatement();
			statement.executeUpdate(query);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	private void modifyOrder(String orderID, String productID, double quantity) {
		String query = "update contained set quantity='" + quantity + "' "
				+ "where oid='" + orderID + "' "
				+ "and pid='" + productID + "'";
		System.out.println(query);
		try {
			statement = connection.createStatement();
			statement.executeUpdate(query);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public ArrayList<String> getOrdersList(String date) {
		ArrayList<String> stores = new ArrayList<String>();
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select cid from orders where order_date='"+date+"'");
			while (rs.next()) {
				String s = rs.getString("cid");
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
		String query = "select * from order "
						+ "where oid='" + orderID + "'";
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {

				String pID = rs.getString("productID");
				String pName = database.getProductName(pID);
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
