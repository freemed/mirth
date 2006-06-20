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
package com.webreach.mirth.client.ui.transformeditor;

import java.util.Vector;
import javax.swing.ImageIcon;


/**
 * @author chrisl, franciscos
 * 
 * Vector is extended because this is the table model used by JTables, 
 * and allows for easy abstraction of a 'data' in conjunction with a 
 * table view.  This allows for steps to be easily manipulated within
 * the table model, and can then be easily converted into an ArrayList
 * when necessary.
 * 
 * Ultimately, it will be better to modify the AbstractTableModel to work
 * with a TransformStep object, and I'll do that when I get a chance to
 * clean up the code ;)  --fjs--
 */
public class TransformStep extends Vector<Object> {
	
	/** the constructor takes an int to indicate where
	 *  within the sequence of steps this one belongs
	 **/
	public TransformStep( int n ) {
		super( NUMBER_OF_COLUMNS );
		
		name = "";
		number = n;
		type = MAPPER_TYPE;	// by default, per Gerald
		icon = null;
		javaScript = null;
		data = new MapperData();
		
		insertElementAt( new Integer( number ), STEP_NUMBER_COL );
		insertElementAt( "[ " + type + " step ]", STEP_NAME_COL );
		insertElementAt( type, STEP_TYPE_COL );
		
	}
	
	public int getNumber() {
		return ( (Integer)get(STEP_NUMBER_COL) ).intValue();
	}
	
	public String getName() {
		return (String)get(STEP_NAME_COL);
	}
	
	public String getType() {
		return (String)get(STEP_TYPE_COL);
	}
	
	public ImageIcon getTypeIcon() {
		return icon;
	}
		
	public String getJavaScript() {
		return null;
	}
	
	public void setNumber( int number ) {
		set( STEP_NUMBER_COL, new Integer(number) );
	}
	
	public void setName( String name ) {
		set( STEP_NAME_COL, name );
	}
	
	public void setType( String type ) {
		set( STEP_TYPE_COL, type );
	}
	
	public Object getData() {
		return data;
	}
	
	public void setData( Object data ) {
		this.data = data;
	}
	
	// TransformStep fields
	//		these are a portion of the vector
	protected Integer number;
	protected String name;
	protected String type;
	//		these are internal fields for the data
	protected ImageIcon icon;
	protected String javaScript;
	protected Object data;
	
	
	// some constants
    protected static final int STEP_NUMBER_COL  = 0;
    protected static final int STEP_NAME_COL  = 1;
    protected static final int STEP_TYPE_COL  = 2;  
	protected static final int NUMBER_OF_COLUMNS = 3;
	
	protected static final String BLANK_TYPE = "";
	protected static final String MAPPER_TYPE = "Mapper";
	protected static final String JAVASCRIPT_TYPE = "JavaScript";
	protected static final String SMTP_TYPE = "SMTP";
	protected static final String JDBC_TYPE = "JDBC";
	protected static final String ALERT_TYPE = "Alerts";
	
}
