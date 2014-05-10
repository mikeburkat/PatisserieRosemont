package orders;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;

public class OrderTable extends JTable {

	private static final long serialVersionUID = 1L;
	
	private OrderModel orderModel;
	String date;
	String store;
	
	public OrderTable() {
		update();
		
	}

	public void update(){
		orderModel = new OrderModel(date, store);
		this.setModel(orderModel);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setCellSelectionEnabled(true);
		
		TableColumn col = this.getColumnModel().getColumn(0);
        MyTableCellEditor myTableCellEditor = new MyTableCellEditor();
        col.setCellEditor(myTableCellEditor);
        this.getColumnModel().getColumn(0).setPreferredWidth(50);
		this.getColumnModel().getColumn(1).setPreferredWidth(300);
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setStore(String store) {
		this.store = store;
	}

}
