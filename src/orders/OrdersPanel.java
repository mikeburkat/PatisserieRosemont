package orders;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;

public class OrdersPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JLabel storeLabel;
	private JLabel dateLabel;
	private OrderTable table;
	private JScrollPane tableScroller;
	private JButton toTop;
	private JButton lastWeek;
	private JButton lastWeekCopy;
	private JButton heuristic;
	private JButton alphabetical;
	private JButton clear;
	private JButton normal;
	private final String NORMAL = "normal";
	private final String TOTOP = "to top";
	private final String LASTWEEK = "last week look";
	private final String LASTWEEKCOPY = "last week copy";
	private final String HEURISTIC = "most probable";
	private final String ALPHABET = "alphabetical";
	private final String CLEAR = "clear";
	
	
	public OrdersPanel() {
		storeLabel = new JLabel("Sklep: Not chosen");
		dateLabel = new JLabel("Data: Not chosen");
		table = new OrderTable();
		toTop = new JButton(TOTOP);
		lastWeek = new JButton(LASTWEEK);
		lastWeekCopy = new JButton(LASTWEEKCOPY);
		heuristic = new JButton(HEURISTIC);
		alphabetical = new JButton(ALPHABET);
		clear = new JButton(CLEAR);
		normal = new JButton(NORMAL);
		
		toTop.addActionListener(this);
		lastWeek.addActionListener(this);
		lastWeekCopy.addActionListener(this);
		heuristic.addActionListener(this);
		alphabetical.addActionListener(this);
		clear.addActionListener(this);
		normal.addActionListener(this);
		
		tableScroller = new JScrollPane(table);
		tableScroller.setPreferredSize(new Dimension(500, 700));
		
		MigLayout mig = new MigLayout("wrap 6");
		mig.setColumnConstraints("[grow][grow][grow][grow][grow][grow]");
		mig.setRowConstraints("[30]20[grow][grow][grow][grow][grow]30");
		this.setLayout(mig);
		
		this.add(dateLabel, "span 3, center");
		this.add(storeLabel, "span 2, center");
		this.add(tableScroller, "cell 0 1 6 3, center");
		this.add(normal, "center");
		this.add(alphabetical, "center");
		this.add(toTop, "center");
		this.add(lastWeek, "center");
//		this.add(heuristic, "center");
		this.add(clear, "center");
		this.add(lastWeekCopy, "cell 3 5 1 1, center");
		
	}

	public void setDate(String d) {
		dateLabel.setText("Data: " + d);
		table.setDate(d);
		table.update();
	}
	
	public void setStore(String s) {
		storeLabel.setText("Sklep: " + s);
		table.setStore(s);
		table.update();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case TOTOP:
				System.out.println("pressed to top");
				table.pushToTop();
				break;
			case LASTWEEK:
				System.out.println("pressed last week");
				table.sameAsLastWeek();
				break;
			case LASTWEEKCOPY:
				System.out.println("pressed last week");
				table.sameAsLastWeekCopy();
				break;
			case HEURISTIC:
				System.out.println("pressed heuristic");
				table.heuristic();
				break;
			case CLEAR:
				System.out.println("pressed clear");
				table.clear();
				break;
			case NORMAL:
				System.out.println("pressed normal");
				table.update();
				break;
			case ALPHABET:
				System.out.println("pressed normal");
				table.alphabetical();
				break;
				
		}
		
		table.repaint();
		tableScroller.getViewport().setViewPosition(new Point(0,0));
			
	}
	

}
