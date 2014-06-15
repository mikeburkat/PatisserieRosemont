package database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DataInitialization {

	private Connection connection;
	private DataBase db;
	Statement statement;

	public DataInitialization(DataBase database, Connection conn) {
		db = database;
		connection = conn;
	}

	public void initDB() {
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
		try {
			statement = connection.createStatement();

			// creates the customers table
			statement
					.executeUpdate("CREATE TABLE customers ( "
							+ "cid         INTEGER PRIMARY KEY ASC AUTOINCREMENT NOT NULL UNIQUE, "
							+ "name        VARCHAR( 0, 50 )  NOT NULL, "
							+ "city        VARCHAR( 0, 30 )  NOT NULL, "
							+ "address     TEXT, "
							+ "postal_code TEXT( 0, 7 ), "
							+ "phone_num   TEXT );");

			statement
					.executeUpdate("CREATE TABLE orders ( "
							+ "oid         INTEGER PRIMARY KEY ASC AUTOINCREMENT NOT NULL, "
							+ "total       REAL    NOT NULL DEFAULT ( 0.0 ), "
							+ "paid_status BOOLEAN NOT NULL DEFAULT ( 0 ))");

			statement
					.executeUpdate("CREATE TABLE placed_order ( "
							+ "cid  INTEGER NOT NULL REFERENCES customers ( cid ) ON DELETE NO ACTION, "
							+ "oid  INTEGER NOT NULL UNIQUE ON CONFLICT IGNORE REFERENCES orders ( oid ) ON DELETE NO ACTION, "
							+ "order_date DATE NOT NULL, "
							+ "CONSTRAINT 'prim_key' PRIMARY KEY ( cid ASC, order_date ASC )  ON CONFLICT REPLACE, "
							+ "CONSTRAINT 'uniq_prim_key' UNIQUE ( cid ASC, order_date ASC )  ON CONFLICT REPLACE);");

			statement
					.executeUpdate("CREATE TABLE products ( "
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

			statement
					.executeUpdate("CREATE TABLE contained (  "
							+ "oid       INTEGER NOT NULL REFERENCES orders ( oid ) ON DELETE NO ACTION, "
							+ "pid       INTEGER NOT NULL REFERENCES products ( pid ) ON DELETE NO ACTION, "
							+ "quantity  REAL    NOT NULL DEFAULT ( 0.0 ), "
							+ "sub_total REAL    NOT NULL DEFAULT ( 0.0 ), "
							+ "CONSTRAINT 'prim_key_contained' PRIMARY KEY ( oid ASC, pid ASC ) ON CONFLICT REPLACE, "
							+ "CONSTRAINT 'uniq_product_per_order' UNIQUE ( oid ASC, pid ASC ) ON CONFLICT REPLACE);");

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

	public void runScript(String fileName) {
		File sqlFile = new File(fileName);
		BufferedReader reader;
		StringBuffer script = new StringBuffer();
		try {
			reader = new BufferedReader(new FileReader(sqlFile));
			String line;
			
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
				script.append(line);
				script.append("\n");
			}
			reader.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			statement = connection.createStatement();
			statement.executeUpdate(script.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
