/** MapperPanel.java
 *
 * 	@author  franciscos
 * 	Created on June 21, 2006, 4:38 PM
 */


package com.webreach.mirth.client.ui.editors;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.layout.*;

import com.webreach.mirth.client.ui.editors.transformer.TransformerPane;
import com.webreach.mirth.client.ui.util.HL7ReferenceLoader;

import com.Ostermiller.Syntax.*;


public class MapperPanel extends CardPanel {
	
	/** Creates new form MapperPanel */
	public MapperPanel(MirthEditorPane p, String n) {
		super();
		parent = p;
		notes = n;
		this.setBorder( BorderFactory.createEmptyBorder() );
		this.setPreferredSize( new Dimension( 0, 0 ) );
		initComponents();
	}
	
	/** initialize components and set layout;
	 *  originally created with NetBeans, modified by franciscos
	 */
	private void initComponents() {
		referenceScrollPane = new JScrollPane();
		refTable = new HL7ReferenceTable();
		refPanel = new JPanel();
		hSplitPane = new JSplitPane();
		mappingPanel = new JPanel();
		labelPanel = new JPanel();
		mappingLabel = new JLabel( "   Variable: " );
		mappingTextField = new JTextField();
		mappingScrollPane = new JScrollPane();
		mappingDoc = new HighlightedDocument();
		mappingDoc.setHighlightStyle( HighlightedDocument.JAVASCRIPT_STYLE );
		mappingTextPane = new JTextPane( mappingDoc );
		
		referenceScrollPane.setBorder( BorderFactory.createEmptyBorder() );
		hSplitPane.setBorder( BorderFactory.createEmptyBorder() );
		mappingPanel.setBorder( BorderFactory.createEmptyBorder() );
		mappingTextField.setBorder( BorderFactory.createEtchedBorder() );
		mappingTextPane.setBorder( BorderFactory.createEmptyBorder() );
		mappingScrollPane.setBorder( BorderFactory.createTitledBorder( 
				BorderFactory.createEtchedBorder(), "Mapping: ", TitledBorder.LEFT,
				TitledBorder.ABOVE_TOP, new Font( null, Font.PLAIN, 11 ), 
				Color.black ));
		
		refPanel.setBorder( BorderFactory.createEmptyBorder() );
		refPanel.setLayout( new BorderLayout() );
		if ( notes != null )
			refPanel.add( new NotesPanel( notes ), BorderLayout.NORTH );
		refPanel.add( referenceScrollPane, BorderLayout.CENTER );
		
		JLabel padding = new JLabel( "  " );
		padding.setFont( new Font( null, Font.PLAIN, 8 ) );
		labelPanel.setLayout( new BorderLayout() );
		labelPanel.add( mappingLabel, BorderLayout.NORTH );
		labelPanel.add( padding, BorderLayout.WEST );
		labelPanel.add( mappingTextField, BorderLayout.CENTER );
		padding = new JLabel( "                             " );
		labelPanel.add( padding, BorderLayout.LINE_END );
		
		referenceScrollPane.setViewportView( refTable );
		mappingScrollPane.setViewportView( mappingTextPane );
		
		mappingPanel.setLayout( new BorderLayout() );
		mappingPanel.add( labelPanel, BorderLayout.NORTH );
		mappingPanel.add( mappingScrollPane, BorderLayout.CENTER );
		
		hSplitPane.setOneTouchExpandable( true );
		hSplitPane.setDividerSize( 7 );
		hSplitPane.setDividerLocation( 450 );
		hSplitPane.setLeftComponent( mappingPanel );
		hSplitPane.setRightComponent( refPanel );        
		
		
		// BGN listeners
		mappingTextField.getDocument().addDocumentListener(
				new DocumentListener() {
					public void changedUpdate(DocumentEvent arg0) {
						parent.modified = true;
					}
					
					public void insertUpdate(DocumentEvent arg0) {
						parent.modified = true;						
					}
					
					public void removeUpdate(DocumentEvent arg0) {
						parent.modified = true;						
					}
					
				});
		
		mappingTextPane.getDocument().addDocumentListener(
				new DocumentListener() {
					public void changedUpdate(DocumentEvent arg0) {
						parent.modified = true;
					}
					
					public void insertUpdate(DocumentEvent arg0) {
						parent.modified = true;						
					}
					
					public void removeUpdate(DocumentEvent arg0) {
						parent.modified = true;						
					}
					
				});
		// END listeners
		
		this.setLayout( new BorderLayout() );
		this.add( hSplitPane, BorderLayout.CENTER );
	} 
	
	
	
	public Map<Object, Object> getData() {
		Map<Object, Object> m = new HashMap<Object, Object>();
		m.put( "Variable", mappingTextField.getText().trim() );
		m.put( "Mapping", mappingTextPane.getText().trim() );
		
		return m;
	}
	
	
	public void setData( Map<Object, Object> data ) {
		if ( data != null ) {
			mappingTextField.setText( (String)data.get( "Variable" ) );
			mappingTextPane.setText( (String)data.get( "Mapping" ) );
		} else {
			mappingTextField.setText( "" );
			mappingTextPane.setText( "" );
		}
	}    
	
	
	String notes;
	private JScrollPane referenceScrollPane;
	private JPanel refPanel;
	private JSplitPane hSplitPane;
	private JTextPane mappingTextPane;
	private HighlightedDocument mappingDoc;
	private HL7ReferenceTable refTable;
	private JLabel mappingLabel;
	private JPanel labelPanel;
	private JPanel mappingPanel;
	private JTextField mappingTextField;
	private JScrollPane mappingScrollPane;
	private MirthEditorPane parent;
	
}
