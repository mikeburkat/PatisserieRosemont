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
	
	public OrderModel(Connection conn) {
		connection = conn;
		database = DataBase.getInstance();
	}
	
	public boolean isOrderPresent(String date, String store) {
		boolean present = false;
		if (date == null || store == null) return false;
		
		String customerID = database.getCustomerID(store);
		
		String query = "select oid from placed_order "
						+ "where order_date='"+date+"' and cid='"+customerID+"'";
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				present = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("isOrderPresent for store: " + store + " and date: " + date + " ans: " + present);
		return present;
	}
	
	public void createOrder(String store, String date) {
		if (store == null || date == null) return;
		store = store.replace("'", "''");
		String query = "insert into orders(total) values(0.0); "
				+ "insert into placed_order(cid, oid, order_date) "
					+ "values((select cid from customers where name='"+store+"'), "
					+ "last_insert_rowid(), '"+ date +"');";
		System.out.println(query);
		try {
			statement = connection.createStatement();
			statement.executeUpdate(query);

		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void deleteOrder(String store, String date){
		String oid = getOrderID(store, date);
		if (oid != null) {
//			String query = "delete from contained where oid="+oid+"; "
//					+ "delete from placed_order where oid="+oid+"; "
//					+ "delete from orders where oid="+oid+";";
			String query = "delete from contained where oid="+oid+"; ";
			
			System.out.println(query);
			try {
				statement = connection.createStatement();
				statement.executeUpdate(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		updateOrderTotal(oid);
	}
	
	public String getOrderID(String store, String date) {
		String orderID = "";
		String customerID = database.getCustomerID(store);
		String query = "select oid from placed_order "
						+ "where cid='" + customerID + "' "
						+ "and order_date='" + date + "'";
		System.out.println(query);
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				orderID = rs.getString("oid");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orderID;
	}
	
	public void addToOrder(double quantity, String pid, String date, String store) {
		boolean oExists = isOrderPresent(date, store); // check if order exists
		System.out.println("exists " + oExists);
		if (!oExists) {
			createOrder(store, date);
		}
		System.out.println("pID " + pid);
		
		String oid = getOrderID(store, date);
		System.out.println("orderID " + oid);
		
		boolean pExists = database.isProductPresent(oid, pid);
		if (!pExists) {
			addProductToOrder(store, oid, pid, quantity);
			System.out.println("product was not found, adding new");
		} else if (quantity == 0.0) {
			deleteFromOrder(oid, pid);
			System.out.println("product was found, deleting");
		} else {
			modifyOrder(store, oid, pid, quantity);
			System.out.println("product was found, modifying");
		}
		updateOrderTotal(oid);
		
	}
	
	private void addProductToOrder(String store, String oid, String pid, double quantity) {
		String priceUsed = database.getCustomerPriceSet(store);
		String query = "insert into contained(oid, pid, quantity, price) " 
						+ "values(" + oid + ", " + pid + ", " + quantity 
						+ ", (select "+priceUsed+" from products where pid="+pid+"))";
		System.out.println(query);
		try {
			statement = connection.createStatement();
			statement.executeUpdate(query);

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	private void updateOrderTotal(String oid) {
		String query = "update orders set total=ifnull((select sum(sub_total) from contained where oid="+oid+"), 0.0) where oid="+oid;
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

	private void modifyOrder(String store, String oid, String pid, double quantity) {
		String priceUsed = database.getCustomerPriceSet(store);
		String query = "update contained set quantity='" + quantity + "', "
				+ "sub_total="+quantity+"*(select "+priceUsed+" from products where pid="+pid+")"
				+ "where oid='" + oid + "' "
				+ "and pid='" + pid + "'";
		System.out.println(query);
		try {
			statement = connection.createStatement();
			statement.executeUpdate(query);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public ArrayList<String> getStoresWhoOrderedOn(String date) {
		ArrayList<String> stores = new ArrayList<String>();
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select name from customers where cid in "
					+ "(select cid from placed_order as p join orders as o on p.oid=o.oid "
					+ "where order_date='"+date+"' and o.total > 0)");
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
	
	public ArrayList<OrderDetails> getOrderDetails(String store, String date, String orderBy, String search) {
		
		ArrayList<OrderDetails> od = new ArrayList<OrderDetails>();
		String oid = getOrderID(store, date);
		String query = "select details.pid, name, details.quantity, category, "
				+ "(case when original_id = 'NULL' then pid else original_id end) as original "
				+ "from (select pid, quantity from contained "
				+ "where oid='"+oid+"') details left join products using(pid) ";
		
		if (!search.isEmpty())
			query += "where name like '%" + search + "%' ";
		
		query += "union "
				+ "select pid, name, 0.0, category, "
				+ "(case when original_id = 'NULL' then pid else original_id end) as original "
				+ "from products "
				+ "where pid in (select pid from products "
				+ "where pid not in (select C.pid from contained C where oid='"+oid+"') "
				+ "and original_id not in (select C.pid from contained C where oid='"+oid+"') "
				+ "and updated = 'false' ";
		
		if (!search.isEmpty())
			query += "and name like '%" + search + "%' ";
		
		query += ") order by " + orderBy;
		
		System.out.println(query);
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				
				String pID = rs.getString("details.pid");
				String pName = rs.getString("name");
				String q = rs.getString("details.quantity");
				Double qd = Double.parseDouble(q);
				
				System.out.println("pid: " + pID + " name: " + pName + " quant: " + qd);
				od.add(new OrderDetails(qd, pName, pID));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return od;
	}

	public ArrayList<String[]> getOrderDetailsForPrinting(String store, String date) {
		String oid = getOrderID(store, date);
		String price = database.getCustomerPriceSet(store);
		String query = "select name, od.quantity, od.sub_total, " + price
				+ " from products, "
				+ "(select pid, quantity, sub_total "
				+ "from contained where oid="+oid+") od "
				+ "using(pid)";
		System.out.println(query);
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			if (rs.isBeforeFirst()) {
				ArrayList<String[]> result = new ArrayList<String[]>();
				
				while (rs.next()) {
					String name = rs.getString("name");
					String quant = rs.getString("od.quantity");
					String sub = rs.getString("od.sub_total");
					String unitPrice = rs.getString(price);
					String[] row = {quant, name, unitPrice, sub};
					result.add(row);
				}
				return result;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String addToOrder(String store, String date) {
		String oid = getOrderID(store, date);
		String query = "select * "
				+ "from orders "
				+ "where oid='" +oid+ "'";
		System.out.println(query);
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			if (rs.isBeforeFirst()) {
				rs.next();
				String total = rs.getString("total");
				return total;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
