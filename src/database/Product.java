package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.w3c.dom.Element;

public class Product {
	int pid;
	String name;
	String category;
	double montrealPrice;
	double ottawaPrice;
	double kosciolPrice;
	double cecilPrice;
	double rosemontPrice;
	boolean updated;
	Date dateCreated;
	Date dateReplaced;
	int originalId;
	SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

	public Product(ResultSet rs) throws SQLException {
		this.pid = rs.getInt("pid");
		this.name = rs.getString("name");
		this.category = rs.getString("category");
		this.montrealPrice = rs.getDouble("montreal_price");
		this.ottawaPrice = rs.getDouble("ottawa_price");
		this.cecilPrice = rs.getDouble("cecil_price");
		this.kosciolPrice = rs.getDouble("kosciol_price");
		this.rosemontPrice = rs.getDouble("rosemont_price");
		this.updated = rs.getBoolean("updated");

		String dC = rs.getString("date_created");
		if (dC != null) {
			try {
				this.dateCreated = fmt.parse(dC);
			} catch (ParseException e) {
				e.printStackTrace();
				this.dateCreated = new Date();
			}
		}
		String dR = rs.getString("date_replaced");
		if (dR != null) {
			try {
				this.dateReplaced = fmt.parse(dR);
			} catch (ParseException e) {
				e.printStackTrace();
				this.dateReplaced = new Date();
			}
		}
	}

	public Product(Element e) {
		this.name = e.getElementsByTagName("name").item(0)
				.getTextContent();
		this.category = e.getElementsByTagName("category").item(0)
				.getTextContent();
		this.montrealPrice = Double
				.parseDouble(e.getElementsByTagName("montrealPrice")
						.item(0).getTextContent());
		this.ottawaPrice = Double.parseDouble(e
				.getElementsByTagName("ottawaPrice").item(0).getTextContent());
		this.kosciolPrice = Double.parseDouble(e
				.getElementsByTagName("kosciolPrice").item(0).getTextContent());
		this.cecilPrice = Double.parseDouble(e
				.getElementsByTagName("cecilPrice").item(0).getTextContent());
		this.rosemontPrice = Double.parseDouble(e
				.getElementsByTagName("storePrice").item(0).getTextContent());
		this.dateCreated = new Date();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Product other = (Product)obj;
		
		return this.name.equals(other.name) && this.category.equals(other.category)
				&& this.montrealPrice == other.montrealPrice
				&& this.ottawaPrice == other.ottawaPrice
				&& this.kosciolPrice == other.kosciolPrice
				&& this.cecilPrice == other.cecilPrice
				&& this.rosemontPrice == other.rosemontPrice;
	}

	public String getDateCreated() {
		return this.fmt.format(this.dateCreated);
	}

	public String getDateReplaced() {
		return this.fmt.format(this.dateCreated);
	}

	public String getInsertQuery() {
		String query = "insert into "
				+ "products(name, category, montreal_price, ottawa_price, kosciol_price, "
				+ "cecil_price, rosemont_price, date_created, updated, original_id) "
				+ "values(" + "'"
				+ this.name
				+ "', "
				+ "'"
				+ this.category
				+ "', "
				+ "'"
				+ this.montrealPrice
				+ "', "
				+ "'"
				+ this.ottawaPrice
				+ "', "
				+ "'"
				+ this.kosciolPrice
				+ "', "
				+ "'"
				+ this.cecilPrice
				+ "', "
				+ "'"
				+ this.rosemontPrice
				+ "', "
				+ "'"
				+ this.getDateCreated() + "', " + "'" + this.updated + "', ";
		if (this.originalId != 0) {
			query += "'" + this.originalId + "'";
		} else {
			query += "'NULL'";
		}
		query += ")";
		return query;
	}

	public String getUpdateQuery() {
		String query = "update products set " + "updated='" + this.updated
				+ "', " + "date_replaced='" + this.getDateReplaced() + "' "
				+ "where pid='" + this.pid + "'";
		return query;
	}
	
	public String toString() {
		return "pid: " + this.pid
			+ " name: " + this.name
			+ " cat: " + this.category
			+ " mtl: " + this.montrealPrice
			+ " ott: " + this.ottawaPrice
			+ " kosc: " + this.kosciolPrice
			+ " cecil: " + this.cecilPrice
			+ " rsmt: " + this.rosemontPrice
			+ " updated: " + this.updated
			+ " created: " + this.dateCreated
			+ " replaced: " + this.dateReplaced
			+ " oid: " + this.originalId;
	}
}
