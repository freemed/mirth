package com.webreach.mirth.client.ui.editors;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import com.webreach.mirth.client.ui.Frame;
import com.webreach.mirth.client.ui.PlatformUI;
import com.webreach.mirth.client.ui.UIConstants;

public class NotesReferenceTable extends ReferenceTable {

	public NotesReferenceTable () {
		super();
		
		String[][] referenceData = new String[2][2];
		
		referenceData[0][0] = "$localMap";
		referenceData[0][1] = "The local variable map that will be sent to the connector.";
		
		referenceData[1][0] = "$globalMap";
		referenceData[1][1] = "The global variable map that persists values between channels.";
        
        this.setModel( new DefaultTableModel( referenceData,
				new String[] {"Variable","Note"} ) {
					public boolean isCellEditable ( int row, int col ) {
						return false;
					}
				});
       
       this.getColumnExt( "Variable" ).setMaxWidth( 125 );
       this.getColumnExt( "Variable" ).setPreferredWidth( 100 );
       
       DefaultTableCellRenderer highlightColumn = new DefaultTableCellRenderer();
       highlightColumn.setBackground( EditorConstants.PANEL_BACKGROUND );
       this.getColumnExt( "Note" ).setCellRenderer( highlightColumn );
       
       this.getColumnExt( "Variable" ).setHeaderRenderer( PlatformUI.CENTER_COLUMN_HEADER_RENDERER );
       this.getColumnExt( "Note" ).setHeaderRenderer( PlatformUI.CENTER_COLUMN_HEADER_RENDERER );
	}
	
	private Frame parent = PlatformUI.MIRTH_FRAME;
	
}
