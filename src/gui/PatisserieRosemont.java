package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import orders.OrdersPanel;
import print.PrintPanel;
import reports.GraphPanel;
import reports.TotalsPanel;
import stores.StoreChooserPane;
import database.DataBase;
import date.DateChooser;
import drivers.DriversPanel;

public class PatisserieRosemont extends JTabbedPane {

	private static final long serialVersionUID = 1L;
	private DataBase db;
	private JPanel dateChooser;
	private StoreChooserPane storeChooserPane;
	private OrdersPanel orders;
	private PrintPanel print;
	private TotalsPanel totals;
	private GraphPanel graphs;
	private DriversPanel drivers;
	private final static JTabbedPane tpane = new JTabbedPane();

	private PatisserieRosemont() {

		db = DataBase.getInstance();
		
		orders = new OrdersPanel();
		storeChooserPane = new StoreChooserPane(orders);
		print = new PrintPanel();
		totals = new TotalsPanel();
		graphs = new GraphPanel();
		drivers = new DriversPanel();
		dateChooser = new DateChooser(orders, print, graphs, drivers, totals, storeChooserPane);
		
		tpane.addTab("	Data	", dateChooser);
		tpane.addTab("	Sklep	", storeChooserPane);
		tpane.addTab("	Order	", orders);
		tpane.addTab("	Drukuj	", print);
		tpane.addTab("	Totaly	", totals);
		//tpane.addTab("	Kierowcy", drivers);
		//tpane.addTab("	Graphs	", graphs);
		
		tpane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				int tab = tpane.getSelectedIndex();
				System.out.println("Tab: " + tab);
				
				switch (tab) {
				case 1:
					storeChooserPane.updateOrederedStoreList();
					break;
				case 3:
					print.updateList();
					break;
				case 4:
					totals.update();
					break;
				default:
					break;
				};
			}
		});
		this.add(tpane);
	}

	private static void createAndShowGUI() {
		JFrame frame = new JFrame("Patisserie Rosemont");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		PatisserieRosemont tabbedPane = new PatisserieRosemont();
		frame.setContentPane(tpane);

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
