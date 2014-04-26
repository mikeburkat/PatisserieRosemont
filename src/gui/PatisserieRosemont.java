package gui;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import orders.OrdersComponent;
import print.PrintComponent;
import reports.ReportsComponent;
import database.DataBase;
import date.DateChooser;
import drivers.DriversComponent;

public class PatisserieRosemont extends JTabbedPane{
	
	private static final long serialVersionUID = 1L;
	private DataBase db;
	
	private PatisserieRosemont() {
		
		db = new DataBase();
		
		JComponent dateChooser = new DateChooser();
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(dateChooser, BorderLayout.CENTER);
		this.addTab("Data", panel);
		
		JComponent orders = new OrdersComponent();
		this.addTab("Ordery", orders);
		
		JComponent print = new PrintComponent();
		this.addTab("Print", print);
		
		JComponent reports = new ReportsComponent();
		this.addTab("Totaly", reports);
		
		JComponent drivers = new DriversComponent();
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
