package reports;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;
import database.DataBase;

public class TotalsPanel extends JPanel implements ActionListener {

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
	
	JLabel selection;
	

	public TotalsPanel() {
		db = DataBase.getInstance();
		
		MigLayout mig = new MigLayout();
		mig.setColumnConstraints("[grow][grow][grow]");
		mig.setRowConstraints("[grow][grow][grow][grow][grow][grow]");
		
		this.setLayout(mig);
		
		tableModel = new DefaultTableModel(columns, 0);
		sp = new JScrollPane();
		table = new JTable(tableModel);
		sp = new JScrollPane(table);
		this.add(sp, "cell 0 1 3 5, center");
		
		allProducts = new JButton("Piek + Cuk");
		bakery = new JButton("Piekarnia");
		pastry = new JButton("Cukiernia");
		allCities = new JButton("MTL + OTT");
		montreal = new JButton("Montreal");
		ottawa = new JButton("Ottawa");
		
		allProducts.addActionListener(this);
		bakery.addActionListener(this);
		pastry.addActionListener(this);
		allCities.addActionListener(this);
		montreal.addActionListener(this);
		ottawa.addActionListener(this);
		
		this.add(allProducts, "cell 0 5, center");
		this.add(bakery, "cell 1 5, center");
		this.add(pastry, "cell 2 5, center");
		this.add(allCities, "cell 0 6, center");
		this.add(montreal, "cell 1 6, center");
		this.add(ottawa, "cell 2 6, center");
		
		selection = new JLabel(productChoice + " | " + cityChoice);
		this.add(selection, "cell 1 0, center");
		
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

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Piek + Cuk":
			productChoice = bread + ", " + cake;
			selection.setText(productChoice + " | " + cityChoice);
			update();
			System.out.println(e.getActionCommand());
			break;
		case "Piekarnia":
			productChoice = bread;
			selection.setText(productChoice + " | " + cityChoice);
			update();
			System.out.println(e.getActionCommand());
			break;
		case "Cukiernia":
			productChoice = cake;
			selection.setText(productChoice + " | " + cityChoice);
			update();
			System.out.println(e.getActionCommand());
			break;
		case "MTL + OTT":
			cityChoice = "'Montreal', 'Ottawa'";
			selection.setText(productChoice + " | " + cityChoice);
			update();
			System.out.println(e.getActionCommand());
			break;
		case "Montreal":
			cityChoice = "'Montreal'";
			selection.setText(productChoice + " | " + cityChoice);
			update();
			System.out.println(e.getActionCommand());
			break;
		case "Ottawa":
			cityChoice = "'Ottawa'";
			selection.setText(productChoice + " | " + cityChoice);
			update();
			System.out.println(e.getActionCommand());
			break;
		}
	}

}
