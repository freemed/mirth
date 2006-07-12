package com.webreach.mirth.client.ui;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

import com.webreach.mirth.client.ui.editors.ReferenceTable;

public class ReferenceTableHandler extends TransferHandler {
	
	protected Transferable createTransferable( JComponent c ) {
		try {
			ReferenceTable reftable = ((ReferenceTable)( c ));
			int currRow = reftable.getSelectedRow();
		
			if ( reftable == null ) return null;
			
			String text;
			if (currRow >= 0 && currRow < reftable.getRowCount() ) 
				text = (String)reftable.getModel().getValueAt(reftable.getSelectedRow(), 0);
			else text = "";
			
			return new ReferenceTableTransferable( text );
		}
		catch ( ClassCastException cce ) {
			return null;
		}
	}
	
	public int getSourceActions( JComponent c ) {
		return COPY;
	}
	
	public boolean canImport( JComponent c, DataFlavor[] df ) {
		return false;
	}
}