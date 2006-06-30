/* ***** BEGIN LICENSE BLOCK *****
 * Version: MPL 1.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is Mirth.
 *
 * The Initial Developer of the Original Code is
 * WebReach, Inc.
 * Portions created by the Initial Developer are Copyright (C) 2006
 * the Initial Developer. All Rights Reserved.
 *
 * Contributor(s):
 *   Chris Lang <chrisl@webreachinc.com>
 *
 * ***** END LICENSE BLOCK ***** */
package com.webreach.mirth.client.ui.editors;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import com.Ostermiller.Syntax.HighlightedDocument;
import com.webreach.mirth.client.ui.editors.transformer.TransformerPane;
import com.webreach.mirth.client.ui.util.HL7ReferenceLoader;

/**
 * @author chrisl
 *
 */
public class JavaScriptPanel extends CardPanel {

	public JavaScriptPanel(MirthEditorPane p) {
		super();
		parent = p;
		initComponents();
	}
	
	private void initComponents() {
		hSplitPane = new JSplitPane();
		refTable = new HL7ReferenceTable();
		treeScrollPane = new JScrollPane();
		headerLabel = new JLabel( header );
		footerLabel = new JLabel( footer );
		mappingPane = new JPanel();
		mappingDoc = new HighlightedDocument();
		mappingDoc.setHighlightStyle( HighlightedDocument.JAVASCRIPT_STYLE );
		mappingScrollPane = new JScrollPane();
		mappingTextPane = new JTextPane( mappingDoc );
		mappingTextPane.setBorder( BorderFactory.createEmptyBorder() );
		mappingPane.setBorder( BorderFactory.createEmptyBorder() );
		mappingPane.setBackground( new Color( 255, 255, 224 ) );
		treeScrollPane.setBorder( BorderFactory.createEmptyBorder() );
        treeScrollPane.setViewportView( refTable );
		
        headerLabel.setForeground( Color.blue );
        headerLabel.setFont( new Font( "Monospaced", Font.BOLD, 12 ) );
        headerLabel.setBorder( BorderFactory.createEmptyBorder() );
        footerLabel.setForeground( Color.blue );
        footerLabel.setFont( new Font( "Monospaced", Font.BOLD, 12 ) );
        footerLabel.setBorder( BorderFactory.createEmptyBorder() );
		
        mappingPane.setLayout( new BorderLayout() );
		mappingPane.add( headerLabel, BorderLayout.NORTH );
		mappingPane.add( mappingTextPane, BorderLayout.CENTER );
		mappingPane.add( footerLabel, BorderLayout.SOUTH );
		mappingScrollPane.setViewportView( mappingPane );
		mappingScrollPane.setBorder( BorderFactory.createTitledBorder( 
        		BorderFactory.createEtchedBorder(), "JavaScript", TitledBorder.LEFT,
        		TitledBorder.ABOVE_TOP, new Font( null, Font.PLAIN, 11 ), 
        		Color.black ));
		
		hSplitPane.setBorder( BorderFactory.createEmptyBorder() );
		hSplitPane.setOneTouchExpandable( true );
    	hSplitPane.setDividerSize( 7 );
    	hSplitPane.setDividerLocation( 450 );
        hSplitPane.setLeftComponent( mappingScrollPane );
        hSplitPane.setRightComponent( treeScrollPane );
        
		//BGN listeners
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
		//END listeners
		
		this.setLayout( new BorderLayout() );
		this.add( hSplitPane, BorderLayout.CENTER );
		
	}
	
	
	public Map<Object, Object> getData() {
		Map<Object, Object> m = new HashMap<Object, Object>();
		m.put( "Script", mappingTextPane.getText().trim() );
		return m;
	}
	
	
	public void setData( Map<Object, Object> m ) {
		if ( m != null )
			mappingTextPane.setText( (String)m.get( "Script" ) );	
	}

	
	private JLabel headerLabel;
	private JLabel footerLabel;
	private JPanel mappingPane;
	private HighlightedDocument mappingDoc;
	private JTextPane mappingTextPane;
	private JScrollPane mappingScrollPane;
	private JSplitPane hSplitPane;
    private JScrollPane treeScrollPane;
	private HL7ReferenceTable refTable;
	private MirthEditorPane parent;
	String header = "Start {\n";
	String footer = "\n} End";
	
}
