package stores;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import database.Customer;
import database.DataBase;
import net.miginfocom.swing.MigLayout;
import orders.OrdersPanel;

public class StoreChooserPane extends JPanel implements PropertyChangeListener{

	private static final long serialVersionUID = 1L;
	private StoreChooser montreal;
	private StoreChooser ottawa;
	private JLabel selectedStore;
	private CustomerDetailsPanel details;
	private JButton addNew;
	private OrdersPanel orders;
	private String date;

	public StoreChooserPane(OrdersPanel o) {
		orders = o;
		details = new CustomerDetailsPanel(new Customer());
		montreal = new StoreChooser("Montreal");
		ottawa = new StoreChooser("Ottawa");
		
		montreal.addPropertyChangeListener(this);
		ottawa.addPropertyChangeListener(this);
		
		MigLayout mig = new MigLayout("wrap 2");
		mig.setColumnConstraints("[grow][grow]");
		mig.setRowConstraints("[30]20[grow]");
		
		this.setLayout(mig);
		this.add(details, "center, span 2");
		this.add(montreal, "center");
		this.add(ottawa, "center");
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		System.out.println(evt.getPropertyName() + " " + evt.getNewValue());
		// clear selection if selection in different city
		if (evt.getPropertyName() != "Montreal" && evt.getPropertyName() != "Ottawa") {
			return;
		}
		
		String s = (String) evt.getNewValue();
		orders.setStore(s);
		
		DataBase db = DataBase.getInstance();
		Customer c = db.getCustomer(s);
		details.setCustomer(c);
		
		if (evt.getPropertyName() == "Montreal") {
			ottawa.clearSelection();
		} else if (evt.getPropertyName() == "Ottawa") {
			montreal.clearSelection();
		}
	}

	
	public void setDate(String d) {
		date = d;
		this.updateOrederedStoreList();
	}

	public void updateOrederedStoreList() {
		montreal.updateOrderedStoreList(date);
		ottawa.updateOrderedStoreList(date);		
	}
	
	
}
