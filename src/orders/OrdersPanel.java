package orders;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class OrdersPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JButton storeChooserButton;
	private JLabel chosenStore;
	
	public OrdersPanel() {


	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();

		
	}

}
