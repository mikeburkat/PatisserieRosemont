package database;

import java.sql.Connection;

public class CustomerModel {
private Connection connection;
	
	public CustomerModel(Connection conn) {
		connection = conn;
	}

}
