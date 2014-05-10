package orders;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import database.DataBase;

public class OrderModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private String date;
	private String store;
	private OrderDetails[] orderDetails;
	private DataBase db;
	private String[] columnNames = { "Quantity", "Product Name" };

	public OrderModel(String d, String s) {
		date = d;
		store = s;

		this.db = DataBase.getInstance();
		boolean present = db.isOrderPresent(date, store);
		System.out.println(present);
		if (present) {
			createDefaultOrder();
			retrieveOrder();
		} else {
			createDefaultOrder();
		}
	}

	public OrderModel() {
		this.db = DataBase.getInstance();
		createDefaultOrder();
	}

	public void createDefaultOrder() {
		ArrayList<String> p = db.getProductList();
		orderDetails = new OrderDetails[p.size()];
		for (int i = 0; i < p.size(); i++) {
			orderDetails[i] = new OrderDetails(0, p.get(i));
		}
	}
	
	public void retrieveOrder() {
		ArrayList<OrderDetails> od = db.getOrderDetails(store, date);
		
		for (OrderDetails o : od) {
			System.out.println(store +" "+ date +" "+ o.getQuantity()+" "+ o.getProduct());
			int i = getIndexOf(o.getProduct());
			//setValueAt(o.getQuantity(), i, 0);
			orderDetails[i].setQuantity(o.getQuantity());
		}
		
	}
	
	public int getIndexOf(String product){
		for (int i = 0; i < orderDetails.length; i++) {
			if (product.equals( orderDetails[i].getProduct() ) ) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public int getRowCount() {
		return orderDetails.length;
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return orderDetails[rowIndex].getQuantity();
		} else {
			return orderDetails[rowIndex].getProduct();
		}
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		System.out.println("Value = " + value);
		orderDetails[row].setQuantity((Double)value);
		db.addToOrder(orderDetails[row].getQuantity(), orderDetails[row].getProduct(), date, store);
	}

	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		if (col == 0) {
			return true;
		} else {
			return false;
		}
	}

}
