package print;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.miginfocom.swing.MigLayout;
import database.DataBase;

public class PrintPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JList<String> storeList;
	private JScrollPane listScroller;
	private DataBase db;
	private String date;
	private List<String> selected;

	private ArrayList<String> montrealStores;
	private ArrayList<String> ottawaStores;

	private JButton selectAll;
	private JButton selectMtl;
	private JButton selectOtt;
	private JButton print;

	public PrintPanel() {
		db = DataBase.getInstance();
		date = "";

		montrealStores = db.getStoreNames("Montreal");
		ottawaStores = db.getStoreNames("Ottawa");

		MigLayout mig = new MigLayout("wrap 3");
		mig.setColumnConstraints("[grow][grow][grow]");
		mig.setRowConstraints("[20]10[center][grow]30");
		this.setLayout(mig);

		selectAll = new JButton("Select All");
		selectMtl = new JButton("Select Montreal");
		selectOtt = new JButton("Select Ottawa");

		selectAll.addActionListener(this);
		selectMtl.addActionListener(this);
		selectOtt.addActionListener(this);

		this.add(selectAll, "center");
		this.add(selectMtl, "center");
		this.add(selectOtt, "center");

		updateList();

		print = new JButton("Print Selected");
		print.addActionListener(this);
		this.add(print, "cell 1 1, center");
	}

	public void updateList() {
		if (listScroller != null) {
			this.remove(listScroller);
		}

		ArrayList<String> orderList = db.getStoresWhoOrderedOn(date);
		String[] storeArray = orderList.toArray(new String[orderList.size()]);

		storeList = new JList<String>(storeArray);
		storeList
				.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		storeList.setVisibleRowCount(-1);
		listScroller = new JScrollPane(storeList);
		listScroller.setPreferredSize(new Dimension(190, 400));

		storeList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					JList<String> source = (JList<String>) e.getSource();
					if (!source.isSelectionEmpty()) {
						selected = source.getSelectedValuesList();
						System.out.println("Selected: ");
						for (String s : selected)
							System.out.println(s);
					}
				}
			}
		});
		this.add(listScroller, "cell 0 1, center");
	}

	public void setDate(String newDate) {
		date = newDate;
		updateList();
	}

	public void selectMontreal() {
		for (int i = 0; i < storeList.getModel().getSize(); i++) {
			if (montrealStores.contains(storeList.getModel().getElementAt(i))) {
				storeList.addSelectionInterval(i, i);
			}
		}
	}

	public void selectOttawa() {
		for (int i = 0; i < storeList.getModel().getSize(); i++) {
			if (ottawaStores.contains(storeList.getModel().getElementAt(i))) {
				storeList.addSelectionInterval(i, i);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Select All":
			storeList.clearSelection();
			selectMontreal();
			selectOttawa();
			break;
		case "Select Montreal":
			storeList.clearSelection();
			selectMontreal();
			break;
		case "Select Ottawa":
			storeList.clearSelection();
			selectOttawa();
			break;
		case "Print Selected":
			printSelected();
			break;
		}
		;
	}

	private void printSelected() {
		if (selected == null) return;
		System.out.println("PRITING SELECTION");
		
		for (String store : selected) {
			
			PrinterJob job = PrinterJob.getPrinterJob();
			job.setPrintable(new PrintOrder(store, date));
			boolean doPrint = job.printDialog();
			if (doPrint) {
				try {
					job.print();
				} catch (PrinterException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
