package orders;

import java.awt.Color;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;

public class OrderTable extends JTable {

	private static final long serialVersionUID = 1L;
	
	private OrderTableModel orderModel;
	String date = null;
	String store = null;
	
	public OrderTable() {
		update();
	}

	public void update(){
		orderModel = new OrderTableModel(date, store);
		this.setModel(orderModel);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setCellSelectionEnabled(true);
		
		this.setShowVerticalLines(false);
		this.setShowHorizontalLines(true);
		this.setGridColor(Color.BLACK);
		
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

	public void pushToTop() {
		orderModel.pushToTop();
	}

	public void sameAsLastWeek() {
		orderModel.sameAsLastWeek();
	}
	
	public void sameAsLastWeekCopy() {
		orderModel.sameAsLastWeekCopy();
	}

	public void heuristic() {
		orderModel.heuristic();
	}

	public void clear() {
		orderModel.clear();
	}

	public void alphabetical() {
		orderModel.alphabetical();
	}

	


}
