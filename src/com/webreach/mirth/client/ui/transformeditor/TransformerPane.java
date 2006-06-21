/*
 * transformPanel.java
 *
 * Created on May 26, 2006, 5:08 PM
 */

package com.webreach.mirth.client.ui.transformeditor;

import java.awt.event.*;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;
import javax.swing.*;

import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXTaskPane;
import org.jdesktop.swingx.JXTaskPaneContainer;
import org.jdesktop.swingx.action.ActionFactory;
import org.jdesktop.swingx.action.BoundAction;
import org.jdesktop.swingx.decorator.AlternateRowHighlighter;
import org.jdesktop.swingx.decorator.HighlighterPipeline;
import com.webreach.mirth.client.ui.Frame;
import com.webreach.mirth.model.Transformer;
import com.webreach.mirth.client.ui.Constants;


/**
 *
 * @author  franciscos
 */
public class TransformerPane extends JPanel {
	
    /** Creates new form transformPanel */
    public TransformerPane( Frame p, Transformer t ) {
        setSize( 600, 800 );
        parent = p;
        initComponents();
        
        System.out.println("Parent: " + parent );
        steps = t.getSteps();
        
    }
   
    
    /** 
     * This method is called from within the constructor to
     * initialize the form.ww
     */
    private void initComponents() {
    	// instantiate the components
        transformerTableScrollPane = new JScrollPane();
        transformerTableScrollPane.setAutoscrolls( true );
        transformerTableModel = new DefaultTableModel();
        transformerTable = new JXTable( transformerTableModel );
        HighlighterPipeline highlighter = new HighlighterPipeline();
        highlighter.addHighlighter( AlternateRowHighlighter.beige );
        transformerTable.setHighlighters( highlighter );
        transformerTable.setGridColor( Constants.GRID_COLOR );
        transformerTable.setRowHeight( Constants.ROW_HEIGHT );

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
        //transformerTableModel.addColumn("Step Object", new Step[]{});
        transformerTableModel.addColumn( " # ", new Integer[]{} );
        transformerTableModel.addColumn( "Step Name", new String[]{} );
        transformerTableModel.addColumn( "Step Type", new String[]{} );
        
        // populate the list with any exisitng steps from the
        // Transformer object.
        if ( steps != null ) {
        	ListIterator li = steps.listIterator();
	        int i = 0;
	        while ( li.hasNext() )
	        	transformerTableModel.insertRow( i++, (Vector)li.next() );

        }
	       
        // this listener will save the changes to the panal data when
        // a new row is selected
        transformerTable.getSelectionModel().addListSelectionListener( 
        		new ListSelectionListener() {
        			public void valueChanged( ListSelectionEvent e ) {
        				
        				if ( !updating && !e.getValueIsAdjusting() 
        						&& transformerTable.getRowCount() > 1 ) {
        					
        					int currSelectedRow = transformerTable.getSelectedRow();
        					
        					Step currStep = (Step)
									transformerTableModel.getDataVector().elementAt( currSelectedRow );
        					String currType = currStep.getType();
        					
        					if ( lastSelectedRow != -1 &&
        							lastSelectedRow != transformerTable.getRowCount() ) {
        						
        						Step lastStep = (Step)
                						transformerTableModel.getDataVector().elementAt( lastSelectedRow );
                				
        						String lastType = lastStep.getType();
                				
                				if ( lastType == MAPPER_TYPE )
                					lastStep.setData( mapperPanel.getData() );
                				else if ( lastType == JAVASCRIPT_TYPE )
                					lastStep.setData( jsPanel.getData() );
                				else if ( lastType == SMTP_TYPE )
                					lastStep.setData( smtpPanel.getData() );
                				else if ( lastType == JDBC_TYPE )
                					lastStep.setData( jdbcPanel.getData() );
                				else if ( lastType == ALERT_TYPE )
                					lastStep.setData( alertPanel.getData() );
        						        							        					
        					}
        					
        					if ( currType == MAPPER_TYPE ) 
								mapperPanel.setData( (MapperData)currStep.getData() );
        					else if ( currType == JAVASCRIPT_TYPE )
        						jsPanel.setData( (ScriptData)currStep.getData() );
        					else if ( currType == SMTP_TYPE )
        						smtpPanel.setData( (SMTPData)currStep.getData() );
        					else if ( currType == JDBC_TYPE )
        						jdbcPanel.setData( (JDBCData)currStep.getData() );
        					else if ( currType == ALERT_TYPE )
        						alertPanel.setData( (AlertData)currStep.getData() );
        					
									
        					stepPanel.showCard( currType );
	        				
        					lastSelectedRow = currSelectedRow;
        					
        				}
        				
        				updateStepNumbers();
        			}
        			
        		});
        
        // establish the cards to use in the Transformer
        //stepPanel.addCard(blankPanel, blankPanel.getType());
        stepPanel.addCard( mapperPanel, MAPPER_TYPE );
        stepPanel.addCard( jsPanel, JAVASCRIPT_TYPE );
        stepPanel.addCard( smtpPanel, SMTP_TYPE );
        stepPanel.addCard( jdbcPanel, JDBC_TYPE );
        stepPanel.addCard( alertPanel, ALERT_TYPE );
        
        // the options for the comboBox in the table
        String[] comboBoxValues = new String[] { MAPPER_TYPE, 
        		JAVASCRIPT_TYPE, SMTP_TYPE,
        		JDBC_TYPE, ALERT_TYPE };
                
        // Set the combobox editor on the data type column, 
        // and add action listener
	    TableColumn col = transformerTable.getColumnModel().getColumn( 
	    		STEP_TYPE_COL );
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
	    
	    // format the data number column
	    col = transformerTable.getColumnModel().getColumn( 
	    		STEP_NUMBER_COL );
	    col.setMaxWidth( 30 );
	    col.setResizable( false );
	    	    
	    transformerTableScrollPane.setViewportView( transformerTable );
        
        // make some task buttons!
	    JXTaskPane transformerTasks = new JXTaskPane();
	    JXTaskPaneContainer transformerTaskPaneContainer = new JXTaskPaneContainer();
	    transformerTasks.setTitle("Transformer Tasks");
	    transformerTasks.setFocusable(false);
	    
	    // move step up task
	    transformerTasks.add( initActionCallback( "moveStepUp",
	    		ActionFactory.createBoundAction( "moveStepUp", "Move Step Up", "U" ),
	    		new ImageIcon( Frame.class.getResource( "images/arrow_up.png" )) ));
	    
	    // move step down task
        transformerTasks.add( initActionCallback( "moveStepDown",
        		ActionFactory.createBoundAction( "moveStepDown", "Move Step Down", "D" ),
        		new ImageIcon( Frame.class.getResource( "images/arrow_down.png" )) ));
        
        // add new step task
        transformerTasks.add( initActionCallback( "addNewStep",
        		ActionFactory.createBoundAction( "addNewStep", "Add New Step", "N" ),
        		new ImageIcon( Frame.class.getResource( "images/add.png" )) ));
        
        // delete step task
        transformerTasks.add( initActionCallback( "deleteStep",
        		ActionFactory.createBoundAction( "deleteStep", "Delete Step", "X" ),
        		new ImageIcon( Frame.class.getResource( "images/delete.png" )) ));
        
        // accept task
        transformerTasks.add( initActionCallback( "accept",
        		ActionFactory.createBoundAction( "accept", "Accept", "A" ),
        		new ImageIcon( Frame.class.getResource( "images/accept.png" )) ));
        
        parent.setVisibleTasks( transformerTasks, 0, true );
        parent.setCurrentTaskPaneContainer( transformerTaskPaneContainer );
        
        
        // BGN LAYOUT - a la NetBeans //
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.LEADING)
            .add(GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(GroupLayout.TRAILING)
                    .add(GroupLayout.LEADING, stepPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(transformerTableScrollPane, GroupLayout.DEFAULT_SIZE, 648, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.RELATED))
                    )
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(transformerTableScrollPane, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.RELATED)
                .add(stepPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        // END LAYOUT //
    } // END initComponents()
    
    
    
