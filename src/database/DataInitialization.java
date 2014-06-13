package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DataInitialization {

	private Connection connection;
	private DataBase db;

	public DataInitialization(DataBase database, Connection conn) {
		db = database;
		connection = conn;
	}

	public void initDB() {
		Statement statement;
		try {
			statement = connection.createStatement();

			// creates the customers table
			statement.executeUpdate("create table customers ("
					+ "customerID integer primary key autoincrement, "
					+ "name varchar(100) not null, "
					+ "city varchar(50) not null, " + "address varchar(200), "
					+ "postalcode varchar(7), " + "phone varchar(14) " + ")");

			// creates the orders table
			statement.executeUpdate("create table orders ("
					+ "orderID integer primary key autoincrement, "
					+ "customerID integer references customers, "
					+ "orderDate date not null, " + "total double, "
					+ "paid boolean default false" + ")");

			// creates the products table
			statement.executeUpdate("create table products ("
					+ "productID integer primary key autoincrement, "
					+ "name varchar(75) not null, "
					+ "category varchar(50) not null, "
					+ "montrealPrice double, " + "ottawaPrice double, "
					+ "kosciolPrice double, " + "krazyKrisPrice double, "
					+ "cecilPrice double, " + "rosemontStorePrice double, "
					+ "updated boolean default false, "
					+ "dateCreated date not null, "
					+ "dateEffective date not null, " + "dateEnd date, "
					+ "dateReplaced date, " + "originalID integer" + ")");

			// creates the order details table
			statement.executeUpdate("create table orderDetails ("
					+ "orderID integer references orders, "
					+ "productID integer references products, "
					+ "quantity double, " + "subtotal double" + ")");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void initNewDB() {
		Statement statement;
		try {
			statement = connection.createStatement();

			// creates the customers table
			statement.executeUpdate("CREATE TABLE customers ( "
							+ "cid         INTEGER           PRIMARY KEY ASC AUTOINCREMENT NOT NULL UNIQUE, "
							+ "name        VARCHAR( 0, 50 )  NOT NULL, "
							+ "city        VARCHAR( 0, 30 )  NOT NULL, "
							+ "address     TEXT, "
							+ "postal_code TEXT( 0, 7 ), "
							+ "phone_num   TEXT );");

			statement.executeUpdate("CREATE TABLE orders ( "
							+ "oid         INTEGER PRIMARY KEY ASC AUTOINCREMENT NOT NULL, "
							+ "total       REAL    NOT NULL DEFAULT ( 0.0 ), "
							+ "paid_status BOOLEAN NOT NULL DEFAULT ( 0 ) );");

			statement.executeUpdate("CREATE TABLE placed_order ( "
					+ "cid  INTEGER NOT NULL REFERENCES customers ( cid ), "
					+ "oid  INTEGER NOT NULL REFERENCES orders ( oid ), "
					+ "order_date DATE    NOT NULL );");

			statement.executeUpdate("CREATE TABLE products ( "
							+ "pid            INTEGER           PRIMARY KEY ASC AUTOINCREMENT NOT NULL, "
							+ "name           VARCHAR( 0, 50 )  NOT NULL, "
							+ "category       VARCHAR( 0, 20 )  NOT NULL, "
							+ "montreal_price REAL              NOT NULL, "
							+ "ottawa_price   REAL              NOT NULL, "
							+ "kosciol_price  REAL              NOT NULL, "
							+ "cecil_price    REAL              NOT NULL, "
							+ "rosemont_price REAL              NOT NULL, "
							+ "updated        BOOLEAN           NOT NULL DEFAULT ( 0 ),  "
							+ "date_created   DATETIME 			NOT NULL, "
							+ "date_effective DATETIME          NOT NULL, "
							+ "date_end       DATETIME, "
							+ "date_replaced  DATETIME, "
							+ "original_id    INTEGER );");
			
			statement.executeUpdate("CREATE TABLE contained (  "
					+ "oid       INTEGER NOT NULL REFERENCES orders ( oid ), "
					+ "pid       INTEGER NOT NULL REFERENCES products ( pid ), "
					+ "quantity  REAL    NOT NULL DEFAULT ( 0.0 ), "
					+ "qub_total REAL    NOT NULL DEFAULT ( 0.0 ));");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void initStores() {
		DataLoader dl = new DataLoader(db);
		dl.importStores();
	}

	public void initProducts() {
		DataLoader dl = new DataLoader(db);
		dl.importProducts();
	}

	public void clearDB() {
		Statement statement;
		try {
			statement = connection.createStatement();
			statement.executeUpdate("drop table if exists customers");
			statement.executeUpdate("drop table if exists orders");
			statement.executeUpdate("drop table if exists placed_order");
			statement.executeUpdate("drop table if exists products");
			statement.executeUpdate("drop table if exists contained");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
