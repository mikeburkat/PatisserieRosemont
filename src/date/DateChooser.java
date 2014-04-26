package date;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import com.toedter.calendar.JCalendar;

public class DateChooser extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JCalendar calendar;
	private JLabel label;

	public DateChooser() {
		MigLayout mig = new MigLayout("wrap 3");

		mig.setColumnConstraints("[30][grow][30]");
		mig.setRowConstraints("[30]20[grow][]");
		
		this.setLayout(mig); // Row constraints
		
		add(new JLabel(""), "cell 0 0");
		
		label = new JLabel("date not selected");
		add(label, "cell 1 0" );
		add(label, "center");
		
		add(new JLabel(""), "cell 2 0");
		
		
		
		add(new JLabel(""), "cell 0 1 1 2");
		
		calendar = new JCalendar();
		add(calendar, "cell 1 1 1 2");
		add(calendar, "grow");
		
		add(new JLabel(""), "cell 2 1 1 2");
		
		
	}

}
