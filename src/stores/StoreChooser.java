package stores;

import java.awt.Dimension;
import java.util.ArrayList;

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
		storeList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		storeList.setVisibleRowCount(-1);
		
		JScrollPane listScroller = new JScrollPane(storeList);
		listScroller.setPreferredSize(new Dimension(250, 80));
		
		this.add(listScroller);
		
	}
	
	private void initStoreList(String city){
		db = DataBase.getInstance();
		ArrayList<String> stores = db.getStoreNames(city);
		for (String s: stores){
			System.out.println(s);
		}
		System.out.println(stores.toString());
		String[] storeArray = stores.toArray(new String[stores.size()]);
		storeList = new JList(storeArray);
	}

}
