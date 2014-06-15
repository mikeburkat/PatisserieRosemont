package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CustomerModel {

	private DataBase database;
	private Connection connection;
	private Statement statement;
	
	public CustomerModel(Connection conn) {
		database = DataBase.getInstance();
		connection = conn;
	}
	
	public void addCustomer(String name, String city, String address,
			String postal, String phone) {
		String query = "insert into "
				+ "customers(name, city, address, postal_code, phone_num) "
				+ "values(" + "'" + name + "', " + "'" + city + "', " + "'"
				+ address + "', " + "'" + postal + "', " + "'" + phone
				+ "'" + ")";
		try {
			statement = connection.createStatement();
			statement.executeUpdate(query);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public String getCustomerID(String store) {
		String customerID = "";
		String query = "select cid from customers "
				+ "where name='" + store + "'";
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
	
	public ArrayList<String> getStoreNames(String city) {
		ArrayList<String> stores = new ArrayList<String>();
		String query = "select name from customers "
						+ "where city='"+city+"'";
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
		for (String s: stores){
			System.out.println(s);
		} 
		return stores;
	}

}
