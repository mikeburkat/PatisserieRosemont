package stores;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import database.DataBase;

public class StoreChooser extends JPanel {

	private JList storeList;
	private DataBase db;
	
	public StoreChooser(String city) {
		initStoreList(city);
		
		storeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		storeList.setVisibleRowCount(-1);
		
		JScrollPane listScroller = new JScrollPane(storeList);
		listScroller.setPreferredSize(new Dimension(150, 200));
		
		this.setLayout(new BorderLayout());
		
		this.add(new JLabel(city), BorderLayout.NORTH);
		this.add(listScroller, BorderLayout.CENTER);
		
	}
	
	private void initStoreList(String city){
		db = DataBase.getInstance();
		ArrayList<String> stores = db.getStoreNames(city);
		String[] storeArray = stores.toArray(new String[stores.size()]);
		storeList = new JList(storeArray);
	}

}
