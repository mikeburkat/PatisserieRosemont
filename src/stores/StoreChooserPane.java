package stores;

import javax.swing.JButton;
import javax.swing.JPanel;

public class StoreChooserPane extends JPanel{

	private static final long serialVersionUID = 1L;
	private StoreChooser montreal;
	private StoreChooser ottawa;
	private JButton addNew;

	public StoreChooserPane() {
		
		montreal = new StoreChooser("Montreal");
		ottawa = new StoreChooser("Ottawa");
		
		this.add(montreal);
		this.add(ottawa);
		
		
	}
	
	
	public String getselected() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
	
}
