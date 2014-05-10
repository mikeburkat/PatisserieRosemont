package date;

import java.beans.PropertyChangeEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import orders.OrdersPanel;
import print.PrintPanel;
import reports.ReportsPanel;

import com.toedter.calendar.JCalendar;

import drivers.DriversPanel;

public class DateChooser extends JPanel {

	private static final long serialVersionUID = 1L;
	private JCalendar calendar;
	private JLabel label;
	private SimpleDateFormat sdf;
	private OrdersPanel orders;
	private PrintPanel print;
	private ReportsPanel reports;
	private DriversPanel drivers;
	
	//TODO add a choose TODAY button

	public DateChooser(OrdersPanel o, PrintPanel p, ReportsPanel r, DriversPanel d) {
		orders = o;
		print = p;
		reports = r;
		drivers = d;
		
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		MigLayout mig = new MigLayout("wrap 3");

		mig.setColumnConstraints("[30][grow][30]");
		mig.setRowConstraints("[30]20[grow][]");

		this.setLayout(mig); // Row constraints

		add(new JLabel(""), "cell 0 0");

		label = new JLabel("date not selected");
		add(label, "cell 1 0");
		add(label, "center");

		add(new JLabel(""), "cell 2 0");
		add(new JLabel(""), "cell 0 1 1 2");

		calendar = new JCalendar();
		calendar.addPropertyChangeListener(new OnDaySelected());

		add(calendar, "cell 1 1 1 2");
		add(calendar, "grow");

		add(new JLabel(""), "cell 2 1 1 2");

	}

	private String getCalendarDate() {
		Date d = calendar.getDate();
		String dat = sdf.format(d);
		return dat;
	}

	public void setLabel(String date) {
		label.setText(date);
		System.out.println(date);
	}

	private class OnDaySelected implements java.beans.PropertyChangeListener {
		public void propertyChange(PropertyChangeEvent evt) {
			String s = getCalendarDate();
			setLabel(s);
			orders.setDate(s);
			print.setDate(s);
			reports.setDate(s);
			drivers.setDate(s);
		}
	}

}
