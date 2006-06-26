/** FilterPane.java
 *
 *  @author franciscos 
 *  Created on May 26, 2006, 5:08 PM
 */


package com.webreach.mirth.client.ui.editors.filter;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXTaskPane;
import org.jdesktop.swingx.JXTaskPaneContainer;
import org.jdesktop.swingx.action.ActionFactory;
import org.jdesktop.swingx.action.BoundAction;
import org.jdesktop.swingx.decorator.AlternateRowHighlighter;
import org.jdesktop.swingx.decorator.HighlighterPipeline;
import com.webreach.mirth.client.ui.Frame;
import com.webreach.mirth.model.Filter;
import com.webreach.mirth.model.Rule;
import com.webreach.mirth.model.Step;
import com.webreach.mirth.model.Transformer;
import com.webreach.mirth.client.ui.Constants;
import com.webreach.mirth.client.ui.editors.*;


public class FilterPane extends JPanel {	
	
	/** CONSTRUCTOR
	 * 
	 *  Frame - the parent where this panel & its tasks will be loaded
	 *  Filter - the data model
	 */
	public FilterPane( Frame p ) {
		parent = p;
		initComponents();
		
		modified = false;
	}
	
	/** load( Filter f )
     *  now that the components have been initialized...
     */
    public void load( Filter f ) {
    	filter = f;
    	
    	// we need to clear all the old data before we load the new
    	makeFilterTable();
    	
    	// add any existing steps to the model
        List<Rule> list = filter.getRules();
        ListIterator<Rule> li = list.listIterator();
        while ( li.hasNext() ) {
        	Rule s = li.next();
        	int row = s.getSequenceNumber();
        	setRowData( s, row );
        }
        
    	// configure the task pane so that it shows only relevant tasks
        int rowCount = filterTableModel.getRowCount();
    	if (  rowCount <= 0 )
        	parent.setVisibleTasks( filterTasks, 1, -1, false );
        else if ( rowCount == 1 )
        	filterTable.setRowSelectionInterval( 0, 0 );
        else
        	parent.setVisibleTasks( filterTasks, 0, -1, true );

        // select the first row if there is one
		if ( rowCount > 0 ) {
			filterTable.setRowSelectionInterval( 0, 0 );
			prevSelectedRow = 0;
		} else
			rulePanel.showCard( BLANK_TYPE );
    	
    	parent.setCurrentContentPage( this );
    	parent.setCurrentTaskPaneContainer( filterTaskPaneContainer );
    }
	
