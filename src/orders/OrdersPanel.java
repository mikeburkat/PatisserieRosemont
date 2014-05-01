package orders;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class OrdersPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	private JLabel store;
	private JLabel date;
	
	public OrdersPanel() {
		store = new JLabel("Sklep: Not chosen");
		date = new JLabel("Data: Not chosen");
		
		MigLayout mig = new MigLayout("wrap 4");
		mig.setColumnConstraints("[grow][grow][grow][grow]");
		mig.setRowConstraints("[30]20[grow][grow][grow]");
		
		this.setLayout(mig);
		
		this.add(date, "span 2, center");
		this.add(store, "span 2, center");
		
	}

	
	
	public void setDate(String d) {
		date.setText("Data: " + d);
	}
	
	public void setStore(String s) {
		store.setText("Sklep: " + s);
	}
	

}
