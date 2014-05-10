package orders;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.TableColumn;

import orders.MyTableCellEditor;
import net.miginfocom.swing.MigLayout;

public class OrdersPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JLabel storeLabel;
	private JLabel dateLabel;
	private OrderTable table;
	JScrollPane tableScroller;
	private JButton toTop;
	private JButton lastWeek;
	private JButton heuristic;
	private JButton clear;
	private JButton normal;
	private final String TOTOP = "to top";
	private final String LASTWEEK = "last week";
	private final String HEURISTIC = "most probable";
	private final String CLEAR = "clear";
	private final String NORMAL = "normal";
	
	public OrdersPanel() {
		storeLabel = new JLabel("Sklep: Not chosen");
		dateLabel = new JLabel("Data: Not chosen");
		table = new OrderTable();
		toTop = new JButton(TOTOP);
		lastWeek = new JButton(LASTWEEK);
		heuristic = new JButton(HEURISTIC);
		clear = new JButton(CLEAR);
		normal = new JButton(NORMAL);
		
		toTop.addActionListener(this);
		lastWeek.addActionListener(this);
		heuristic.addActionListener(this);
		clear.addActionListener(this);
		normal.addActionListener(this);
		
		tableScroller = new JScrollPane(table);
		tableScroller.setPreferredSize(new Dimension(500, 700));
		
		MigLayout mig = new MigLayout("wrap 5");
		mig.setColumnConstraints("[grow][grow][grow][grow][grow]");
		mig.setRowConstraints("[30]20[grow][grow][grow]30");
		this.setLayout(mig);
		
		this.add(dateLabel, "span 3, center");
		this.add(storeLabel, "span 2, center");
		this.add(tableScroller, "cell 0 1 5 3, center");
		this.add(normal, "center");
		this.add(toTop, "center");
		this.add(lastWeek, "center");
		this.add(heuristic, "center");
		this.add(clear, "center");
		
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
		}
		
		table.repaint();
		tableScroller.getViewport().setViewPosition(new Point(0,0));
			
	}
	

}
