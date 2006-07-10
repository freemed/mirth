package com.webreach.mirth.client.ui.editors;

import java.awt.Cursor;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import com.webreach.mirth.client.ui.HL7XMLTreePanel;
import com.webreach.mirth.client.ui.PlatformUI;


public class HL7ReferenceTree extends JPanel {
	
	public HL7ReferenceTree() {
		initComponents();
		HL7TabbedPane.addTab("HL7 Tree", treeScrollPane );
	}
	
	private void initComponents() {
        HL7TabbedPane = new JTabbedPane();
        pasteTab = new JPanel();
        pasteScrollPane = new JScrollPane();
        pasteBox = new JTextArea();
        treeScrollPane = new JScrollPane();
        treePanel = new HL7XMLTreePanel();

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

}
