package reports;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;
import database.DataBase;

public class TotalsPanel extends JPanel {

	String date;
	String[] columns = {"quantity", "Product"};
	JTable table;
	DataBase db;
	String bread = "'bread', 'pastry'";
	String cake = "'other', 'cake'";
	String productChoice = bread + ", " + cake;
	String cityChoice = "'Montreal', 'Ottawa'";
	JScrollPane sp;
	DefaultTableModel tableModel;
	
	JButton allProducts;
	JButton bakery;
	JButton pastry;
	JButton allCities;
	JButton montreal;
	JButton ottawa;
	

	public TotalsPanel() {
		db = DataBase.getInstance();
		
		MigLayout mig = new MigLayout();
		mig.setColumnConstraints("[grow][grow][grow][grow]");
		mig.setRowConstraints("20[grow][grow][grow][grow][grow][grow]");
		
		this.setLayout(mig);
		tableModel = new DefaultTableModel(columns, 0);
		sp = new JScrollPane();
		table = new JTable(tableModel);
		sp = new JScrollPane(table);
		this.add(sp, "cell 0 0 4 6, center");
		
	}

	public void setDate(String newDate) {
		date = newDate;
		update();
	}

	public void update() {
		ArrayList<String[]> rows = db.getTotals(date, productChoice, cityChoice);
		
		tableModel = new DefaultTableModel(columns, 0);
		for (String[] row : rows) {
			tableModel.addRow(row);
		}
		table.setModel(tableModel);
		
		this.repaint();
	}

}
