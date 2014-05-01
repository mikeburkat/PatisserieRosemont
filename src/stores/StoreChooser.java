package stores;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import database.DataBase;

public class StoreChooser extends JPanel {

	private JList storeList;
	private DataBase db;
	private String selected;
	private String myCity;

	public StoreChooser(String city) {
		myCity = city;
		initStoreList(city);

		storeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		storeList.setVisibleRowCount(-1);

		JScrollPane listScroller = new JScrollPane(storeList);
		listScroller.setPreferredSize(new Dimension(190, 300));

		this.setLayout(new BorderLayout());

		storeList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					JList<String> source = (JList<String>) e.getSource();
					if (!source.isSelectionEmpty()) {
						selected = source.getSelectedValue().toString();
						firePropertyChange(myCity, "", selected);
					}
				}
			}
		});

		this.add(new JLabel(city), BorderLayout.NORTH);
		this.add(listScroller, BorderLayout.CENTER);

	}

	private void initStoreList(String city) {
		db = DataBase.getInstance();
		ArrayList<String> stores = db.getStoreNames(city);
		String[] storeArray = stores.toArray(new String[stores.size()]);
		storeList = new JList<String>(storeArray);
	}

	public void clearSelection() {
		storeList.clearSelection();
	}

}
