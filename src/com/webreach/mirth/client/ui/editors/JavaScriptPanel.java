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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.Ostermiller.Syntax.HighlightedDocument;
import com.webreach.mirth.client.ui.MirthTextPane;
import com.webreach.mirth.client.ui.UIConstants;


/**
 * @author franciscos
 *
 */
public class JavaScriptPanel extends CardPanel {
	
	public JavaScriptPanel(MirthEditorPane p) {
		super();
		parent = p;
		initComponents();
	}
	
	private void initComponents() {
		tabPanel = new TabbedReferencePanel();
		headerArea = new JTextArea( header );
		footerArea = new JTextArea( footer );
		refPanel = new JPanel();
		mappingPane = new JPanel();
		mappingDoc = new HighlightedDocument();
		mappingDoc.setHighlightStyle( HighlightedDocument.JAVASCRIPT_STYLE );
		mappingScrollPane = new JScrollPane();
		mappingTextPane = new MirthTextPane( mappingDoc );
		
		mappingTextPane.setBorder( BorderFactory.createEmptyBorder() );
		mappingPane.setBorder( BorderFactory.createEmptyBorder() );
		
		headerArea.setForeground( Color.blue );
		headerArea.setFont( EditorConstants.DEFAULT_FONT_BOLD );
		headerArea.setBorder( BorderFactory.createEmptyBorder() );
		headerArea.setBackground( UIConstants.NONEDITABLE_LINE_BACKGROUND );
		headerArea.setEditable(false);
		
		mappingTextPane.setFont( EditorConstants.DEFAULT_FONT );
		
		footerArea.setForeground( Color.blue );
		footerArea.setFont( EditorConstants.DEFAULT_FONT_BOLD );
		footerArea.setBorder( BorderFactory.createEmptyBorder() );
		footerArea.setBackground( UIConstants.NONEDITABLE_LINE_BACKGROUND );
		footerArea.setEditable(false);
		
		mappingPane.setLayout( new BorderLayout() );
		mappingPane.add( headerArea, BorderLayout.NORTH );
		mappingPane.add( mappingTextPane, BorderLayout.CENTER );
		mappingPane.add( footerArea, BorderLayout.SOUTH );
		
		lineNumbers = new LineNumber( mappingPane );
		mappingScrollPane.setViewportView( mappingPane );
		mappingScrollPane.setRowHeaderView( lineNumbers );
		mappingScrollPane.setBorder( BorderFactory.createTitledBorder( 
				BorderFactory.createLoweredBevelBorder(), "JavaScript", TitledBorder.LEFT,
				TitledBorder.ABOVE_TOP, new Font( null, Font.PLAIN, 11 ), 
				Color.black ));
		
		refPanel.setBorder( BorderFactory.createEmptyBorder() );
		refPanel.setLayout( new BorderLayout() );
		refPanel.add( tabPanel, BorderLayout.CENTER );
		
		hSplitPane = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT, 
				mappingScrollPane, refPanel );
		hSplitPane.setContinuousLayout( true );
		hSplitPane.setDividerLocation( 450 );
		
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
	
	public void update(){
		tabPanel.update();
	}
	
	public Map<Object, Object> getData() {
		Map<Object, Object> m = new HashMap<Object, Object>();
		m.put( "Script", mappingTextPane.getText().trim() );
		return m;
	}
	
	
	public void setData( Map<Object, Object> m ) {
		if ( m != null )
			mappingTextPane.setText( (String)m.get( "Script" ) );	
		else
			mappingTextPane.setText( "" );
	}
	
	public MirthTextPane getDocument() {
		return mappingTextPane;
	}
	
	private JPanel refPanel;
	private JTextArea headerArea;
	private JTextArea footerArea;
	private JPanel mappingPane;
	private HighlightedDocument mappingDoc;
	private MirthTextPane mappingTextPane;
	private JScrollPane mappingScrollPane;
	private LineNumber lineNumbers;
	private JSplitPane hSplitPane;
//	public to access updateVariable() method from parent
	public TabbedReferencePanel tabPanel;
	private MirthEditorPane parent;
	private String header = "{";
	private String footer = "}";
	
}
