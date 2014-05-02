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
		boolean present = db.isOrderDetailsPresent(date, store);
		System.out.println(present);
		if (!present) {
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
