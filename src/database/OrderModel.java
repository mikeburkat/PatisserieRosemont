package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OrderModel {
	
	private Connection connection;
	private Statement statement;
	
	public OrderModel(Connection conn) {
		connection = conn;
	}
	
	public void createOrder(String store, String date) {
		Statement statement;
		
		String insert = ("begin transaction; "
				+ "insert into orders(total) values(0.0); "
				+ "insert into placed_order(cid, oid, order_date) "
					+ "values((select cid from customers where name='"+store+"'), "
					+ "last_insert_rowid(), '"+ date +"'); "
				+ "commit;");
		
		try {
			statement = connection.createStatement();
			statement.executeUpdate( insert );

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
	

}
