package com.webreach.mirth.client.ui.editors;

import java.awt.BorderLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import com.webreach.mirth.client.ui.HL7XMLTreePanel;


public class TabbedReferencePanel extends JPanel {
	
	public TabbedReferencePanel() {
		initComponents();
		HL7TabbedPane.addTab( "HL7 Tree", treeScrollPane );
		HL7TabbedPane.addTab( "Variables", varScrollPane );
	}
	
	private void initComponents() {
        HL7TabbedPane = new JTabbedPane();
        pasteTab = new JPanel();
        pasteScrollPane = new JScrollPane();
        pasteBox = new JTextArea();
        treeScrollPane = new JScrollPane();
        treePanel = new HL7XMLTreePanel();

        String[][] referenceData = new String[2][2];
		referenceData[0][0] = "$localMap";
		referenceData[0][1] = "The local variable map that will be sent to the connector.";
		referenceData[1][0] = "$globalMap";
		referenceData[1][1] = "The global variable map that persists values between channels.";
        
        globalVarTable = new VariableReferenceTable( referenceData );
        globalVarPanel = new JPanel();
		globalVarPanel.setBorder( BorderFactory.createTitledBorder("Global Variables") );
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
		
		varPanel = new JPanel();
		varPanel.setLayout( new BorderLayout() );
		varPanel.add( globalVarPanel, BorderLayout.NORTH );
		varPanel.add( dbVarPanel, BorderLayout.CENTER );
		varScrollPane = new JScrollPane();
		varScrollPane.setViewportView( varPanel );
		
        pasteBox.setColumns(20);
        pasteBox.setRows(5);
        pasteScrollPane.setViewportView(pasteBox);

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
        
        treeScrollPane.setViewportView( treePanel );

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
	private JTextArea pasteBox;
	private JScrollPane treeScrollPane;
	private HL7XMLTreePanel treePanel;
	private VariableReferenceTable globalVarTable;
	private VariableReferenceTable dbVarTable;
	private JPanel globalVarPanel;
	private JPanel dbVarPanel;
	private JScrollPane varScrollPane;
	private JPanel varPanel;
}
