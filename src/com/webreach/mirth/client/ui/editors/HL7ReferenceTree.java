package com.webreach.mirth.client.ui.editors;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import org.jdesktop.layout.GroupLayout;
import com.webreach.mirth.client.ui.HL7TreePanel;


public class HL7ReferenceTree extends JPanel {
	
	public HL7ReferenceTree() {
		initComponents();
	}
	
	private void initComponents() {
        HL7TabbedPane = new JTabbedPane();
        pasteTab = new JPanel();
        pasteScrollPane = new JScrollPane();
        pasteBox = new JTextArea();
        treeTab = new JPanel();
        treeScrollPane = new JScrollPane();
        treePanel = new HL7TreePanel();

        pasteBox.setColumns(20);
        pasteBox.setRows(5);
        pasteScrollPane.setViewportView(pasteBox);

        treeTab.addComponentListener(
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
						
						treePanel.repaint();
						treePanel.revalidate();
					}

					public void componentHidden(ComponentEvent arg0) {
						treePanel.clearMessage();
					}
        			
        		});
        
        GroupLayout pasteTabLayout = new GroupLayout(pasteTab);
        pasteTab.setLayout(pasteTabLayout);
        pasteTabLayout.setHorizontalGroup(
            pasteTabLayout.createParallelGroup(GroupLayout.LEADING)
            .add(pasteTabLayout.createSequentialGroup()
                .addContainerGap()
                .add(pasteScrollPane, GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                .addContainerGap())
        );
        pasteTabLayout.setVerticalGroup(
            pasteTabLayout.createParallelGroup(GroupLayout.LEADING)
            .add(pasteTabLayout.createSequentialGroup()
                .addContainerGap()
                .add(pasteScrollPane, GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                .addContainerGap())
        );
        HL7TabbedPane.addTab("tab1", pasteTab);

        GroupLayout treePanelLayout = new GroupLayout(treePanel);
        treePanel.setLayout(treePanelLayout);
        treePanelLayout.setHorizontalGroup(
            treePanelLayout.createParallelGroup(GroupLayout.LEADING)
            .add(0, 373, Short.MAX_VALUE)
        );
        treePanelLayout.setVerticalGroup(
            treePanelLayout.createParallelGroup(GroupLayout.LEADING)
            .add(0, 331, Short.MAX_VALUE)
        );
        treeScrollPane.setViewportView(treePanel);

        GroupLayout treeTabLayout = new GroupLayout(treeTab);
        treeTab.setLayout(treeTabLayout);
        treeTabLayout.setHorizontalGroup(
            treeTabLayout.createParallelGroup(GroupLayout.LEADING)
            .add(treeTabLayout.createSequentialGroup()
                .addContainerGap()
                .add(treeScrollPane, GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                .addContainerGap())
        );
        treeTabLayout.setVerticalGroup(
            treeTabLayout.createParallelGroup(GroupLayout.LEADING)
            .add(treeTabLayout.createSequentialGroup()
                .addContainerGap()
                .add(treeScrollPane, GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                .addContainerGap())
        );
        HL7TabbedPane.addTab("tab2", treeTab);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.LEADING)
            .add(GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(HL7TabbedPane, GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.LEADING)
            .add(GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(HL7TabbedPane, GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                .addContainerGap())
        );
    }

	
	private JTabbedPane HL7TabbedPane;
	private JPanel pasteTab;
	private JScrollPane pasteScrollPane;
	private JTextArea pasteBox;
	private JPanel treeTab;
	private JScrollPane treeScrollPane;
	private HL7TreePanel treePanel;

}
