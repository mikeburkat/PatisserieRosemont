package orders;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.table.AbstractTableModel;

import database.DataBase;

public class OrderTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private String date;
	private String store;
	private OrderDetails[] orderDetails;
	private DataBase db;
	private String[] columnNames = { "Quantity", "Product Name" };

	public OrderTableModel(String d, String s) {
		date = d;
		store = s;

		this.db = DataBase.getInstance();
		boolean present = db.isOrderPresent(date, store);
		if (present) {
			createDefaultOrder();
			retrieveOrder(store, date, "pid asc");
		} else {
			createDefaultOrder();
			db.createOrder(store, date);
		}
	}
	
	public OrderTableModel() {
		this.db = DataBase.getInstance();
		createDefaultOrder();
	}

	public void createDefaultOrder() {
		ArrayList<String[]> pList = db.getProductList();
		orderDetails = new OrderDetails[pList.size()];
		for (int i = 0; i < pList.size(); i++) {
			orderDetails[i] = new OrderDetails(0, pList.get(i)[1], pList.get(i)[0]);
		}
	}
	
	public void retrieveOrder(String store, String date, String orderBy) {
		ArrayList<OrderDetails> od = db.getOrderDetails(store, date, orderBy);
		orderDetails = new OrderDetails[od.size()];
		for (int i = 0; i < od.size(); i++) {
			orderDetails[i] = od.get(i);
//			setValueAt(od.get(i).getQuantity(), i, 0);
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
		db.addToOrder(orderDetails[row].getQuantity(), orderDetails[row].getPid(), date, store);
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

	public void pushToTop() {
		retrieveOrder(store, date, "quantity desc, pid asc");
	}
	
	public void alphabetical() {
		retrieveOrder(store, date, "name asc");
	}
	
	public void sameAsLastWeek() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		Date d;
		try {
			d = sdf.parse(date);
			cal.setTime(d);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		cal.add(Calendar.DATE, -7);
		String lastWeekDate = sdf.format(cal.getTime());
		retrieveOrder(store, lastWeekDate, "quantity desc, pid asc");
	}
	
	public void sameAsLastWeekCopy() {
		clear();
		sameAsLastWeek();
		for (int i = 0; i < orderDetails.length; i++) {
			if (orderDetails[i].getQuantity() > 0){
				setValueAt(orderDetails[i].getQuantity(), i, 0);
			} 
		}
	}

	public void heuristic() {
		
	}

	public void clear() {
		db.deleteOrder(store, date);
		retrieveOrder(store, date, "pid asc");
	}

	



}
