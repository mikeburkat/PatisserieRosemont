package stores;

import java.awt.Color;
import java.awt.Component;
import java.util.HashSet;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

public class OrderedListRenderer extends DefaultListCellRenderer {

	public HashSet haveOrder = new HashSet();
	
	@Override
	public Component getListCellRendererComponent(JList list,
			Object value, int index, boolean selected, boolean focus) {
		
		super.getListCellRendererComponent( list, value, index,
                selected, focus );

        if( haveOrder.contains( value ) )
        {
            setForeground( Color.red );
        }
        else
        {
            setForeground( Color.black );
        }

        return( this );
	}

	public void updateOrderedSet(HashSet h) {
		haveOrder = h;
	}

}
