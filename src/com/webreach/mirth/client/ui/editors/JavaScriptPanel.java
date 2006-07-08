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
import javax.swing.JTextPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.Ostermiller.Syntax.HighlightedDocument;
import com.webreach.mirth.client.ui.UIConstants;


/**
 * @author franciscos
 *
 */
public class JavaScriptPanel extends CardPanel {
	
	public JavaScriptPanel(MirthEditorPane p, String notes) {
		super();
		parent = p;
		this.notes = notes;
		initComponents();
	}
	
	public JavaScriptPanel(MirthEditorPane p) {
		this(p, null);
	}
	
	private void initComponents() {
		refTree = new HL7ReferenceTree();
		referenceScrollPane = new JScrollPane();
		headerArea = new JTextArea( header );
		footerArea = new JTextArea( footer );
		refPanel = new JPanel();
		mappingPane = new JPanel();
		mappingDoc = new HighlightedDocument();
		mappingDoc.setHighlightStyle( HighlightedDocument.JAVASCRIPT_STYLE );
		mappingScrollPane = new JScrollPane();
		editor = new JPanel();
		mappingTextPane = new JTextPane( mappingDoc );
		
		mappingTextPane.setBorder( BorderFactory.createEmptyBorder() );
		mappingPane.setBorder( BorderFactory.createEmptyBorder() );
		referenceScrollPane.setBorder( BorderFactory.createEmptyBorder() );
		referenceScrollPane.setViewportView( refTree );
		
		headerArea.setForeground( Color.blue );
		headerArea.setFont( new Font( "Monospaced", Font.BOLD, 12 ) );
		headerArea.setBorder( BorderFactory.createEmptyBorder() );
		headerArea.setBackground( UIConstants.NONEDITABLE_LINE_BACKGROUND );
		headerArea.setEditable(false);
		
		footerArea.setForeground( Color.blue );
		footerArea.setFont( new Font( "Monospaced", Font.BOLD, 12 ) );
		footerArea.setBorder( BorderFactory.createEmptyBorder() );
		footerArea.setBackground( UIConstants.NONEDITABLE_LINE_BACKGROUND );
		footerArea.setEditable(false);
		
		mappingPane.setLayout( new BorderLayout() );
		mappingPane.add( headerArea, BorderLayout.NORTH );
		mappingPane.add( mappingTextPane, BorderLayout.CENTER );
		mappingPane.add( footerArea, BorderLayout.SOUTH );
		
		lineCount = 1;
		lineNumbers = new JTextArea( Integer.toString(lineCount) );
		Font lineNumbersFont = new Font("Monospaced", Font.PLAIN, 12);
		lineNumbers.setEditable( false );
		lineNumbers.setFont( lineNumbersFont );
		lineNumbers.setForeground( Color.DARK_GRAY );
		lineNumbers.setBackground( UIConstants.GRID_COLOR );
		lineNumbers.setSize( lineNumbersFont.getSize() * 3, mappingTextPane.getHeight() );
		
		editor.setLayout( new BorderLayout() );
		editor.add( lineNumbers, BorderLayout.WEST );
		editor.add( mappingPane, BorderLayout.CENTER );

		mappingScrollPane.setViewportView( editor );
		mappingScrollPane.setBorder( BorderFactory.createTitledBorder( 
				BorderFactory.createLoweredBevelBorder(), "JavaScript", TitledBorder.LEFT,
				TitledBorder.ABOVE_TOP, new Font( null, Font.PLAIN, 11 ), 
				Color.black ));
		
		refPanel.setBorder( BorderFactory.createEmptyBorder() );
		refPanel.setLayout( new BorderLayout() );
		if ( notes != null )
			refPanel.add( new NotesPanel( notes ), BorderLayout.NORTH );
		refPanel.add( referenceScrollPane, BorderLayout.CENTER );
		
		hSplitPane = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT, 
				mappingScrollPane, refPanel );
		hSplitPane.setContinuousLayout( true );
		hSplitPane.setDividerLocation( 450 );
		
		//BGN listeners
		mappingTextPane.getDocument().addDocumentListener(
				new DocumentListener() {
					String numbers = "";
					
					public void changedUpdate(DocumentEvent arg0) {
						updateDoc();
					}
					
					public void insertUpdate(DocumentEvent arg0) {
						//updateDoc();				
					}
					
					public void removeUpdate(DocumentEvent arg0) {
						//updateDoc();
					}
					
					private void updateDoc() {
						parent.modified = true;
						int currLineCount = 
							mappingTextPane.getDocument().getDefaultRootElement().getElementCount();
						
						if ( lineCount < currLineCount ) {
							for ( int i = 1;  i < currLineCount;  i++ )
								numbers += i + "\n";
							lineCount = currLineCount;
							lineNumbers.setText( numbers );
						}
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
		else
			mappingTextPane.setText( "" );
	}
	
	public JTextPane getDocument() {
		return mappingTextPane;
	}
	
	String notes;
	private JPanel refPanel;
	private JTextArea headerArea;
	private JTextArea footerArea;
	private JPanel mappingPane;
	private HighlightedDocument mappingDoc;
	private JTextPane mappingTextPane;
	private JScrollPane mappingScrollPane;
	private JTextArea lineNumbers;
	private int lineCount;
	private JPanel editor;
	private JSplitPane hSplitPane;
	private JScrollPane referenceScrollPane;
	private HL7ReferenceTree refTree;
	private MirthEditorPane parent;
	private String header = "{";
	private String footer = "}";
	
}
