package com.webreach.mirth.client.ui.editors;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import com.webreach.mirth.client.ui.Frame;
import com.webreach.mirth.client.ui.PlatformUI;

public class MirthEditorPane extends JPanel {
	public boolean modified = false;
    public Frame parent = PlatformUI.MIRTH_FRAME;
    
    public MirthEditorPane() {
    	super();
    	
//		let the parent decide how big this should be
    	this.setPreferredSize( new Dimension( 0, 0 ) );
    }    
}
