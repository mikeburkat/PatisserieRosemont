package gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import orders.OrdersPanel;
import print.PrintPanel;
import reports.ReportsPanel;
import stores.StoreChooserPane;
import database.DataBase;
import date.DateChooser;
import drivers.DriversPanel;

public class PatisserieRosemont extends JTabbedPane {

	private static final long serialVersionUID = 1L;
	private DataBase db;
	private JPanel dateChooser;
	private JPanel storeChooserPane;
	private OrdersPanel orders;
	private PrintPanel print;
	private ReportsPanel reports;
	private DriversPanel drivers;

	private PatisserieRosemont() {

		db = DataBase.getInstance();
		//db.init();
		
		orders = new OrdersPanel();
		storeChooserPane = new StoreChooserPane(orders);
		print = new PrintPanel();
		reports = new ReportsPanel();
		drivers = new DriversPanel();
		dateChooser = new DateChooser(orders, print, reports, drivers);
		
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

		frame.setSize(700, 800);
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
