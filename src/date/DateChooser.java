package date;

import java.beans.PropertyChangeEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JComponent;
import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;

import com.toedter.calendar.JCalendar;

public class DateChooser extends JComponent {

	private static final long serialVersionUID = 1L;
	private JCalendar calendar;
	private JLabel label;
	private SimpleDateFormat sdf;

	public DateChooser() {
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

	private String GetCalendarDate() {
		Date d = calendar.getDate();
		String dat = sdf.format(d);
		return dat;
	}

	public void SetDate(String date) {
		label.setText(date);
		System.out.println(date);
	}

	private class OnDaySelected implements java.beans.PropertyChangeListener {
		public void propertyChange(PropertyChangeEvent evt) {
			SetDate(GetCalendarDate());
		}
	}

}
