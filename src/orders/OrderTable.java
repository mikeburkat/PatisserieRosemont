package orders;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class OrderTable extends JTable {

	private static final long serialVersionUID = 1L;
	
	private OrderModel orderModel;
	String date;
	String store;
	
	public OrderTable(String d, String s) {
		date = d;
		store = s;
		
		orderModel = new OrderModel(date, store);
		this.setModel(orderModel);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setCellSelectionEnabled(true);
	}
	
	public OrderTable() {
		orderModel = new OrderModel();
		orderModel = new OrderModel(date, store);
		this.setModel(orderModel);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setCellSelectionEnabled(true);
	}

	public void update(){
		orderModel = new OrderModel(date, store);
	}


	public void setDate(String date) {
		this.date = date;
	}


	public void setStore(String store) {
		this.store = store;
	}

}