	/** This method is called from within the constructor to
	 *  initialize the form.
	 */
	public void initComponents() {
		
		// the available panels (cards)
		rulePanel = new CardPanel();
		blankPanel = new BlankPanel();
		jsPanel = new JavaScriptPanel();
		// 		establish the cards to use in the Filter
		rulePanel.addCard( blankPanel, BLANK_TYPE );
		rulePanel.addCard( jsPanel, JAVASCRIPT_TYPE );
		
		filterTablePane = new JScrollPane();        
		filterTablePane.addMouseListener( new MouseAdapter() {
			public void mouseClicked( MouseEvent evt ) {
				deselectRows();
			}
		});
		
		// make and place the task pane in the parent Frame
		filterTaskPaneContainer = new JXTaskPaneContainer();
		
		viewTasks = new JXTaskPane();
		viewTasks.setTitle("Mirth Views");
		viewTasks.setFocusable(false);
		viewTasks.add(initActionCallback( "accept",
				ActionFactory.createBoundAction( "accept", "Back to Channels", "B" ), 
				new ImageIcon( Frame.class.getResource( "images/resultset_previous.png" )) ));
		parent.setNonFocusable(viewTasks);
		filterTaskPaneContainer.add(viewTasks);
		
		filterTasks = new JXTaskPane();
		filterTasks.setTitle( "Filter Tasks" );
		filterTasks.setFocusable( false );
		
		// add new rule task
		filterTasks.add( initActionCallback( "addNewRule",
				ActionFactory.createBoundAction( "addNewRule", "Add New Rule", "N" ),
				new ImageIcon( Frame.class.getResource( "images/add.png" )) ));
		
		// delete rule task
		filterTasks.add( initActionCallback( "deleteRule",
				ActionFactory.createBoundAction( "deleteRule", "Delete Rule", "X" ),
				new ImageIcon( Frame.class.getResource( "images/delete.png" )) ));
		
		// move rule up task
		filterTasks.add( initActionCallback( "moveRuleUp",
				ActionFactory.createBoundAction( "moveRuleUp", "Move Rule Up", "U" ),
				new ImageIcon( Frame.class.getResource( "images/arrow_up.png" )) ));
		
		// move rule down task
		filterTasks.add( initActionCallback( "moveRuleDown",
				ActionFactory.createBoundAction( "moveRuleDown", "Move Rule Down", "D" ),
				new ImageIcon( Frame.class.getResource( "images/arrow_down.png" )) ));
		
		// add the tasks to the taskpane, and the taskpane to the mirth client
		filterTaskPaneContainer.add( filterTasks );
		parent.setNonFocusable( filterTasks );
		parent.setCurrentTaskPaneContainer( filterTaskPaneContainer );
		
		makeFilterTable();
		
		// BGN LAYOUT
		filterTablePane.setBorder( BorderFactory.createEmptyBorder() );
		rulePanel.setBorder( BorderFactory.createEmptyBorder() );
		vSplitPane = new JSplitPane( JSplitPane.VERTICAL_SPLIT,
				filterTablePane, rulePanel );
		vSplitPane.setContinuousLayout( true );
		vSplitPane.setDividerLocation( 200 );
		this.setLayout( new BorderLayout() );
		this.add( vSplitPane, BorderLayout.CENTER );
		this.setBorder( BorderFactory.createEmptyBorder() );
		// END LAYOUT
		
		parent.setCurrentContentPage( this );        
		
		// select the first row if there is one, and configure
        // the task pane so that it shows only relevant tasks
        int rowCount = filterTableModel.getRowCount();
        
        if (  rowCount <= 0 )
        	parent.setVisibleTasks( filterTasks, 1, -1, false );
        else if ( rowCount == 1 )
            parent.setVisibleTasks( filterTasks, 2, -1, false );
        else 
        	parent.setVisibleTasks( filterTasks, 0, -1, true );
        
        // select the first row if there is one
		if ( rowCount > 0 ) {
			filterTable.setRowSelectionInterval( 0, 0 );
			prevSelectedRow = 0;
		}
        	
	}  // END initComponents()
	
	// for the task pane
	public BoundAction initActionCallback( 
			String callbackMethod, BoundAction boundAction, ImageIcon icon ) {
		
		if(icon != null) boundAction.putValue(Action.SMALL_ICON, icon);
		boundAction.registerCallback(this,callbackMethod);
		return boundAction;
	}
	
