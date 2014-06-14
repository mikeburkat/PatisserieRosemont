package database;

import java.sql.Connection;

public class ProductModel {
private Connection connection;
	
	public ProductModel(Connection conn) {
		connection = conn;
	}

}
