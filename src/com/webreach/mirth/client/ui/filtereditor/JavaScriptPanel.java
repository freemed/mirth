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

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.text.BadLocationException;

import com.Ostermiller.Syntax.HighlightedDocument;

/**
 * @author chrisl
 *
 */
public class JavaScriptPanel extends StepPanel {

	public JavaScriptPanel() {
		super();
		
		mappingPane = new JPanel();
		mappingDoc = new HighlightedDocument();
		mappingDoc.setHighlightStyle( HighlightedDocument.JAVASCRIPT_STYLE );
		mappingTextPane = new JTextPane( mappingDoc );
		mappingTextPane.setBorder( BorderFactory.createEtchedBorder() );
		mappingPane.setBorder( BorderFactory.createTitledBorder("JavaScript" ) );
		mappingPane.setLayout( new BorderLayout() );
		mappingPane.add( mappingTextPane, BorderLayout.CENTER );
		
		this.setLayout( new BorderLayout() );
		this.add( mappingPane, BorderLayout.CENTER );
		
	}
	
	
	public Map<Object, Object> getData() {
		Map<Object, Object> m = new HashMap<Object, Object>();
		m.put( "Script", mappingTextPane.getText() );
		return m;
	}
	
	
	public void setData( Map<Object, Object> m ) {
		mappingTextPane.setText( (String)m.get( "Script" ) );	
	}

	
	private JPanel mappingPane;
	private HighlightedDocument mappingDoc;
	private JTextPane mappingTextPane;
	
}