	public void makeFilterTable() {
		filterTable = new JXTable();
		
		filterTable.setModel(new DefaultTableModel( 
				new String [] { "#", "Operator", "Script" }, 0 ) {
			boolean[] canEdit = new boolean [] {
					false, true, false
			};
			
			public boolean isCellEditable( int rowIndex, int columnIndex ) {
				return canEdit[columnIndex];
			}
		});
		
		filterTableModel = (DefaultTableModel)filterTable.getModel();
		
		String[] comboBoxValues = new String[] { Rule.Operator.NONE.toString(), 
				Rule.Operator.AND.toString(), Rule.Operator.OR.toString() };
		
		// Set the combobox editor on the operator column, and add action listener
		MyComboBoxEditor comboBox = new MyComboBoxEditor( comboBoxValues );
		((JComboBox)comboBox.getComponent()).addItemListener( new ItemListener() {
			public void itemStateChanged( ItemEvent evt ) {
			}
		}); 
		
		filterTable.setSelectionMode( 0 );		// only select one row at a time        
		filterTable.getColumnExt( RULE_NUMBER_COL ).setMaxWidth( 30 );
		filterTable.getColumnExt( RULE_NUMBER_COL ).setMinWidth( 30 );
		filterTable.getColumnExt( RULE_OP_COL ).setMaxWidth( 60 );
		filterTable.getColumnExt( RULE_OP_COL ).setMinWidth( 60 );
		filterTable.getColumnExt( RULE_OP_COL ).setCellEditor( comboBox );
		
		filterTable.setSortable( false );
		filterTable.setRowHeight( Constants.ROW_HEIGHT );
		filterTable.setColumnMargin( Constants.COL_MARGIN );
		filterTable.setOpaque( true );
		filterTable.setRowSelectionAllowed( true );
		HighlighterPipeline highlighter = new HighlighterPipeline();
		highlighter.addHighlighter( AlternateRowHighlighter.beige );
		filterTable.setHighlighters( highlighter );
		filterTable.setBorder( BorderFactory.createEmptyBorder() );
		filterTablePane.setBorder( BorderFactory.createEmptyBorder() );
		
		filterTablePane.setViewportView( filterTable );
		
		filterTable.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged( ListSelectionEvent evt ) {
						if ( !saving ) FilterListSelected(evt);
					}
				});
		
		filterTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked( MouseEvent evt ) {
				if (evt.getClickCount() >= 1 ) ;	// ??? //
			}
		});
	}    
	
	private void FilterListSelected( ListSelectionEvent evt ) {
		int row = filterTable.getSelectedRow();
		
		if( isValid( row ) ) { 
			saveData();
			loadData();
			rulePanel.showCard( JAVASCRIPT_TYPE );
			filterTable.setRowSelectionInterval( row, row );
		}
		
		prevSelectedRow = row;
	}
	
	private boolean isValid( int row ) {
		return ( row >= 0 && row < filterTableModel.getRowCount() );
	}
	
	public void deselectRows() {
		saveData();
		filterTable.clearSelection();
		rulePanel.showCard( BLANK_TYPE );
	}
	
	// sets the data from the previously used panel into the
	// previously selected Rule object
	private void saveData() {
		saving = true;
		
		if ( isValid( prevSelectedRow )) {
			Map<Object,Object> m = new HashMap<Object,Object>();
			m = jsPanel.getData();
			filterTableModel.setValueAt( 
					m.get("Script"), prevSelectedRow, RULE_SCRIPT_COL );
		}
		
		saving = false;
	}
	
	// loads the data object into the correct panel
    private void loadData() {
    	int row = filterTable.getSelectedRow();
    	
    	if ( isValid( row ) ) {
    		Map<Object,Object> m = new HashMap<Object,Object>();
    		m.put("Script", filterTableModel.getValueAt( row, RULE_SCRIPT_COL ));
    		jsPanel.setData( m );
    	}
    }
    
	private void setRowData( Rule rule, int row ) {
		Object[] tableData = new Object[NUMBER_OF_COLUMNS];
		
		// we have a new rule
		if ( rule == null )	{
			rule = new Rule();
			rule.setSequenceNumber( row );
			rule.setScript( "return null;" );
			if ( row == 0 )
				rule.setOperator( Rule.Operator.NONE );	// NONE operator by default on row 0
			else
				rule.setOperator( Rule.Operator.AND );	// AND operator by default elsewhere
		}
		
		tableData[RULE_NUMBER_COL] = rule.getSequenceNumber();
		tableData[RULE_OP_COL] = rule.getOperator();
		tableData[RULE_SCRIPT_COL] = rule.getScript();
		
		filterTableModel.addRow( tableData );
		filterTable.setRowSelectionInterval( row, row );
	}
	
	/** void addNewRule()
	 *  add a new rule to the end of the list
	 */
	public void addNewRule() {
		int row = filterTable.getRowCount();
		setRowData( null, row );
		prevSelectedRow = row;
	}
	
	/** void deleteRule(MouseEvent evt)
	 *  delete all selected rows
	 */
	public void deleteRule() {
		int row = filterTable.getSelectedRow();
		if ( isValid( row ) )
			filterTableModel.removeRow( row );
		
		if ( isValid( row ) )
			filterTable.setRowSelectionInterval( row, row );
		else if ( isValid( row - 1 ) )
			filterTable.setRowSelectionInterval( row - 1, row - 1 );
		else
			rulePanel.showCard( BLANK_TYPE );
		
		updateRuleNumbers();
	}
	
	public void moveRuleUp() { moveRule( -1 ); }
	public void moveRuleDown() { moveRule( 1 ); }
	
	/** void moveRule( int i )
	 *  move the selected row i places
	 */
	public void moveRule( int i ) {
		int selectedRow = filterTable.getSelectedRow();
		int moveTo = selectedRow + i;
		
		// we can't move past the first or last row
		if ( moveTo >= 0 && moveTo < filterTable.getRowCount() ) {
			filterTableModel.moveRow( selectedRow, selectedRow, moveTo );
			filterTable.setRowSelectionInterval( moveTo, moveTo );
		}
		
		updateRuleNumbers();
	}
	
	/** void accept(MouseEvent evt)
	 *  returns a vector of vectors to the caller of this.
	 */
	public void accept() {
		List<Rule> list = new ArrayList<Rule>();
		for ( int i = 0;  i < filterTable.getRowCount();  i++ ) {
			Rule rule = new Rule();
			rule.setSequenceNumber( Integer.parseInt(
					filterTable.getValueAt( i, RULE_NUMBER_COL ).toString() ));
			rule.setOperator( Rule.Operator.valueOf(
					filterTableModel.getValueAt( i, RULE_OP_COL ).toString() )); 
			rule.setScript( (String)filterTableModel.getValueAt( i, RULE_SCRIPT_COL ));
			
			list.add( rule );
		}
		
		//modified = true;
		filter.setRules( list );
		filterTableModel.setDataVector( null, new String[] {} );
		
		// reset the task pane and content to channel edit page
		parent.channelEditPage.setSourceVariableList();
		parent.channelEditPage.setDestinationVariableList();
		parent.setCurrentContentPage( parent.channelEditPage );
		parent.setCurrentTaskPaneContainer(parent.taskPaneContainer);
		//if ( modified ) parent.showSaveButton();
	}
	
	/** void updateRuleNumbers()
	 *  traverses the table and updates all data numbers, both in the model
	 *  and the view, after any change to the table
	 */
	private void updateRuleNumbers() {    
		int rowCount = filterTableModel.getRowCount();
    	
    	for ( int i = 0;  i < rowCount;  i++ )
    		filterTableModel.setValueAt( i, i, RULE_NUMBER_COL );

        if ( rowCount <= 0 )
        	parent.setVisibleTasks( filterTasks, 1, -1, false );
        else if ( rowCount == 1 ) {
        	parent.setVisibleTasks( filterTasks, 0, -1, true );
        	parent.setVisibleTasks( filterTasks, 2, -1, false );
        } else
        	parent.setVisibleTasks( filterTasks, 0, -1, true );
    }
	
	
