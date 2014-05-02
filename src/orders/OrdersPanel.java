package orders;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.TableColumn;

import orders.MyTableCellEditor;
import net.miginfocom.swing.MigLayout;

public class OrdersPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	private JLabel storeLabel;
	private JLabel dateLabel;
	private OrderTable table;
	
	public OrdersPanel() {
		storeLabel = new JLabel("Sklep: Not chosen");
		dateLabel = new JLabel("Data: Not chosen");
		table = new OrderTable();
		
		table.setShowVerticalLines(false);
        table.setShowHorizontalLines(true);
        table.setGridColor(Color.BLACK);
        
        TableColumn col = table.getColumnModel().getColumn(0);
        MyTableCellEditor myTableCellEditor = new MyTableCellEditor();
        col.setCellEditor(myTableCellEditor);
		
		JScrollPane tableScroller = new JScrollPane(table);
		tableScroller.setPreferredSize(new Dimension(400, 500));
		
		MigLayout mig = new MigLayout("wrap 4");
		mig.setColumnConstraints("[grow][grow][grow][grow]");
		mig.setRowConstraints("[30]20[grow][grow][grow]");
		this.setLayout(mig);
		
		this.add(dateLabel, "span 2, center");
		this.add(storeLabel, "span 2, center");
		this.add(tableScroller, "cell 0 1 4 3, center");
		
	}

	
	
	public void setDate(String d) {
		dateLabel.setText("Data: " + d);
		
	}
	
	public void setStore(String s) {
		storeLabel.setText("Sklep: " + s);
	}
	

}
