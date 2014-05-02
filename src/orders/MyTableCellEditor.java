package orders;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.DefaultCellEditor;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.KeyStroke;

public class MyTableCellEditor extends DefaultCellEditor {
	// original was: extends AbstractCellEditor implements TableCellEditor

	private static final long serialVersionUID = 1L;
	// This is the component that will handle the editing of the cell value
	JFormattedTextField ftf;

	public MyTableCellEditor() {
		super(new JFormattedTextField());
		ftf = (JFormattedTextField) getComponent();
		ftf.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
				"check");
		ftf.getActionMap().put("check", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try { // The text is valid,
					ftf.commitEdit(); // so use it.
					ftf.postActionEvent(); // stop editing
				} catch (java.text.ParseException exc) {
				}
			}
		});
	}

	// This method is called when a cell value is edited by the user.

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int rowIndex, int vColIndex) {
		// 'value' is value contained in the cell located at (rowIndex,
		// vColIndex)
		ftf = new JFormattedTextField();
		// NumberFormat nf = NumberFormat.getIntegerInstance();

		if (isSelected) {
			// cell (and perhaps other cells) are selected
		}

		// Configure the component with the specified value
		// ((JFormattedTextField)component).setText(nf.format(value));
		((JFormattedTextField) ftf).setText(value.toString());
		((JFormattedTextField) ftf).selectAll();
		// System.out.println("Formatting "+nf.format(value));
		// table.setValueAt(value, rowIndex, vColIndex);

		// Return the configured component
		return ftf;
	}

	// This method is called when editing is completed.
	// It must return the new value to be stored in the cell.
	@Override
	public Object getCellEditorValue() {
		System.out.println("getCellEditorValue: "
				+ Double.valueOf(ftf.getText()));
		return Double.valueOf(((JFormattedTextField) ftf).getText());

	}

}
