package stores;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import orders.OrdersPanel;

public class StoreChooserPane extends JPanel implements PropertyChangeListener{

	private static final long serialVersionUID = 1L;
	private StoreChooser montreal;
	private StoreChooser ottawa;
	private JLabel selectedStore;
	private JButton addNew;
	private OrdersPanel orders;

	public StoreChooserPane(OrdersPanel o) {
		orders = o;
		selectedStore = new JLabel("Not Selected Yet");
		montreal = new StoreChooser("Montreal");
		ottawa = new StoreChooser("Ottawa");
		
		montreal.addPropertyChangeListener(this);
		ottawa.addPropertyChangeListener(this);
		
		MigLayout mig = new MigLayout("wrap 2");
		mig.setColumnConstraints("[grow][grow]");
		mig.setRowConstraints("[30]20[grow]");
		
		this.setLayout(mig);
		
		this.add(selectedStore, "center, span 2");
		this.add(montreal, "center");
		this.add(ottawa, "center");
		
	}
	
	
	public String getselected() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		System.out.println(evt.getPropertyName() + " " + evt.getNewValue());
		// clear selection if selection in different city
		if (evt.getPropertyName() == "Montreal") {
			String s = (String) evt.getNewValue();
			selectedStore.setText(s);
			orders.setStore(s);
			ottawa.clearSelection();
		} else if (evt.getPropertyName() == "Ottawa") {
			String s = (String) evt.getNewValue();
			selectedStore.setText(s);
			orders.setStore(s);
			montreal.clearSelection();
		}
		
	}

	
	
	
	
}
