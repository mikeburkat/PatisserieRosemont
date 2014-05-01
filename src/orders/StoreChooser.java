package orders;

import javax.swing.JList;
import javax.swing.JPanel;

import database.DataBase;

public class StoreChooser extends JPanel {

	private JList storeList;
	private DataBase db;
	
	public StoreChooser(String city) {
		initStoreList(city);
		
	}
	
	private void initStoreList(String city){
		
		db = DataBase.getInstance();
		db.getStoreNames(city);
		
		
	}

}
