package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

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

	public void addProduct(Product p) {
		String query = p.getInsertQuery();
		System.out.println(query);
		try {
			statement = connection.createStatement();
			statement.executeUpdate(query);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	private void updateProduct(Product p) {
		String query = p.getUpdateQuery();
		System.out.println(query);
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

	public Product getProduct(String name) {
		String query = "select * from products where name='"+name+"' and updated='false'";
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			if (rs.next()) {
				return new Product(rs);		
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void replace(Product oldP, Product p) {
		oldP.updated = true;
		oldP.dateReplaced = new Date();
		p.originalId = oldP.pid;
		this.addProduct(p);
		this.updateProduct(oldP);
	}

}
