package orders;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class StoresTabbedPane extends JTabbedPane{
	
	private static final long serialVersionUID = 1L;

	private JPanel montreal;
	private StoreChooser ottawa;
	
	public StoresTabbedPane(){
		montreal = new StoreChooser("Montreal");
		ottawa = new StoreChooser("Ottawa");
		
		
	}

}
