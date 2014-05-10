package print;

import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import database.DataBase;

public class PrintPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	ArrayList<JLabel> labels;
	DataBase db;
	String date;
	
	public PrintPanel() {
		db = DataBase.getInstance();
		labels = new ArrayList<JLabel>();
		date = "";
	}
	
	public void updateList() {
		ArrayList<String> orderList = db.getOrdersList(date);
		labels.clear();
		
		for (String i : orderList) {
			labels.add(new JLabel(i));
			System.out.println("printList " +i);
		}
		
		this.removeAll();
		for (JLabel jl : labels) {
			this.add(jl);
		}
		
	}
	
	public void setDate(String newDate) {
		date = newDate;
		updateList();
	}

}
