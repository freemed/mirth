package com.webreach.mirth.client.ui.editors;

import java.awt.BorderLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.tree.TreeNode;

import com.Ostermiller.Syntax.HighlightedDocument;
import com.webreach.mirth.client.ui.ReferenceTableHandler;
import com.webreach.mirth.client.ui.ReferenceTableTransferable;
import com.webreach.mirth.client.ui.HL7XMLTreePanel;
import com.webreach.mirth.client.ui.PlatformUI;
import com.webreach.mirth.client.ui.SQLParserUtil;
import com.webreach.mirth.client.ui.TreeTransferable;
import com.webreach.mirth.model.Channel;
import com.webreach.mirth.client.ui.MirthTextPane;



public class TabbedReferencePanel extends JPanel {
	
	public TabbedReferencePanel() {
		initComponents();
		HL7TabbedPane.addTab( "HL7 Tree", treeScrollPane );
		HL7TabbedPane.addTab( "Variables", varScrollPane );
	}
	public void Update()
	{
		updateSQL();
	}
	private void updateVariables(String[] variables){
	
		
		dbVarPanel.remove(dbVarTable);
		dbVarTable = new VariableReferenceTable( variables );
		dbVarTable.setDragEnabled(true);
		dbVarTable.setTransferHandler(new ReferenceTableHandler());
		dbVarPanel.add( dbVarTable, BorderLayout.CENTER );
		repaint();
	}
	public void setDroppedTextPrefix(String prefix){
		treePanel.setDroppedTextPrefix(prefix);
	}
	private void updateSQL() {
		Object sqlStatement = PlatformUI.MIRTH_FRAME.channelEditPage.getSourceConnector().getProperties().get("SQLStatement");
		if ((sqlStatement != null) && (!sqlStatement.equals(""))){
			SQLParserUtil spu = new SQLParserUtil((String)sqlStatement);
			updateVariables(spu.Parse());
		}
	}
	private void initComponents() {
        HL7TabbedPane = new JTabbedPane();
        pasteTab = new JPanel();
        pasteScrollPane = new JScrollPane();
        treeScrollPane = new JScrollPane();
        treePanel = new HL7XMLTreePanel();

        String[][] referenceData = new String[2][2];
		referenceData[0][0] = "localMap";
		referenceData[0][1] = "The local variable map that will be sent to the connector.";
		referenceData[1][0] = "globalMap";
		referenceData[1][1] = "The global variable map that persists values between channels.";
        
        globalVarTable = new VariableReferenceTable( referenceData );
        globalVarPanel = new JPanel();
		globalVarPanel.setBorder( BorderFactory.createTitledBorder("Variables") );
		globalVarPanel.setBackground( EditorConstants.PANEL_BACKGROUND );
		globalVarPanel.setLayout( new BorderLayout() );
		globalVarPanel.add( globalVarTable, BorderLayout.CENTER );
		
		String[] temp = {"var1", "var2", "var3", "var4"};
		dbVarTable = new VariableReferenceTable( temp );
        dbVarPanel = new JPanel();
		dbVarPanel.setBorder( BorderFactory.createTitledBorder("Database Variables") );
		dbVarPanel.setBackground( EditorConstants.PANEL_BACKGROUND );
		dbVarPanel.setLayout( new BorderLayout() );
		dbVarPanel.add( dbVarTable, BorderLayout.CENTER );
		dbVarTable.setDragEnabled(true);
		dbVarTable.setTransferHandler(new ReferenceTableHandler());
		
		varPanel = new JPanel();
		varPanel.setLayout( new BorderLayout() );
		varPanel.add( globalVarPanel, BorderLayout.NORTH );
		varPanel.add( dbVarPanel, BorderLayout.CENTER );
		varScrollPane = new JScrollPane();
		varScrollPane.setViewportView( varPanel );
		
        
        pasteScrollPane.setViewportView(pasteBox);
        varScrollPane.addComponentListener(
        		new ComponentListener(){
        			public void componentResized(ComponentEvent arg0) {
					}
        			public void componentMoved(ComponentEvent arg0) {
					}

					public void componentShown(ComponentEvent arg0) {
//						chrisl 7/11/206
						updateSQL();
					}
					

					public void componentHidden(ComponentEvent arg0) {
						
					}
        			
        		});

//		we need to create an HL7 Lexer...	
		HighlightedDocument HL7Doc = new HighlightedDocument();
		HL7Doc.setHighlightStyle( HighlightedDocument.C_STYLE );
		pasteBox = new MirthTextPane( HL7Doc );
		pasteBox.setFont( EditorConstants.DEFAULT_FONT );
		//pasteBox.setColumns(20);
        //pasteBox.setRows(5);
//		this is a tricky way to have "no line-wrap" in a JTextPane;
//		not using JTextArea for compliance with our current syntax
//		highlighting package, and for use of MirthTextPane, which
//		provides right-click edit functionality
		JPanel pasteBoxPanel = new JPanel();
		pasteBoxPanel.setLayout( new BorderLayout() );
		pasteBoxPanel.add( pasteBox, BorderLayout.CENTER );
        pasteScrollPane.setViewportView( pasteBoxPanel );
        treeScrollPane.setViewportView( treePanel );
        

					
        treeScrollPane.addComponentListener(
        		new ComponentListener() {

					public void componentResized(ComponentEvent arg0) {
					}

					public void componentMoved(ComponentEvent arg0) {
					}

					public void componentShown(ComponentEvent arg0) {
						String message = pasteBox.getText();
						if ( message != null || !message.equals("") )
							treePanel.setMessage( message.replaceAll("\\n","\r\n") );
						else
						treePanel.clearMessage();
						treePanel.revalidate();
						treePanel.repaint();
					}

					public void componentHidden(ComponentEvent arg0) {
						treePanel.clearMessage();
					}
        			
        		});
        
        org.jdesktop.layout.GroupLayout pasteTabLayout = new org.jdesktop.layout.GroupLayout(pasteTab);
        pasteTab.setLayout(pasteTabLayout);
        pasteTabLayout.setHorizontalGroup(
            pasteTabLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pasteTabLayout.createSequentialGroup()
                .add(pasteScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE))
        );
        pasteTabLayout.setVerticalGroup(
            pasteTabLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pasteTabLayout.createSequentialGroup()
                .add(pasteScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE))
        );
        HL7TabbedPane.addTab("HL7 Message", pasteTab);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .add(HL7TabbedPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .add(HL7TabbedPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE))
        );
    }

	
	private JTabbedPane HL7TabbedPane;
	private JPanel pasteTab;
	private JScrollPane pasteScrollPane;
	private MirthTextPane pasteBox;
	private JScrollPane treeScrollPane;
	private HL7XMLTreePanel treePanel;
	private VariableReferenceTable globalVarTable;
	private VariableReferenceTable dbVarTable;
	private JPanel globalVarPanel;
	private JPanel dbVarPanel;
	private JScrollPane varScrollPane;
	private JPanel varPanel;
}
