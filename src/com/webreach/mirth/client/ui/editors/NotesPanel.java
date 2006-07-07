package com.webreach.mirth.client.ui.editors;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;

public class NotesPanel extends JTextArea {

	public NotesPanel( String notes ){
		super( notes + "\n" );
		this.setBackground( Color.WHITE );
		this.setForeground( Color.BLUE );
		this.setFont( new Font( "SansSerif", Font.PLAIN, 11 ) );
		this.setBorder( BorderFactory.createEtchedBorder() );
		this.setEditable(false);
		this.setLineWrap(true);
		this.setTabSize( 4 );
	}
}
