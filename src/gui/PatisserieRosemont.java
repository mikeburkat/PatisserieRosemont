package gui;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import orders.OrdersPanel;
import print.PrintComponent;
import reports.ReportsComponent;
import stores.StoreChooserPane;
import database.DataBase;
import date.DateChooser;
import drivers.DriversComponent;

public class PatisserieRosemont extends JTabbedPane {

	private static final long serialVersionUID = 1L;
	private DataBase db;
	JPanel dateChooser;
	JPanel storeChooserPane;
	OrdersPanel orders;
	JComponent print;
	JComponent reports;
	JComponent drivers;

	private PatisserieRosemont() {

		db = DataBase.getInstance();
		
		orders = new OrdersPanel();
		dateChooser = new DateChooser(orders);
		storeChooserPane = new StoreChooserPane(orders);
		print = new PrintComponent();
		reports = new ReportsComponent();
		drivers = new DriversComponent();
		
		this.addTab("Data", dateChooser);
		this.addTab("Sklep", storeChooserPane);
		this.addTab("Order", orders);
		this.addTab("Drukuj", print);
		this.addTab("Totaly", reports);
		this.addTab("Kierowcy", drivers);

	}

	private static void createAndShowGUI() {
		JFrame frame = new JFrame("Patisserie Rosemont");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JTabbedPane tabbedPane = new PatisserieRosemont();
		frame.setContentPane(tabbedPane);

		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}
