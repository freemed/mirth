/** MapperPanel.java
 *
 * 	@author  franciscos
 * 	Created on June 21, 2006, 4:38 PM
 */


package com.webreach.mirth.client.ui.editors;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.Ostermiller.Syntax.HighlightedDocument;
import com.webreach.mirth.client.ui.PlatformUI;
import com.webreach.mirth.model.Channel;


public class MapperPanel extends CardPanel {
	
	/** Creates new form MapperPanel */
	public MapperPanel() {initComponents();}
	public MapperPanel(MirthEditorPane p) {
		super();
		parent = p;
		
		initComponents();
	}
	
	/** initialize components and set layout;
	 *  originally created with NetBeans, modified by franciscos
	 */
	private void initComponents() {
		referenceScrollPane = new JScrollPane();
		refTree = new TabbedReferencePanel();
		refPanel = new JPanel();
		mappingPanel = new JPanel();
		labelPanel = new JPanel();
		mappingLabel = new JLabel( "   " + label );
		mappingTextField = new JTextField();
		mappingScrollPane = new JScrollPane();
		mappingDoc = new HighlightedDocument();
		mappingDoc.setHighlightStyle( HighlightedDocument.JAVASCRIPT_STYLE );
		mappingTextPane = new JTextPane( mappingDoc );
		
		referenceScrollPane.setBorder( BorderFactory.createEmptyBorder() );
		mappingPanel.setBorder( BorderFactory.createEmptyBorder() );
		mappingTextField.setBorder( BorderFactory.createEtchedBorder() );
		mappingTextPane.setBorder( BorderFactory.createEmptyBorder() );
		mappingScrollPane.setBorder( BorderFactory.createTitledBorder( 
				BorderFactory.createLoweredBevelBorder(), "Mapping: ", TitledBorder.LEFT,
				TitledBorder.ABOVE_TOP, new Font( null, Font.PLAIN, 11 ), 
				Color.black ));
		
		mappingTextPane.setFont( EditorConstants.DEFAULT_FONT );
		
		refPanel.setBorder( BorderFactory.createEmptyBorder() );
		refPanel.setLayout( new BorderLayout() );
		refPanel.add( referenceScrollPane, BorderLayout.CENTER );
		
		JLabel padding = new JLabel( "  " );
		padding.setFont( new Font( null, Font.PLAIN, 8 ) );
		labelPanel.setLayout( new BorderLayout() );
		labelPanel.add( mappingLabel, BorderLayout.NORTH );
		labelPanel.add( padding, BorderLayout.WEST );
		labelPanel.add( mappingTextField, BorderLayout.CENTER );
		padding = new JLabel( "                             " );
		labelPanel.add( padding, BorderLayout.LINE_END );
		
		mappingPane = new JPanel();
		mappingPane.setLayout( new BorderLayout() );
		mappingPane.add( mappingTextPane, BorderLayout.CENTER );
		
		referenceScrollPane.setViewportView( refTree );
		mappingScrollPane.setViewportView( mappingPane );
		
		mappingPanel.setLayout( new BorderLayout() );
		mappingPanel.add( labelPanel, BorderLayout.NORTH );
		mappingPanel.add( mappingScrollPane, BorderLayout.CENTER );
		
		hSplitPane = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT, 
				mappingPanel, refPanel );
		hSplitPane.setContinuousLayout( true );
		hSplitPane.setDividerLocation( 450 );
		
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
		
		this.addComponentListener(
				new ComponentListener() {
					
					public void componentResized(ComponentEvent arg0) {}
					
					public void componentMoved(ComponentEvent arg0) {}
//					this will ensure the variable field is given the proper label
					public void componentShown(ComponentEvent arg0) {
						Channel channel = PlatformUI.MIRTH_FRAME.channelEditPage.currentChannel;
						if ( channel.getDirection().equals(Channel.Direction.INBOUND) )
							mappingLabel.setText( "   Variable: " );
						else if ( channel.getDirection().equals(Channel.Direction.OUTBOUND) )
							mappingLabel.setText( "   HL7 Message Segment: " );
					}
					
					public void componentHidden(ComponentEvent arg0) {}
					
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
	
	
	private String label;
	private JScrollPane referenceScrollPane;
	private JPanel refPanel;
	private JSplitPane hSplitPane;
	private JTextPane mappingTextPane;
	private HighlightedDocument mappingDoc;
	private TabbedReferencePanel refTree;
	private JLabel mappingLabel;
	private JPanel labelPanel;
	private JPanel mappingPane;
	private JPanel mappingPanel;
	private JTextField mappingTextField;
	private JScrollPane mappingScrollPane;
	private MirthEditorPane parent;
}
