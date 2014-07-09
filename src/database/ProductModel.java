package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ProductModel {

	private DataBase database;
	private Connection connection;
	private Statement statement;

	public ProductModel(Connection conn) {
		database = DataBase.getInstance();
		connection = conn;
	}
	
	public boolean isProductPresent(String orderID, String productID) {
		boolean present = false;
		String query = "select * from contained where oid='"+orderID+"' and pid='"+productID+"'";
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

	public void addProduct(String category, String name, double mtlPrice,
			String date) {
		String query = "insert into "
				+ "products(name, category, montreal_Price, dateCreated, dateEffective) "
				+ "values(" + "'" + category + "', " + "'" + name
				+ "', " + "'" + mtlPrice + "', " + "'" + date
				+ "', " + "'" + date + "'" + ")";
		try {
			statement = connection.createStatement();
			statement.executeUpdate(query);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	public String getProductID(String product) {
		String ProductID = "";
		String query = "select pid from products where name='" + product + "'";
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				ProductID = rs.getString("pid");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		System.out.println(ProductID);
		return ProductID;
	}

	public String getProductName(String pID) {
		String name = "";
		String query = "select name from products where pid='" + pID + "'";
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				name = rs.getString("name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return name;
	}
	
	public ArrayList<String[]> getProductList() {
		Statement statement;
		ArrayList<String[]> products = new ArrayList<String[]>();
		System.out.println("here");
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select name, pid from products");
			while (rs.next()) {
				String p = rs.getString("name");
				String pid = rs.getString("pid");
				String[] arr = {pid, p};
				products.add(arr);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (String[] p: products){
			System.out.println(p[0] + " : " + p[1]);
		} 
		return products;
	}

}