//	............................................................................\\
	
	// the passed arguments to the constructor
	private Frame parent;
	private Filter filter;
	
	// fields
	private JXTable filterTable;
	private DefaultTableModel filterTableModel;
	private JScrollPane filterTablePane;
	private JSplitPane vSplitPane;
	private boolean saving;				// allow the selection listener to breathe
	private boolean modified;
	JXTaskPaneContainer filterTaskPaneContainer = new JXTaskPaneContainer();
	JXTaskPane viewTasks = new JXTaskPane();
	JXTaskPane filterTasks = new JXTaskPane();
	
	
	// this little sucker is used to track the last row that had
	// focus after a new row is selected
	private int prevSelectedRow = -1;	// no row by default
	
	// panels using CardLayout
	protected CardPanel rulePanel;		// the card holder
	protected BlankPanel blankPanel;	// the cards
	protected JavaScriptPanel jsPanel;  //    \/
	
	// filter constants
	public static final int RULE_NUMBER_COL  = 0;
	public static final int RULE_OP_COL  = 1;
	public static final int RULE_SCRIPT_COL  = 2;
	public static final int NUMBER_OF_COLUMNS = 3;
	public static final String BLANK_TYPE = "";
	public static final String JAVASCRIPT_TYPE = "JavaScript";
	
}
