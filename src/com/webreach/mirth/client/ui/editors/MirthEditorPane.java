package com.webreach.mirth.client.ui.editors;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.webreach.mirth.client.ui.Frame;
import com.webreach.mirth.client.ui.PlatformUI;

public class MirthEditorPane extends JPanel {
	public boolean modified = false;
    public Frame parent = PlatformUI.MIRTH_FRAME;
    public JScrollPane referenceScrollPane;
	public JPanel refPanel;
	public TabbedReferencePanel tabPanel;
	
    public MirthEditorPane() {
    	super();
    	
		tabPanel = new TabbedReferencePanel();
		
		referenceScrollPane = new JScrollPane();
    	referenceScrollPane.setBorder( BorderFactory.createEmptyBorder() );
		referenceScrollPane.setViewportView( tabPanel );

    	refPanel = new JPanel();
    	refPanel.setBorder( BorderFactory.createEmptyBorder() );
		refPanel.setLayout( new BorderLayout() );
		refPanel.add( referenceScrollPane, BorderLayout.CENTER );
		
//		let the parent decide how big this should be
    	this.setPreferredSize( new Dimension( 0, 0 ) );
    }
    
    public void update() {
    	tabPanel.update();
	}
    
    public void setDroppedTextPrefix(String prefix){
    	tabPanel.setDroppedTextPrefix( prefix );
    }
}
