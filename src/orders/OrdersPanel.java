package orders;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.miginfocom.swing.MigLayout;

public class OrdersPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JLabel storeLabel;
	private JLabel dateLabel;
	private JLabel searchLabel;
	private JTextField searchField;
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
		searchLabel = new JLabel("Search:");
		searchField = new JTextField("", 50);
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
		mig.setRowConstraints("[30]20[grow][grow][grow][grow][grow][grow][grow][grow]30");
		this.setLayout(mig);
		
		this.add(dateLabel, "cell 0 0 3 1, center");
		this.add(storeLabel, "cell 1 0 3 1, center");
		this.add(searchLabel, "cell 1 1 1 1, center");
		this.add(searchField, "span 2 1 5 1, center");
		this.add(tableScroller, "cell 0 2 6 4, center");
		this.add(normal, "center");
		this.add(alphabetical, "center");
		this.add(toTop, "center");
		this.add(lastWeek, "center");
//		this.add(heuristic, "center");
		this.add(clear, "center");
		this.add(lastWeekCopy, "cell 3 7 1 1, center");
		
		searchField.getDocument().addDocumentListener((SearchDocumentListener) e -> {
			System.out.println("Search: " + searchField.getText());
			table.search(searchField.getText());
			table.repaint();
			tableScroller.getViewport().setViewPosition(new Point(0,0));
		});
		
		searchField.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "jumpToTable");
		searchField.getActionMap().put("jumpToTable", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
			System.out.println("jumpToTable");
			table.requestFocusInWindow();
			table.changeSelection(0, 0, false, false);
		}
	});
		
		table.setSearchField(searchField);
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

	@FunctionalInterface
	public interface SearchDocumentListener extends DocumentListener {
	    void textUpdate(DocumentEvent e);

	    @Override
	    default void insertUpdate(DocumentEvent e) {
	        textUpdate(e);
	    }
	    @Override
	    default void removeUpdate(DocumentEvent e) {
	        textUpdate(e);
	    }
	    @Override
	    default void changedUpdate(DocumentEvent e) {
	        textUpdate(e);
	    }
	}
}
