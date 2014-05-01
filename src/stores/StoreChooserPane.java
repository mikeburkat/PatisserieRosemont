package stores;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StoreChooserPane extends JPanel implements PropertyChangeListener{

	private static final long serialVersionUID = 1L;
	private StoreChooser montreal;
	private StoreChooser ottawa;
	private JLabel selectedStore;
	private JButton addNew;

	public StoreChooserPane() {
		
		selectedStore = new JLabel("Not Selected Yet");
		montreal = new StoreChooser("Montreal");
		ottawa = new StoreChooser("Ottawa");
		
		montreal.addPropertyChangeListener(this);
		ottawa.addPropertyChangeListener(this);
		
		this.add(selectedStore);
		this.add(montreal);
		this.add(ottawa);
		
	}
	
	
	public String getselected() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		System.out.println(evt.getPropertyName() + " " + evt.getNewValue());
		
		
		if (evt.getPropertyName() == "Montreal") {
			selectedStore.setText((String) evt.getNewValue());
			ottawa.clearSelection();
		} else if (evt.getPropertyName() == "Ottawa") {
			selectedStore.setText((String) evt.getNewValue());
			montreal.clearSelection();
		}
		
	}

	
	
	
	
}
