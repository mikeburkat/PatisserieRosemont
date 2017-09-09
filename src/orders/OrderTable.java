package orders;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;

public class OrderTable extends JTable {

	private static final long serialVersionUID = 1L;
	
	private static final int ROW_HEIGHT = 18;
	private static final int FONT_SIZE = 14;
	private static final int COLUMN_1_WIDTH = 50;
	private static final int COLUMN_2_WIDTH = 300;
	
	private OrderTableModel orderModel;
	String date = null;
	String store = null;

	private JTextField searchField;
	
	public OrderTable() {
		update();
	}

	public void update(){
		orderModel = new OrderTableModel(date, store);
		this.setModel(orderModel);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//this.setCellSelectionEnabled(true);
		this.setRowSelectionAllowed(true);
		
		this.setShowVerticalLines(false);
		this.setShowHorizontalLines(true);
		this.setGridColor(Color.BLACK);
		
		TableColumn col = this.getColumnModel().getColumn(0);
        MyTableCellEditor myTableCellEditor = new MyTableCellEditor();
        myTableCellEditor.setSearchFiled(searchField);
        col.setCellEditor(myTableCellEditor);
        this.setRowHeight(ROW_HEIGHT);
        this.getColumnModel().getColumn(0).setPreferredWidth(COLUMN_1_WIDTH);
		this.getColumnModel().getColumn(1).setPreferredWidth(COLUMN_2_WIDTH);
		this.setFont(new Font(this.getFont().getName(), this.getFont().getStyle(), FONT_SIZE));
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

	public void search(String str) {
		orderModel.search(str);
	}

	public void setSearchField(JTextField searchField) {
		this.searchField = searchField;
	}


}
