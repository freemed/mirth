package com.webreach.mirth.client.ui.editors;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.webreach.mirth.client.ui.PlatformUI;


public class VariableReferenceTable extends ReferenceTable {
	private Object[] tooltip;
	
	public VariableReferenceTable () {
		super();
		makeTable( null, null );
	}
	
	public VariableReferenceTable ( Object[] data ) {
		super();
		makeTable( data, null );
	}
	
	public VariableReferenceTable ( Object[] data, Object[] tooltip ) {
		super();
		makeTable( data, tooltip );
	}
	
	
	private void makeTable(Object[] data, Object[] tooltip) {
		if (data == null) return;
		
		this.tooltip = tooltip;
		
		Object[][] d = new String[data.length][2];
		for ( int i = 0;  i < data.length;  i++ ) {
			d[i][0] = data[i];
			d[i][1] = null;
		}
		
		this.setModel( new DefaultTableModel( d,
				new Object[] {"Variable"} ) {
			public boolean isCellEditable ( int row, int col ) {
				return false;
			}
		});
		
		this.getColumnExt( "Variable" ).setPreferredWidth( 80 );
		this.getColumnExt( "Variable" ).setHeaderRenderer( PlatformUI.CENTER_COLUMN_HEADER_RENDERER );
		
	}
	
	public String getToolTipText( MouseEvent event ) {
		Point p = event.getPoint();
		int col = columnAtPoint(p);
		int row = rowAtPoint(p);
		if ( col >= 0  &&  row >= 0  &&  tooltip != null ) {
			Object o = getValueAt( row, col );
			if ( o != null ) 
				return "<html><body style=\"width:150px\"><p>" + 
						tooltip[row] + 
						"</p></body></html>";
		}
		return null;
	}
	
}
