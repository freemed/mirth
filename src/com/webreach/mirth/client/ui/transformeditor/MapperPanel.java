/** MapperPanel.java
 *
 * 	@author  franciscos
 * 	Created on June 21, 2006, 4:38 PM
 */


package com.webreach.mirth.client.ui.transformeditor;

import java.awt.Dimension;

import javax.swing.*;
import org.jdesktop.layout.*;
import com.Ostermiller.Syntax.*;
import com.webreach.mirth.client.ui.Constants;


public class MapperPanel extends StepPanel {
    
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
        helpTree = new JTree();
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
        mappingScrollPane.setBorder( BorderFactory.createEmptyBorder() );
        mappingTextPane.setBorder( BorderFactory.createEtchedBorder() );
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
    
    
	public Object getData() {
		MapperData mapperData = new MapperData();
		mapperData.setVariableName( mappingTextField.getText() );
		mapperData.setVariableMapping( mappingTextPane.getText() );
		return mapperData;
	}
	
	
	public void setData( MapperData data ) {
		if ( data != null ) {
			mappingTextField.setText( data.getVariableName() );
			mappingTextPane.setText( data.getVariableMapping() );
		}
	}    
    
    
    // Variables declaration
    protected JScrollPane treeScrollPane;
    protected JSplitPane hSplitPane;
    protected JTextPane mappingTextPane;
    private HighlightedDocument mappingDoc;
    protected JTree helpTree;
    protected JLabel mappingLabel;
    protected JPanel mappingPanel;
    protected JTextField mappingTextField;
    protected JScrollPane mappingScrollPane;
    // End of variables declaration
    
}
