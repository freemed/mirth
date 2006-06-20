/*
 * transformPanel.java
 *
 * Created on May 26, 2006, 5:08 PM
 */

package com.webreach.mirth.client.ui.transformeditor;

import java.awt.*;
import java.awt.event.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;
import javax.swing.*;
import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;


/**
 *
 * @author  franciscos
 */
public class TransformPane extends JPanel {

	
    /** Creates new form transformPanel */
    public TransformPane() {
        initComponents();
        setSize( 600, 800 );
    }
   
    
    /** 
     * This method is called from within the constructor to
     * initialize the form.ww
     */
    private void initComponents() {
    	// instantiate the components
        transformTableScrollPane = new JScrollPane();
        transformTableScrollPane.setAutoscrolls( true );
        transformTableModel = new DefaultTableModel();
        transformTable = new JTable( transformTableModel );
        transformTable.setGridColor( new Color(224,224,224) );

        // the available panels
        stepPanel = new StepPanel();
        mapperPanel = new MapperPanel();
        jsPanel = new JavaScriptPanel();
        smtpPanel = new SMTPPanel();
        jdbcPanel = new JDBCPanel();
        alertPanel = new AlertPanel();

        // add some columns to the table
        // the object column will be hidden.  it maintains the relationship
        // between a row and the TransfomerStep it represents
        //transformTableModel.addColumn("Step Object", new TransformStep[]{});
        transformTableModel.addColumn( " # ", new Integer[]{} );
        transformTableModel.addColumn( "Step Name", new String[]{} );
        transformTableModel.addColumn( "Step Type", new String[]{} );

        // this listener will save the changes to the panal data when
        // a new row is selected
        transformTable.getSelectionModel().addListSelectionListener( 
        		new ListSelectionListener() {
        			public void valueChanged( ListSelectionEvent e ) {
        				
        				if ( !updating && !e.getValueIsAdjusting() 
        						&& transformTable.getRowCount() > 1 ) {
        					
        					int currSelectedRow = transformTable.getSelectedRow();
        					
        					TransformStep currStep = (TransformStep)
									transformTableModel.getDataVector().elementAt( currSelectedRow );
        					String currType = currStep.getType();
        					
        					if ( lastSelectedRow != -1 &&
        							lastSelectedRow != transformTable.getRowCount() ) {
        						
        						TransformStep lastStep = (TransformStep)
                						transformTableModel.getDataVector().elementAt( lastSelectedRow );
                				
        						String lastType = lastStep.getType();
                				
                				if ( lastType == TransformStep.MAPPER_TYPE )
                					lastStep.setData( mapperPanel.getData() );
                				else if ( lastType == TransformStep.JAVASCRIPT_TYPE )
                					lastStep.setData( jsPanel.getData() );
                				else if ( lastType == TransformStep.SMTP_TYPE )
                					lastStep.setData( smtpPanel.getData() );
                				else if ( lastType == TransformStep.JDBC_TYPE )
                					lastStep.setData( jdbcPanel.getData() );
                				else if ( lastType == TransformStep.ALERT_TYPE )
                					lastStep.setData( alertPanel.getData() );
        						        							        					
        					}
        					
        					if ( currType == TransformStep.MAPPER_TYPE ) 
								mapperPanel.setData( (MapperData)currStep.getData() );
        					else if ( currType == TransformStep.JAVASCRIPT_TYPE )
        						jsPanel.setData( (ScriptData)currStep.getData() );
        					else if ( currType == TransformStep.SMTP_TYPE )
        						smtpPanel.setData( (SMTPData)currStep.getData() );
        					else if ( currType == TransformStep.JDBC_TYPE )
        						jdbcPanel.setData( (JDBCData)currStep.getData() );
        					else if ( currType == TransformStep.ALERT_TYPE )
        						alertPanel.setData( (AlertData)currStep.getData() );
        					
									
        					stepPanel.showCard( currType );
	        				
        					lastSelectedRow = currSelectedRow;
        					
        				}
        				
        				updateStepNumbers();
        			}
        			
        		});
        
        // establish the cards to use in the Transformer
        //stepPanel.addCard(blankPanel, blankPanel.getType());
        stepPanel.addCard( mapperPanel, TransformStep.MAPPER_TYPE );
        stepPanel.addCard( jsPanel, TransformStep.JAVASCRIPT_TYPE );
        stepPanel.addCard( smtpPanel, TransformStep.SMTP_TYPE );
        stepPanel.addCard( jdbcPanel, TransformStep.JDBC_TYPE );
        stepPanel.addCard( alertPanel, TransformStep.ALERT_TYPE );
        
        // the options for the comboBox in the table
        String[] comboBoxValues = new String[] { TransformStep.MAPPER_TYPE, 
        		TransformStep.JAVASCRIPT_TYPE, TransformStep.SMTP_TYPE,
        		TransformStep.JDBC_TYPE, TransformStep.ALERT_TYPE };
                
        // Set the combobox editor on the data type column, 
        // and add action listener
	    TableColumn col = transformTable.getColumnModel().getColumn( 
	    		TransformStep.STEP_TYPE_COL );
	    MyComboBoxEditor comboBox = new MyComboBoxEditor( comboBoxValues );
	    ((JComboBox)comboBox.getComponent()).addItemListener( new ItemListener() {
            public void itemStateChanged( ItemEvent evt ) {
            	
            	String type = evt.getItem().toString();

            	// put some logic here to detect if the current panel
            	// has data, and if so, tell the user that changing
            	// the type will lose the data
            	
            	stepPanel.showCard( type );
            	
            }
            
        });
	    
	    col.setCellEditor( comboBox );
	    col.setMinWidth( 90 );
	    col.setMaxWidth( 200 );
	    
	    // If the cell should appear like a combobox in its
	    // non-editing state, also set the combobox renderer
        //comboBox = new MyComboBoxEditor(comboBoxValues);
	    //col.setCellRenderer(new MyComboBoxRenderer(comboBoxValues));    
	    
	    // format the data number column
	    col = transformTable.getColumnModel().getColumn( 
	    		TransformStep.STEP_NUMBER_COL );
	    col.setMaxWidth( 30 );
	    col.setResizable( false );
	    	    
	    transformTableScrollPane.setViewportView( transformTable );
        
        // make some buttons!
        moveUpButton = new JButton(	new ImageIcon( 
        		"C:\\Documents and Settings\\franciscos\\Desktop\\icons\\arrow_up.png" ));
        moveUpButton.setToolTipText( "Move data up" );
        moveUpButton.addMouseListener( new MouseAdapter() {
        	public void mouseClicked( MouseEvent evt ) {
        		moveStepUp( evt );
        	}
        });
        
        moveDownButton = new JButton( new ImageIcon(
        		"C:\\Documents and Settings\\franciscos\\Desktop\\icons\\arrow_down.png" ));
        moveDownButton.setToolTipText( "Move data down" );
        moveDownButton.addMouseListener( new MouseAdapter() {
        	public void mouseClicked( MouseEvent evt ) {
        		moveStepDown( evt );
        	}
        });
        
        addNewStepButton = new JButton( new ImageIcon(
        		"C:\\Documents and Settings\\franciscos\\Desktop\\icons\\add.png" ));
        addNewStepButton.setToolTipText( "Add new data" );
        addNewStepButton.addMouseListener( new MouseAdapter() {
        	public void mouseClicked( MouseEvent evt ) {
        		addNewStep( evt );
        	}
        });
        
        deleteStepButton = new JButton( new ImageIcon(
        		"C:\\Documents and Settings\\franciscos\\Desktop\\icons\\delete.png" ));
        deleteStepButton.setToolTipText( "Delete data" );
        deleteStepButton.addMouseListener( new MouseAdapter() {
        	public void mouseClicked( MouseEvent evt ) {
        		deleteStep( evt );
        	}
        });
        
        acceptButton = new JButton( new ImageIcon(
        		"C:\\Documents and Settings\\franciscos\\Desktop\\icons\\accept.png" ));
        acceptButton.setToolTipText( "Accept" );
        acceptButton.addMouseListener( new MouseAdapter() {
        	public void mouseClicked( MouseEvent evt ) {
        		accept( evt );
        	}
        });

        // BGN LAYOUT //
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.LEADING)
            .add(GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(GroupLayout.TRAILING)
                    .add(GroupLayout.LEADING, stepPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(transformTableScrollPane, GroupLayout.DEFAULT_SIZE, 648, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(GroupLayout.TRAILING)
                            .add(addNewStepButton)
                         	.add(deleteStepButton)
                         	.add(moveUpButton)
                            .add(moveDownButton)
                         	.add(acceptButton)))
                	)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(addNewStepButton)
                        .add(deleteStepButton)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(moveUpButton)
                        .add(moveDownButton)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(acceptButton))
                    .add(transformTableScrollPane, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.RELATED)
                .add(stepPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        // END LAYOUT //
    } // END initComponents()
    
    
    /** void moveUpButtonClicked(MouseEvent evt)
     *  move the selected group of rows up by one row
     */
    private void moveStepUp( MouseEvent evt ) {
    	
    	updating = true;
    	
    	// need to grab the current row index,
    	// if the row is > 0, switch with row-1
    	int firstSelectedRow = transformTable.getSelectedRow();
    	int moveTo = firstSelectedRow - 1;
    	int selectedRowCount = transformTable.getSelectedRowCount();
    	int lastSelectedRow;
    	if ( selectedRowCount > 1 )
    		lastSelectedRow = selectedRowCount + firstSelectedRow - 1;
    	else lastSelectedRow = firstSelectedRow;
    	
    	// can't move above the first row
    	if ( firstSelectedRow > 0 && moveTo >= 0 ) {
    		transformTableModel.moveRow(
    				firstSelectedRow, lastSelectedRow, moveTo );
    	   	transformTable.setRowSelectionInterval(
    			moveTo, moveTo + selectedRowCount - 1 );
    	}
    	
    	updating = false;
    	
    }
    
    
    /** void moveDownButtonClicked(MouseEvent evt)
     *  move the selected group of rows down by one row
     */
    private void moveStepDown( MouseEvent evt ) {
    	
    	updating = true;
    	
    	int firstSelectedRow = transformTable.getSelectedRow();
    	int moveTo = firstSelectedRow + 1;
    	int selectedRowCount = transformTable.getSelectedRowCount();
    	int maxRow = transformTable.getRowCount() - 1;
    	int lastSelectedRow;
    	if ( selectedRowCount > 1 )
    		lastSelectedRow = selectedRowCount + firstSelectedRow - 1; 
    	else lastSelectedRow = firstSelectedRow;
    	
    	// we can't move past the last row
    	if ( lastSelectedRow < maxRow  && moveTo <= maxRow ) {
    			transformTableModel.moveRow(
    					firstSelectedRow, lastSelectedRow, moveTo );
    	    	transformTable.setRowSelectionInterval(
    	    			moveTo, moveTo + selectedRowCount - 1 );
    	}
    	
    	updating = false;
    	
    }    
    
    
    /** void addNewStepButton(MouseEvent evt)
     *  add a new row after the current row
     */
    private void addNewStep( MouseEvent evt ) {
    	
    	//updating = true;
    	
    	int newRow = transformTable.getSelectedRow() 
    		+ transformTable.getSelectedRowCount();
    	
    	// if there are no rows
    	if ( newRow == -1 || newRow >= transformTable.getRowCount() ) newRow = 0;
    	
    	TransformStep step = new TransformStep( newRow );
    	
    	// we need to actually place these objects in the row
    	transformTableModel.insertRow( newRow, step );
    	transformTable.setRowSelectionInterval( newRow, newRow );
    	
    	//updating = false;
    	
    }
    
    
    /** void deleteButton(MouseEvent evt)
     *  delete all selected rows
     */
    private void deleteStep( MouseEvent evt ) {
    	
    	updating = true;
    	
    	int firstSelectedRow = transformTable.getSelectedRow();
    	int selectedRowCount = transformTable.getSelectedRowCount();

    	// if at least one row is selected
    	if ( selectedRowCount > 0 )
    		for ( int i = 0;  i < selectedRowCount;  i++ )
    			transformTableModel.removeRow( firstSelectedRow );
    	
    	// let's fix the slection highlight after we remove all the rows
    	int maxRowIndex = transformTable.getRowCount() - 1;
    	
    	if ( maxRowIndex < 0 ) 
    		;// no more rows; nothing to select
    	else if ( maxRowIndex >= firstSelectedRow )
    		transformTable.setRowSelectionInterval( firstSelectedRow, firstSelectedRow );
    	else 
    		transformTable.setRowSelectionInterval( maxRowIndex, maxRowIndex );
    	
    	updating = false;
    	
    }
    
    
    /** void accept(MouseEvent evt)
     *  returns a vector of vectors to the caller of this.
     */
    private void accept( MouseEvent evt ) {
    	System.out.println(transformTableModel.getDataVector());    	
    	   	
    }
    
    
    /** void updateStepNumbers()
     *  traverses the table and updates all data numbers, both in the model
     *  and the view, after any change to the table
     */
    void updateStepNumbers() {    	
    	for ( int i = 0;  i < transformTable.getRowCount();  i++ )
    		((TransformStep)transformTableModel.getDataVector().elementAt(i)).setNumber(i + 1);
    }
    
    
    // Variables declaration
    private JButton moveUpButton;
    private JButton moveDownButton;
    private JButton addNewStepButton;
    private JButton deleteStepButton;
    private JButton acceptButton;
    private JTable transformTable;
    private DefaultTableModel transformTableModel;
    private JScrollPane transformTableScrollPane;
    
    // this little sucker is used to track the last row that had
    // focus after a new row is selected
    private int lastSelectedRow = -1;	// no row by default
    private boolean updating = false;
     
    // panels
    protected StepPanel stepPanel;			// the card holder
    protected BlankPanel blankPanel;		// the cards \/
    protected MapperPanel mapperPanel; 		//           \/
    protected JavaScriptPanel jsPanel;      //           \/
    protected SMTPPanel smtpPanel;      	//           \/
    protected JDBCPanel jdbcPanel;			//           \/
    protected AlertPanel alertPanel;		//           \/
    
    // End of variables declaration
    
}
