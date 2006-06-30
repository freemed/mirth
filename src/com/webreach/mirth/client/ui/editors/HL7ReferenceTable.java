package com.webreach.mirth.client.ui.editors;

import java.io.IOException;
import javax.swing.table.DefaultTableModel;
import com.webreach.mirth.client.ui.Frame;
import com.webreach.mirth.client.ui.PlatformUI;
import com.webreach.mirth.client.ui.util.HL7ReferenceLoader;


public class HL7ReferenceTable extends ReferenceTable {

	public HL7ReferenceTable () {
		super();
		
		String[][] referenceData = null;
        
        try {
        	referenceData = ( new HL7ReferenceLoader()).getReferenceTable();
        } catch (IOException e) {
        	parent.alertError("Could not load HL7 Reference Table!\n\n"
        			+ e.getMessage() );
        	System.err.println( "Could not load HL& Reference Table!" );
        	e.printStackTrace(); 
        }
        
       this.setModel( new DefaultTableModel( referenceData, 
				new String[] {"ID","Description","Chapter"} ) {
					boolean[] canEdit = new boolean [] { false, false, false };
					
					public boolean isCellEditable ( int row, int col ) {
						return canEdit[col];
					}
				});
       
       this.getColumnExt( "ID" ).setMaxWidth( 30 );
       this.getColumnExt( "ID" ).setMinWidth( 30 );
       this.getColumnExt( "Chapter" ).setMaxWidth( 55 );
       this.getColumnExt( "Chapter" ).setMinWidth( 55 );
	}
	
	private Frame parent = PlatformUI.MIRTH_FRAME;
}
