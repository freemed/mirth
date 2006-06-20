package com.webreach.mirth.client.ui.transformeditor;


import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import com.Ostermiller.Syntax.*;


// do we really need to extend StepPanel?  maybe we don't
// need a StepPanel at all (see notes in StepPanel), and
// we could just as well extend JPanel...
public class MapperPanel extends StepPanel {	
	
	public MapperPanel() {
		super();
		
		setBorder( BorderFactory.createTitledBorder( "Mapper Step" ) );
		variableNameLabel = new JLabel( "Variable: " );
		variableNameTextField = new JTextField( 20 );
		variableNameTextField.setBorder( 
				BorderFactory.createBevelBorder( BevelBorder.LOWERED ) );
		mappingDoc = new HighlightedDocument();
		mappingDoc.setHighlightStyle( HighlightedDocument.JAVASCRIPT_STYLE );
		mappingTextPane = new JTextPane( mappingDoc );
		mappingTextPane.setBorder( 
				BorderFactory.createBevelBorder( BevelBorder.LOWERED ) );
		mappingPane = new JPanel();
		mappingLabelPane = new JPanel();
		mappingPane.setLayout( new BorderLayout() );
		mappingLabelPane.setLayout( new BorderLayout() );
		mappingLabelPane.add( variableNameLabel, BorderLayout.WEST );
		mappingLabelPane.add( variableNameTextField, BorderLayout.CENTER );
		mappingPane.add( mappingLabelPane, BorderLayout.NORTH );
		mappingPane.add( mappingTextPane, BorderLayout.CENTER );
		mappingScrollPane = new JScrollPane( mappingPane );
		mappingScrollPane.setAutoscrolls( true );
		mappingScrollPane.setBorder( BorderFactory.createEmptyBorder() );
		mappingScrollPane.setSize( new Dimension( 620, 460 ) );
		
		helpTree = new JTree();
		helpTree.setBorder( BorderFactory.createTitledBorder( "Help" ) );
		
		hSplitPane = new JSplitPane(
				JSplitPane.HORIZONTAL_SPLIT, mappingScrollPane, helpTree );
		hSplitPane.setContinuousLayout( true );
		hSplitPane.setOneTouchExpandable( true );
		hSplitPane.setDividerSize( 14 );
		hSplitPane.setDividerLocation( 500 );
		
		this.setLayout( new BorderLayout() );
		this.add( hSplitPane, BorderLayout.CENTER );
		
	}

	
	public MapperData getData() {
		MapperData mapperData = new MapperData();
		mapperData.setVariableName( variableNameTextField.getText() );
		mapperData.setVariableMapping( mappingTextPane.getText() );
		return mapperData;
	}
	
	
	public void setData( MapperData mapperData ) {
		if ( mapperData != null ) {
			variableNameTextField.setText( mapperData.getVariableName() );
			mappingTextPane.setText( mapperData.getVariableMapping() );
			
		}
		
	}
	
	private JSplitPane hSplitPane;
	private JLabel variableNameLabel;
	private JTextField variableNameTextField;
	private JPanel mappingPane;
	private JPanel mappingLabelPane;
	private JScrollPane mappingScrollPane;
	private JTextPane mappingTextPane;
	private HighlightedDocument mappingDoc;
	private JTree helpTree;
		
}
