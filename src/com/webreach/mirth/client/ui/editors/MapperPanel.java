/** MapperPanel.java
 *
 * 	@author  franciscos
 * 	Created on June 21, 2006, 4:38 PM
 */


package com.webreach.mirth.client.ui.editors;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.*;
import org.jdesktop.layout.*;
import org.jdesktop.swingx.JXTree;

import com.Ostermiller.Syntax.*;


public class MapperPanel extends CardPanel {
    
    /** Creates new form MapperPanel */
    public MapperPanel() {
		super();
        initComponents();
    }
    
    /** initialize components and set layout;
     *  originally created with NetBeans, modified by franciscos
     */
    private void initComponents() {
    	treeScrollPane = new JScrollPane();
        helpTree = new HelpTree();
        hSplitPane = new JSplitPane();
        mappingPanel = new JPanel();
        mappingLabel = new JLabel( "Variable: " );
        mappingTextField = new JTextField();
        mappingScrollPane = new JScrollPane();
        mappingDoc = new HighlightedDocument();
		mappingDoc.setHighlightStyle( HighlightedDocument.JAVASCRIPT_STYLE );
        mappingTextPane = new JTextPane( mappingDoc );
        
        treeScrollPane.setBorder( BorderFactory.createEmptyBorder() );
        hSplitPane.setBorder( BorderFactory.createEmptyBorder() );
        mappingPanel.setBorder( BorderFactory.createEmptyBorder() );
        mappingTextField.setBorder( BorderFactory.createEtchedBorder() );
        mappingScrollPane.setBorder( BorderFactory.createLoweredBevelBorder() );
        mappingTextPane.setBorder( BorderFactory.createTitledBorder( 
        		BorderFactory.createEtchedBorder(), "Mapping", TitledBorder.LEFT,
        		TitledBorder.ABOVE_TOP, new Font( null, Font.PLAIN, 11 ), 
        		new Color( 0,0,0 ) ));
        this.setBorder( BorderFactory.createEmptyBorder() );
    	this.setPreferredSize( new Dimension( 1, 1 ) );
       
    	hSplitPane.setDividerLocation( 450 );
        hSplitPane.setLeftComponent( mappingPanel );
        hSplitPane.setRightComponent( treeScrollPane );
         
        mappingScrollPane.setViewportView( mappingTextPane );
        treeScrollPane.setViewportView( helpTree );

        // BGN NetBeans LAYOUT
        GroupLayout mappingPanelLayout = new GroupLayout(mappingPanel);
        mappingPanel.setLayout(mappingPanelLayout);
        mappingPanelLayout.setHorizontalGroup(
            mappingPanelLayout.createParallelGroup(GroupLayout.LEADING)
            .add(mappingPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(mappingPanelLayout.createParallelGroup(GroupLayout.LEADING)
                    .add(mappingScrollPane, GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
                    .add(mappingPanelLayout.createSequentialGroup()
                        .add(mappingLabel)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(mappingTextField, GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)))
                .addContainerGap())
        );
        mappingPanelLayout.setVerticalGroup(
            mappingPanelLayout.createParallelGroup(GroupLayout.LEADING)
            .add(mappingPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(mappingPanelLayout.createParallelGroup(GroupLayout.BASELINE)
                    .add(mappingLabel)
                    .add(mappingTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.RELATED)
                .add(mappingScrollPane, GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)
                .addContainerGap())
        );

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.LEADING)
            .add(hSplitPane, GroupLayout.DEFAULT_SIZE, 656, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.LEADING)
            .add(hSplitPane, GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)
        );        
    } // END NetBeans LAYOUT
    
    
	public Map<Object, Object> getData() {
		Map<Object, Object> m = new HashMap<Object, Object>();
		m.put( "Variable", mappingTextField.getText() );
		m.put( "Mapping", mappingTextPane.getText() );
		
		return m;
	}
	
	
	public void setData( Map<Object, Object> data ) {
		if ( data != null ) {
			mappingTextField.setText( (String)data.get( "Variable" ) );
			mappingTextPane.setText( (String)data.get( "Mapping" ) );
		}
	}    
    
    
    // Variables declaration
    protected JScrollPane treeScrollPane;
    protected JSplitPane hSplitPane;
    protected JTextPane mappingTextPane;
    private HighlightedDocument mappingDoc;
    protected HelpTree helpTree;
    protected JLabel mappingLabel;
    protected JPanel mappingPanel;
    protected JTextField mappingTextField;
    protected JScrollPane mappingScrollPane;
    // End of variables declaration
    
}
