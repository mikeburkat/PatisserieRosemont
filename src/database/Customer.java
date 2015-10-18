package database;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.w3c.dom.Element;

public class Customer {
	
	int cid;
	String name;
	String fullName;
	public int getCid() {
		return cid;
	}

	public String getName() {
		return name;
	}

	public String getFullName() {
		return fullName;
	}

	public String getCity() {
		return city;
	}

	public String getAddress() {
		return address;
	}

	public String getPostal() {
		return postal;
	}

	public String getPhone() {
		return phone;
	}

	public String getPriceUsed() {
		return priceUsed;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setPostal(String postal) {
		this.postal = postal;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setPriceUsed(String priceUsed) {
		this.priceUsed = priceUsed;
	}

	String city;
	String address;
	String postal;
	String phone;
	String priceUsed;
	
	
	public Customer(ResultSet rs) throws SQLException {
		this.cid = rs.getInt("cid");
		this.name = rs.getString("name");
		this.name = this.name.replace("'", "''");
		this.fullName = rs.getString("full_name");
		this.fullName = this.fullName.replace("'", "''");
		this.city = rs.getString("city");
		this.address = rs.getString("address");
		this.postal = rs.getString("postal_code");
		this.phone = rs.getString("phone_num");
		this.priceUsed = rs.getString("price_used");
	}
	
	public Customer(Element e) {
		this.name = e.getElementsByTagName("name").item(0).getTextContent();
		this.name = this.name.replace("'", "''");
		this.fullName = e.getElementsByTagName("fullName").item(0).getTextContent();
		this.fullName = this.fullName.replace("'", "''");
		this.address = e.getElementsByTagName("address").item(0).getTextContent();
		this.city = e.getElementsByTagName("city").item(0).getTextContent();
		this.postal = e.getElementsByTagName("postal").item(0).getTextContent();
		this.phone = e.getElementsByTagName("phone").item(0).getTextContent();
		this.priceUsed = e.getElementsByTagName("priceUsed").item(0).getTextContent();
	}
	
	public Customer() {
		this.cid = 0;
		this.name = "";
		this.fullName = "";
		this.city = "";
		this.address = "";
		this.postal = "";
		this.phone = "";
		this.priceUsed = "";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Customer other = (Customer)obj;
		
		if (!address.equals(other.address)) {
			System.out.println("address");
		}
		
		if (!city.equals(other.city)) {
			System.out.println("city");
		}
		
		if (!name.equals(other.name)) {
			System.out.println("name");
		}
		
		if (!fullName.equals(other.fullName)) {
			System.out.println("fullName");
		}
		
		if (!phone.equals(other.phone)) {
			System.out.println("phone");
		}
		
		if (!priceUsed.equals(other.priceUsed)) {
			System.out.println("priceUsed");
		}
		
		return this.name.equals(other.name)
				&& this.fullName.equals(other.fullName)
				&& this.city.equals(other.city)
				&& this.address.equals(other.address)
				&& this.postal.equals(other.postal)
				&& this.phone.equals(other.phone)
				&& this.priceUsed.equals(other.priceUsed);
	}
	
	public String escapeApostrophe(String s) {
		return s.replace("'", "''");
	}
	
	public String insertQuery() {
		String query = "insert into customers(name, full_name, city, address, postal_code, phone_num, price_used) "
				+ "values(" 
				+ "'" + name + "', " 
				+ "'" + fullName + "', "
				+ "'" + city + "', " 
				+ "'" + address + "', "
				+ "'" + postal + "', " 
				+ "'" + phone + "', "
				+ "'" + priceUsed + "'"
				+ ")";
		return query;
	}
		
	public String updateQuery() {
		String query = "update customers set "
				+ "name='" + name + "', "
				+ "full_name='" + fullName + "', "
				+ "city='" + city + "', "
				+ "address='" + address + "', "
				+ "postal_code='" + postal + "', "
				+ "phone_num='" + phone + "', "
				+ "price_used='" + priceUsed + "' "
				+ "where cid='" + cid + "'";
		return query;
	}
	
	public String toString() {
		return "cid='" + cid + "', "
				+ "name='" + name + "', "
				+ "full_name='" + fullName + "', "
				+ "city='" + city + "', "
				+ "address='" + address + "', "
				+ "postal_code='" + postal + "', "
				+ "phone_num='" + phone + "', "
				+ "price_used='" + priceUsed + "'";
	}
	
}
