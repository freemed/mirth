/**
 * The contents of this file are subject to the Mozilla Public License Version 1.1
 * (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.mozilla.org/MPL/
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for the
 * specific language governing rights and limitations under the License.
 *
 * The Original Code is "TreePanel.java".  Description:
 * "This is a Swing panel that displays the contents of a Message object in a JTree"
 *
 * The Initial Developer of the Original Code is University Health Network. Copyright (C)
 * 2001.  All Rights Reserved.
 *
 * Contributor(s): chrisl.
 *
 * Alternatively, the contents of this file may be used under the terms of the
 * GNU General Public License (the  “GPL”), in which case the provisions of the GPL are
 * applicable instead of those above.  If you wish to allow use of your version of this
 * file only under the terms of the GPL and not to allow others to use your version
 * of this file under the MPL, indicate your decision by deleting  the provisions above
 * and replace  them with the notice and other provisions required by the GPL License.
 * If you do not delete the provisions above, a recipient may use your version of
 * this file under either the MPL or the GPL.
 *
 */
package com.webreach.mirth.client.ui.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

import org.apache.log4j.Logger;

/*
 * Parses a sql statement for column names
 */
public class SQLParserUtil {
	private String REGEX = "`[^`]*`";
	String _sqlStatement = "";	
	private Logger logger = Logger.getLogger(this.getClass());
	public SQLParserUtil(String statement){
		_sqlStatement = statement;
	}
	public SQLParserUtil(){
		
	}
	public String[] Parse(String statement){
		_sqlStatement = statement;
		return Parse();
	}
	public String[] Parse(){
		
		try{
			//Pattern pattern = Pattern.compile(REGEX);
			int fromClause = _sqlStatement.toUpperCase().indexOf(" FROM ");
			String columnText = _sqlStatement.substring(7, fromClause).trim();
			return columnText.replaceAll(" ", "").replaceAll("`","").split(",");
			
		}catch(Exception e){
			logger.error(e);
			return null;
		}
	}
    public static void main(String[] args){
    	SQLParserUtil squ = new SQLParserUtil("SELECT `pd_lname`,`pd_fname`,    `pd_tname` FROM `patients`;");
    	String[] columns = squ.Parse();
    	for(int i = 0; i < columns.length; i++){
    		System.out.println(columns[i]);
    	}
    }
}
