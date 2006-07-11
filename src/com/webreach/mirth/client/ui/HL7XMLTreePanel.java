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
 * Contributor(s): ______________________________________.
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

package com.webreach.mirth.client.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.StringReader;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Composite;
import ca.uhn.hl7v2.model.Group;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.Primitive;
import ca.uhn.hl7v2.model.Segment;
import ca.uhn.hl7v2.model.Structure;
import ca.uhn.hl7v2.model.Type;
import ca.uhn.hl7v2.model.Varies;
import ca.uhn.hl7v2.parser.DefaultXMLParser;
import ca.uhn.hl7v2.parser.EncodingCharacters;
import ca.uhn.hl7v2.parser.EncodingNotSupportedException;
import ca.uhn.hl7v2.parser.PipeParser;
import ca.uhn.hl7v2.parser.XMLParser;
import ca.uhn.hl7v2.validation.impl.NoValidation;

public class HL7XMLTreePanel extends JPanel {
	private PipeParser parser;
	private XMLParser xmlParser;
	private EncodingCharacters encodingChars;
	private JTree tree;
	private Logger logger = Logger.getLogger(this.getClass());
	private String _dropPrefix = "msg";
	public HL7XMLTreePanel() {
		parser = new PipeParser();
		parser.setValidationContext(new NoValidation());
		xmlParser = new DefaultXMLParser();
		encodingChars = new EncodingCharacters('|', null);
		this.setLayout(new GridLayout(1, 1));
		this.setBackground( Color.white );
	}
	public void setDroppedTextPrefix(String prefix){
		_dropPrefix = prefix;
	}
	/**
	 * Updates the panel with a new Message.
	 */
	public void setMessage(String source) {
		Message message = null;
		Document xmlDoc = null;
		logger.debug("encoding HL7 message to XML:\n" + message);
		
		if (source != null && !source.equals("")) {
			PlatformUI.MIRTH_FRAME.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			
			try {
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				xmlDoc = docBuilder.parse(new InputSource(new StringReader(xmlParser.encode(parser.parse(source)))));
				
				message = parser.parse(source);
			} catch (EncodingNotSupportedException e) {
				PlatformUI.MIRTH_FRAME.alertWarning( "Encoding not supported.\n" +
						"Please check the syntax of your message\n" +
				"and try again.");
			} catch (HL7Exception e) {
				PlatformUI.MIRTH_FRAME.alertError( "HL7 Error!\n" +
						"Please check the syntax of your message\n" +
				"and try again.");
			} catch (Exception e) {
				PlatformUI.MIRTH_FRAME.alertException(e.getStackTrace());
				e.printStackTrace();
			}
			
			if (xmlDoc != null) {
				Element el = xmlDoc.getDocumentElement();
				DefaultMutableTreeNode top = new DefaultMutableTreeNode(el.getNodeName());
				
				NodeList children = el.getChildNodes();
				for (int i = 0; i < children.getLength(); i++) {
					processElement(children.item(i), top);
				}
				//processElement(xmlDoc.getDocumentElement(), top);
				//addChildren(message, top);
				
				tree = new JTree(top);
				tree.setDragEnabled( true );
				tree.setTransferHandler(new TreeTransferHandler());
				
				tree.addMouseMotionListener(new MouseMotionAdapter() {
					public void mouseDragged(MouseEvent evt) {
						refTableMouseDragged(evt);
					}
					public void mouseMoved(MouseEvent evt) {
						refTableMouseMoved(evt);
					}
				});
				tree.addMouseListener(new MouseAdapter() {
					public void mouseExited(MouseEvent evt) {
						refTableMouseExited(evt);
					}
				});
				
				removeAll();
				add(tree);
				revalidate();
			}
			

		PlatformUI.MIRTH_FRAME.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}
	
	private void refTableMouseExited(MouseEvent evt) {
		tree.clearSelection();
	}
	
	private void refTableMouseDragged(MouseEvent evt) {
	}
	
	private void refTableMouseMoved(MouseEvent evt) {
		int row = tree.getRowForLocation(evt.getPoint().x, evt.getPoint().y );
		
		if ( row >= 0 && row < tree.getRowCount() )				
			tree.setSelectionRow( row );
	}
	
	
	private void processElement(Object elo, DefaultMutableTreeNode dmtn) {
		if (elo instanceof Element) {
			Element el = (Element)elo;
			DefaultMutableTreeNode currentNode =
				new DefaultMutableTreeNode(el.getNodeName());
			String text = "";
			if (el.hasChildNodes()) {
				text = el.getFirstChild().getNodeValue();
			}
			else {
				text = el.getTextContent();
			}
			text = text.trim();
			if((text != null) && (!text.equals("")))
				currentNode.add(new DefaultMutableTreeNode(text));
			
			//processAttributes(el, currentNode);
			
			NodeList children = el.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				processElement(children.item(i), currentNode);
			}
			dmtn.add(currentNode);
		}
	}
	
	private void processAttributes(Element el, DefaultMutableTreeNode dmtn) {
		NamedNodeMap atts = el.getAttributes();
		for (int i = 0; i < atts.getLength(); i++) {
			Attr att = (Attr) atts.item(i);
			DefaultMutableTreeNode attNode =
				new DefaultMutableTreeNode("@"+att.getName());
			attNode.add(new DefaultMutableTreeNode(att.getValue()));
			dmtn.add(attNode);
		}
	}
	
	public class TreeTransferHandler extends TransferHandler {
		
		protected Transferable createTransferable( JComponent c ) {
			try {
				TreeNode tp = (TreeNode)( ( JTree ) c ).getSelectionPath().getLastPathComponent();
				if ( tp == null )
					return null;
				if (!tp.isLeaf())
					return null;
				String leaf = tp.toString();
				// if (leaf.equals(DNDConstants.TASK) || leaf.equals(DNDConstants.TYPE))
				//   return null;
				return new TreeTransferable( tp, _dropPrefix );
			}
			catch ( ClassCastException cce ) {
				return null;
			}
		}
		
		public int getSourceActions( JComponent c ) {
			return COPY;
		}
		
		public boolean canImport( JComponent c, DataFlavor[] df ) {
			return false;
		}
	}
	public void clearMessage() {
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Paste an HL7 message to view HL7 message tree.");
		JTree tree = new JTree(top);
		removeAll();
		add(tree);
		revalidate();
	}
	
	
	
	
}