    public BoundAction initActionCallback(String callbackMethod,BoundAction boundAction, ImageIcon icon)
    {
        if(icon != null)
            boundAction.putValue(Action.SMALL_ICON, icon);
        boundAction.registerCallback(this,callbackMethod);
        return boundAction;
    }
    
    
    /** void moveStepUp (MouseEvent evt)
     *  move the selected group of rows up by one row
     */
    private void moveStepUp( MouseEvent evt ) {
    	
    	updating = true;
    	
    	// need to grab the current row index,
    	// if the row is > 0, switch with row-1
    	int firstSelectedRow = transformerTable.getSelectedRow();
    	int moveTo = firstSelectedRow - 1;
    	int selectedRowCount = transformerTable.getSelectedRowCount();
    	int lastSelectedRow;
    	if ( selectedRowCount > 1 )
    		lastSelectedRow = selectedRowCount + firstSelectedRow - 1;
    	else lastSelectedRow = firstSelectedRow;
    	
    	// can't move above the first row
    	if ( firstSelectedRow > 0 && moveTo >= 0 ) {
    		transformerTableModel.moveRow(
    				firstSelectedRow, lastSelectedRow, moveTo );
    	   	transformerTable.setRowSelectionInterval(
    			moveTo, moveTo + selectedRowCount - 1 );
    	}
    	
    	updating = false;
    	
    }
    
    
    
