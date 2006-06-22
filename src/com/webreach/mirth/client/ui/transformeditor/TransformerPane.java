/** TransformerPane.java
 *
 *  @author franciscos 
 *  Created on May 26, 2006, 5:08 PM
 */


package com.webreach.mirth.client.ui.transformeditor;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;
import javax.swing.*;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXTaskPane;
import org.jdesktop.swingx.JXTaskPaneContainer;
import org.jdesktop.swingx.action.ActionFactory;
import org.jdesktop.swingx.action.BoundAction;
import org.jdesktop.swingx.decorator.AlternateRowHighlighter;
import org.jdesktop.swingx.decorator.HighlighterPipeline;
import com.webreach.mirth.client.ui.Frame;
import com.webreach.mirth.model.Transformer;
import com.webreach.mirth.model.Step;
import com.webreach.mirth.client.ui.Constants;



public class TransformerPane extends JPanel {
	
	
    /** constructor
     * 
     *  Frame - the parent where this panel & its tasks will be loaded
     *  Transformer - the data model
     */
    public TransformerPane( Frame p, Transformer t ) {
        parent = p;
        transformer = t;
        initComponents();
    }
   
    
    /** This method is called from within the constructor to
     *  initialize the form.
     */
    private void initComponents() {
    	
    	// the table
        transformerTableScrollPane = new JScrollPane();
        transformerTableScrollPane.setAutoscrolls( true );
        transformerTableModel = new DefaultTableModel();
        transformerTable = new JXTable( transformerTableModel );
        HighlighterPipeline highlighter = new HighlighterPipeline();
        highlighter.addHighlighter( AlternateRowHighlighter.beige );
        transformerTable.setHighlighters( highlighter );
        transformerTable.setGridColor( Constants.GRID_COLOR );
        transformerTable.setRowHeight( Constants.ROW_HEIGHT );
        // add some columns to the table
        transformerTableModel.addColumn( " # ", new Integer[]{} );
        transformerTableModel.addColumn( "Step Name", new String[]{} );
        transformerTableModel.addColumn( "Step Type", new String[]{} );
        transformerTableModel.addColumn( "Step Data", new Object[]{} );		// this col will be hidden
        TableColumnModel colModel = transformerTable.getColumnModel();
        colModel.removeColumn( colModel.getColumn( STEP_DATA_COL ) );		// hide the column

        // the options for the comboBox in the table
        String[] comboBoxValues = new String[] { 
        		MAPPER_TYPE, JAVASCRIPT_TYPE, SMTP_TYPE, JDBC_TYPE, ALERT_TYPE };
        
        // Set the combobox editor on the data type column, 
        // and add action listener
	    TableColumn col = transformerTable.getColumnModel().getColumn( STEP_TYPE_COL );
	    MyComboBoxEditor comboBox = new MyComboBoxEditor( comboBoxValues );
	    col.setCellEditor( comboBox );
	    col.setMinWidth( 90 );
	    col.setMaxWidth( 200 );
	    
	    // format the data number column
	    col = transformerTable.getColumnModel().getColumn( STEP_NUMBER_COL );
	    col.setMaxWidth( 30 );
	    col.setResizable( false );
	    	    
	    transformerTableScrollPane.setViewportView( transformerTable );
        
	    // populate the list with any exisitng steps from the
        // Transformer object.
	    System.out.println(transformer);
        List steps = transformer.getSteps();
        System.out.println(steps);
        if ( steps != null ) {
        	ListIterator li = steps.listIterator();
	        int i = 0;
	        while ( li.hasNext() ) {
	        	Step step = (Step)li.next();
	        	Vector<Object> v = stepToVector( step );
	        	transformerTableModel.insertRow( i, v );	        
	        	transformerTable.setRowSelectionInterval( i, i );
	        	i++;
	        }
        }
	    
	    ((JComboBox)comboBox.getComponent()).addItemListener( new ItemListener() {
            public void itemStateChanged( ItemEvent evt ) {            	
            	String type = evt.getItem().toString();
            	// put some logic here to detect if the current panel
            	// has data, and if so, tell the user that changing
            	// the type will lose the data            	
            	stepPanel.showCard( type );
            }
        });  
	    
        // this listener will save the changes to the panal data when
        // a new row is selected
        transformerTable.getSelectionModel().addListSelectionListener( 
        		new ListSelectionListener() {
        			public void valueChanged( ListSelectionEvent e ) {
        				
        				if ( !updating && !e.getValueIsAdjusting() 
        						&& transformerTable.getRowCount() > 1 ) {
        					
        					// save the previous panel data if there was a row
        					// selected previously
        					if ( prevSelectedRow != -1 && 
        							prevSelectedRow != transformerTable.getRowCount() ) {
        						
        						Vector<Object> prevRow = (Vector<Object>)
                						transformerTableModel.getDataVector().elementAt( prevSelectedRow );
                				Step prevStep = vectorToStep( prevRow );
                				prevStep.setData( stepPanel.getData() );
                				prevRow = stepToVector( prevStep );
                				//transformerTableModel.removeRow( prevSelectedRow );
                				//transformerTableModel.insertRow( prevSelectedRow, prevRow );
        					}

        					// get the current row
        					int currSelectedRow = transformerTable.getSelectedRow();
        					Vector<Object> currRow = (Vector<Object>)
									 transformerTableModel.getDataVector().elementAt( currSelectedRow );
        					Step currStep = vectorToStep( currRow );
        					String currType = currStep.getType();
        					stepPanel.setData( currStep.getData() );
        					stepPanel.showCard( currType );
	        				
        					prevSelectedRow = currSelectedRow;
        				}
        				
        				updateStepNumbers();
        			}
        		});
        
        // the available panels (cards)
        stepPanel = new StepPanel();
        mapperPanel = new MapperPanel();
        jsPanel = new JavaScriptPanel();
        smtpPanel = new SMTPPanel();
        jdbcPanel = new JDBCPanel();
        alertPanel = new AlertPanel();
        // 		establish the cards to use in the Transformer
        // 		stepPanel.addCard(blankPanel, blankPanel.getType());
        stepPanel.addCard( mapperPanel, MAPPER_TYPE );
        stepPanel.addCard( jsPanel, JAVASCRIPT_TYPE );
        stepPanel.addCard( smtpPanel, SMTP_TYPE );
        stepPanel.addCard( jdbcPanel, JDBC_TYPE );
        stepPanel.addCard( alertPanel, ALERT_TYPE );

	    // make some task buttons!
	    JXTaskPaneContainer transformerTaskPaneContainer = new JXTaskPaneContainer();
	    
	    JXTaskPane viewPane = new JXTaskPane();
        viewPane.setTitle("Mirth Views");
        viewPane.setFocusable(false);
        viewPane.add(initActionCallback( "accept",
        		ActionFactory.createBoundAction( "accept", "Back to Channels", "B" ), 
        		new ImageIcon( Frame.class.getResource( "images/resultset_previous.png" )) ));
        parent.setNonFocusable(viewPane);
        transformerTaskPaneContainer.add(viewPane);
	    
	    JXTaskPane transformerTasks = new JXTaskPane();
	    transformerTasks.setTitle("Transformer Tasks");
	    transformerTasks.setFocusable(false);
	    
        
        // add new step task
        transformerTasks.add( initActionCallback( "addNewStep",
        		ActionFactory.createBoundAction( "addNewStep", "Add New Step", "N" ),
        		new ImageIcon( Frame.class.getResource( "images/add.png" )) ));
        
        // delete step task
        transformerTasks.add( initActionCallback( "deleteStep",
        		ActionFactory.createBoundAction( "deleteStep", "Delete Step", "X" ),
        		new ImageIcon( Frame.class.getResource( "images/delete.png" )) ));
        
	    // move step up task
	    transformerTasks.add( initActionCallback( "moveStepUp",
	    		ActionFactory.createBoundAction( "moveStepUp", "Move Step Up", "U" ),
	    		new ImageIcon( Frame.class.getResource( "images/arrow_up.png" )) ));
	    
	    // move step down task
        transformerTasks.add( initActionCallback( "moveStepDown",
        		ActionFactory.createBoundAction( "moveStepDown", "Move Step Down", "D" ),
        		new ImageIcon( Frame.class.getResource( "images/arrow_down.png" )) ));
        
        // add the tasks to the taskpane, and the taskpane to the mirth client
        transformerTaskPaneContainer.add( transformerTasks );
        parent.setNonFocusable( transformerTasks );
        parent.setVisibleTasks( transformerTasks, 0, true );
        parent.setCurrentTaskPaneContainer( transformerTaskPaneContainer );
        parent.setCurrentContentPage( this );
        
        // LAYOUT
        transformerTableScrollPane.setBorder( BorderFactory.createEmptyBorder() );
        stepPanel.setBorder( BorderFactory.createEmptyBorder() );
        vSplitPane = new JSplitPane( JSplitPane.VERTICAL_SPLIT,
        		transformerTableScrollPane, stepPanel );
        vSplitPane.setContinuousLayout( true );
        vSplitPane.setDividerLocation( 200 );
        this.setLayout( new BorderLayout() );
        this.add( vSplitPane, BorderLayout.CENTER );
   
    } /* END initComponents() */
    
    
    public BoundAction initActionCallback( 
    		String callbackMethod, BoundAction boundAction, ImageIcon icon ) {
    	
        if(icon != null)
            boundAction.putValue(Action.SMALL_ICON, icon);
        boundAction.registerCallback(this,callbackMethod);
        return boundAction;
    }
    
    
    /** void moveStepUp (MouseEvent evt)
     *  move the selected group of rows up by one row
     */
    public void moveStepUp() {
    	
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
    public void moveStepDown() {
    	
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
    public void addNewStep() {
    	
    	int newRow = transformerTable.getSelectedRow() 
    		+ transformerTable.getSelectedRowCount();
    	
    	// if there are no rows
    	if ( newRow == -1 || newRow > transformerTable.getRowCount() ) newRow = 0;
    	
    	Step step = new Step();
    	Vector<Object> v = new Vector<Object>( NUMBER_OF_COLUMNS );
    	v.insertElementAt( step.getSequenceNumber(), STEP_NUMBER_COL );
    	v.insertElementAt( step.getName(), STEP_NAME_COL );
    	v.insertElementAt( step.getType(), STEP_TYPE_COL );

    	// we need to actually place these objects in the row
    	transformerTableModel.insertRow( newRow, v );
    	transformerTable.setRowSelectionInterval( newRow, newRow );
    }
    
    /** void deleteStep(MouseEvent evt)
     *  delete all selected rows
     */
    public void deleteStep() {
    	
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
    public void accept() {
    	
    	Vector v = (Vector)transformerTableModel.getDataVector();
    	List<Step> steps = v.subList( 0, v.size() - 1 );
    	transformer.setSteps( steps );
    	
    	parent.setCurrentContentPage(parent.channelEditPage);
    	parent.setCurrentTaskPaneContainer(parent.taskPaneContainer);
    }
    
    /** void updateStepNumbers()
     *  traverses the table and updates all data numbers, both in the model
     *  and the view, after any change to the table
     */
    private void updateStepNumbers() {    	
    	for ( int i = 0;  i < transformerTable.getRowCount();  i++ )
    		((Vector<Object>)
    				transformerTableModel.getDataVector().elementAt(i)).set( STEP_NUMBER_COL, i );
    }
    
    
    /** StepToVector( Step step )
     *  converts a Step object to a Vector object
     */
    private Vector<Object> stepToVector( Step step ) {    	
    	Vector<Object> v = new Vector<Object>( NUMBER_OF_COLUMNS );
    	v.insertElementAt( step.getSequenceNumber(), STEP_NUMBER_COL );
    	v.insertElementAt( step.getName(), STEP_NAME_COL );
    	v.insertElementAt( step.getType(), STEP_TYPE_COL );
    	v.insertElementAt( step.getData(), STEP_DATA_COL );

    	return v;
    }
    
    /** VectorToStep( Vector<Object> v )
     *  converts a Vector object to a Step object
     */
    private Step vectorToStep( Vector<Object> v ) {
    	Step step = new Step();
    	step.setSequenceNumber( ((Integer)v.get( STEP_NUMBER_COL )).intValue() );
    	step.setName( (String)v.get( STEP_NAME_COL ) );
    	step.setType( (String)v.get( STEP_TYPE_COL ) );
    	step.setData( (Object)v.get( STEP_DATA_COL ) );
    	
    	return step;
    }
    
    /** dataVectorToList( Vector<Object> v )
     *  converts the table's dataVector to a List<Step>  
     */
    private List<Step> dataVectorToList( Vector<Object> v ) {
    	List<Step> list = new ArrayList<Step>();
    	for ( int i = 0;  i < v.size();  i++ )
    		list.add( vectorToStep( (Vector<Object>)v.get(i) ) );
    	
    	return list;
    }
    
    /** listToDataVector( List<Step> l )
     *  converts a List<Step> to a Vector of Vectors for
     *  a table model's dataVector
     */
    private Vector<Vector> listToDataVector( List<Step> l ){
    	Vector<Vector> v = new Vector<Vector>();
    	Iterator<Step> li = l.iterator();
    	while ( li.hasNext() )
    		v.add( stepToVector( li.next() ));
    	
    	return v;
    }
    
    
    
//............................................................................\\
    
    // the passed arguments to the constructor
    private Frame parent;
    private Transformer transformer;
    
    // fields
    private JXTable transformerTable;
    private DefaultTableModel transformerTableModel;
    private JScrollPane transformerTableScrollPane;
    private JSplitPane vSplitPane;

    // this little sucker is used to track the last row that had
    // focus after a new row is selected
    private int prevSelectedRow = -1;	// no row by default
    private boolean updating = false;
     
    // panels using CardLayout
    protected StepPanel stepPanel;			// the card holder
    protected BlankPanel blankPanel;		// the cards
    protected MapperPanel mapperPanel; 		//    \/
    protected JavaScriptPanel jsPanel;      //    \/
    protected SMTPPanel smtpPanel;      	//    \/
    protected JDBCPanel jdbcPanel;			//    \/
    protected AlertPanel alertPanel;		//    \/
    
    // transformer constants
    public static final int STEP_NUMBER_COL  = 0;
    public static final int STEP_NAME_COL  = 1;
    public static final int STEP_TYPE_COL  = 2;
    public static final int STEP_DATA_COL = 3;
    public static final int NUMBER_OF_COLUMNS = 4;
    public static final String BLANK_TYPE = "";
    public static final String MAPPER_TYPE = "Mapper";
    public static final String JAVASCRIPT_TYPE = "JavaScript";
    public static final String SMTP_TYPE = "SMTP";
    public static final String JDBC_TYPE = "JDBC";
    public static final String ALERT_TYPE = "Alerts";
    
}
