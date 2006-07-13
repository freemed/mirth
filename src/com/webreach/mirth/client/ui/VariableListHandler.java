package com.webreach.mirth.client.ui;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

import org.jdesktop.swingx.JXList;

import com.webreach.mirth.client.ui.components.MirthVariableList;
import com.webreach.mirth.client.ui.editors.ReferenceTable;

public class VariableListHandler extends TransferHandler {
	
	protected Transferable createTransferable( JComponent c ) {
		try {
			JXList list = ((JXList)( c ));
			if ( list == null ) return null;
			String text = (String)list.getSelectedValue();
			if (text != null){
				return new VariableTransferable( text, "${", "}" );
			}
			return null;
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