    /** void moveStepDown (MouseEvent evt)
     *  move the selected group of rows down by one row
     */
    private void moveStepDown( MouseEvent evt ) {
    	
    	updating = true;
    	
    	int firstSelectedRow = transformerTable.getSelectedRow();
    	int moveTo = firstSelectedRow + 1;
    	int selectedRowCount = transformerTable.getSelectedRowCount();
    	int maxRow = transformerTable.getRowCount() - 1;
    	int lastSelectedRow;
    	if ( selectedRowCount > 1 )
    		lastSelectedRow = selectedRowCount + firstSelectedRow - 1; 
    	else lastSelectedRow = firstSelectedRow;
    	
    	// we can't move past the last row
    	if ( lastSelectedRow < maxRow  && moveTo <= maxRow ) {
    			transformerTableModel.moveRow(
    					firstSelectedRow, lastSelectedRow, moveTo );
    	    	transformerTable.setRowSelectionInterval(
    	    			moveTo, moveTo + selectedRowCount - 1 );
    	}
    	
    	updating = false;
    	
    }    
    
    
    
    /** void addNewStep(MouseEvent evt)
     *  add a new row after the current row
     */
    private void addNewStep( MouseEvent evt ) {
    	
    	int newRow = transformerTable.getSelectedRow() 
    		+ transformerTable.getSelectedRowCount();
    	
    	// if there are no rows
    	if ( newRow == -1 || newRow >= transformerTable.getRowCount() ) newRow = 0;
    	
    	Step step = new Step( newRow );
    	
    	// we need to actually place these objects in the row
    	transformerTableModel.insertRow( newRow, step );
    	transformerTable.setRowSelectionInterval( newRow, newRow );
    	
    }
    
    
    
    /** void deleteStep(MouseEvent evt)
     *  delete all selected rows
     */
    private void deleteStep( MouseEvent evt ) {
    	
    	updating = true;
    	
    	int firstSelectedRow = transformerTable.getSelectedRow();
    	int selectedRowCount = transformerTable.getSelectedRowCount();

    	// if at least one row is selected
    	if ( selectedRowCount > 0 )
    		for ( int i = 0;  i < selectedRowCount;  i++ )
    			transformerTableModel.removeRow( firstSelectedRow );
    	
    	// let's fix the slection highlight after we remove all the rows
    	int maxRowIndex = transformerTable.getRowCount() - 1;
    	
    	if ( maxRowIndex < 0 ) 
    		;// no more rows; nothing to select
    	else if ( maxRowIndex >= firstSelectedRow )
    		transformerTable.setRowSelectionInterval( firstSelectedRow, firstSelectedRow );
    	else 
    		transformerTable.setRowSelectionInterval( maxRowIndex, maxRowIndex );
    	
    	updating = false;
    	
    }
    
    
    
    /** void accept(MouseEvent evt)
     *  returns a vector of vectors to the caller of this.
     */
    private void accept( MouseEvent evt ) {
    	System.out.println(transformerTableModel.getDataVector()); 
    	
    	   	
    }
    
    
    
    /** void updateStepNumbers()
     *  traverses the table and updates all data numbers, both in the model
     *  and the view, after any change to the table
     */
    void updateStepNumbers() {    	
    	for ( int i = 0;  i < transformerTable.getRowCount();  i++ )
    		((Step)transformerTableModel.getDataVector().elementAt(i)).setNumber(i + 1);
    }
    
    
    
    
    // Variables declaration
    private JXTable transformerTable;
    private DefaultTableModel transformerTableModel;
    private JScrollPane transformerTableScrollPane;
    
    // the passed arguments
    private Frame parent;
    private List steps;
    
    // this little sucker is used to track the last row that had
    // focus after a new row is selected
    private int lastSelectedRow = -1;	// no row by default
    private boolean updating = false;
     
    // panels using CardLayout
    protected StepPanel stepPanel;			// the card holder
    protected BlankPanel blankPanel;		// the cards \/
    protected MapperPanel mapperPanel; 		//           \/
    protected JavaScriptPanel jsPanel;      //           \/
    protected SMTPPanel smtpPanel;      	//           \/
    protected JDBCPanel jdbcPanel;			//           \/
    protected AlertPanel alertPanel;		//           \/
    // End of variables declaration
    
    // for TranformSteps
    public static final int STEP_NUMBER_COL  = 0;
    public static final int STEP_NAME_COL  = 1;
    public static final int STEP_TYPE_COL  = 2;  
    public static final int NUMBER_OF_COLUMNS = 3;
    public static final String BLANK_TYPE = "";
    public static final String MAPPER_TYPE = "Mapper";
    public static final String JAVASCRIPT_TYPE = "JavaScript";
    public static final String SMTP_TYPE = "SMTP";
    public static final String JDBC_TYPE = "JDBC";
    public static final String ALERT_TYPE = "Alerts";
    
}
