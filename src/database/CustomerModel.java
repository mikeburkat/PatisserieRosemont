package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerModel {

	private DataBase database;
	private Connection connection;
	private Statement statement;

	public CustomerModel(Connection conn) {
		database = DataBase.getInstance();
		connection = conn;
	}
	

	public void addCustomer(Customer c) {
		String query = c.insertQuery();
		System.out.println(query);
		try {
			statement = connection.createStatement();
			statement.executeUpdate(query);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	private void updateCustomer(Customer c) {
		String query = c.updateQuery();
		System.out.println(query);
		try {
			statement = connection.createStatement();
			statement.executeUpdate(query);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	public String getCustomerID(String store) {
		String customerID = "";
		store = store.replace("'", "''");
		String query = "select cid from customers " + "where name='" + store + "'";
		System.out.println(query);
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				customerID = rs.getString("cid");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(customerID);
		return customerID;
	}

	public String getCustomerPriceSet(String store) {
		String priceUsed = "";
		store = store.replace("'", "''");
		String query = "select price_used from customers where name='" + store + "'";
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				priceUsed = rs.getString("price_used");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(priceUsed);
		return priceUsed;

	}

	public ArrayList<String> getStoreNames(String city) {
		ArrayList<String> stores = new ArrayList<String>();
		String query = "select name from customers " + "where city='" + city
				+ "'";
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				String s = rs.getString("name");
				stores.add(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (String s : stores) {
			System.out.println(s);
		}
		return stores;
	}

	public ResultSet getCustomerDetails(String store) {
		store = store.replace("'", "''");
		String query = "select * from customers " + "where name='" + store + "'";
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			if (rs.isBeforeFirst()) {
				return rs;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}


	public Customer getCustomer(String store) {
		String query = "select * from customers " + "where name='" + store + "'";
		System.out.println(query);
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			if (rs.next()) {
				return new Customer(rs);
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void replace(Customer dbC, Customer c) {
		c.cid = dbC.cid;
		this.updateCustomer(c);
	}
}
