package stores;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import database.Customer;

public class CustomerDetailsPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	Customer customer;
	JLabel name;
	JLabel fullName;
	JLabel city;
	JLabel address;
	JLabel postal;
	JLabel phone;
	
	public CustomerDetailsPanel(Customer c) {
		setLayout(new GridLayout(6, 2));
		add(new JLabel("Name:"));
		name = new JLabel("");
		add(name);
		add(new JLabel("Full Name:"));
		fullName = new JLabel("");
		add(fullName);
		add(new JLabel("City:"));
		city = new JLabel("");
		add(city);
		add(new JLabel("Address:"));
		address = new JLabel("");
		add(address);
		add(new JLabel("Postal Code:"));
		postal = new JLabel("");
		add(postal);
		add(new JLabel("Phone Number:"));
		phone = new JLabel("");
		add(phone);
		
		setCustomer(c);
	}
	
	public void setCustomer(Customer c) {
		customer = c;
		name.setText(customer.getName());
		fullName.setText(customer.getFullName());
		city.setText(customer.getCity());
		address.setText(customer.getAddress());
		postal.setText(customer.getPostal());
		phone.setText(customer.getPhone());

		System.out.println("Customer details, " + customer.getName() + ", " + customer.getFullName());
	}
}
