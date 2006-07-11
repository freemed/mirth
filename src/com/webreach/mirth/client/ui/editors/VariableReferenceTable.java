package com.webreach.mirth.client.ui.editors;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.webreach.mirth.client.ui.PlatformUI;

public class VariableReferenceTable extends ReferenceTable {

	public VariableReferenceTable (String[] data ) {
		super();
		
		String[][] d = new String[data.length][2];
		for ( int i = 0;  i < data.length;  i++ ) {
			d[i][0] = data[i];
			d[i][1] = null;
		}
		
		this.setModel( new DefaultTableModel( d,
				new String[] {"Variable"} ) {
					public boolean isCellEditable ( int row, int col ) {
						return false;
					}
				});
       
       this.getColumnExt( "Variable" ).setPreferredWidth( 80 );
       this.getColumnExt( "Variable" ).setHeaderRenderer( PlatformUI.CENTER_COLUMN_HEADER_RENDERER );
	}
	
	public VariableReferenceTable (String[][] data) {
		super();
		
		this.setModel( new DefaultTableModel( data,
				new String[] {"Variable","Note"} ) {
					public boolean isCellEditable ( int row, int col ) {
						return false;
					}
				});
       
       this.getColumnExt( "Variable" ).setMaxWidth( 125 );
       this.getColumnExt( "Variable" ).setPreferredWidth( 80 );
       
       DefaultTableCellRenderer highlightColumn = new DefaultTableCellRenderer();
       highlightColumn.setBackground( EditorConstants.PANEL_BACKGROUND );
       this.getColumnExt( "Note" ).setCellRenderer( highlightColumn );
       
       this.getColumnExt( "Variable" ).setHeaderRenderer( PlatformUI.CENTER_COLUMN_HEADER_RENDERER );
       this.getColumnExt( "Note" ).setHeaderRenderer( PlatformUI.CENTER_COLUMN_HEADER_RENDERER );
	
	
	}
	
}
