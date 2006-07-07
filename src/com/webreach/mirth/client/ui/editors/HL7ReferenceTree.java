package com.webreach.mirth.client.ui.editors;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class HL7ReferenceTree extends JPanel {
	
	public HL7ReferenceTree() {
		pasteBox = new JTextArea();
		HL7Tree = new HL7TreePanel();
		processButton = new JButton("Process Message");
		
		pasteBox.setMaximumSize( new Dimension( 100, 300 ) );
		pasteBox.setBorder( BorderFactory.createEtchedBorder() );
		HL7Tree.setBorder( BorderFactory.createEtchedBorder() );
		
		JScrollPane treeScrollPane = new JScrollPane();
		JScrollPane pasteScrollPane = new JScrollPane();
		treeScrollPane.setViewportView( HL7Tree );
		pasteScrollPane.setViewportView( pasteBox );
		
		this.setLayout( new BorderLayout() );
		this.add( treeScrollPane, BorderLayout.NORTH );
		this.add( pasteScrollPane, BorderLayout.CENTER );
		this.add( processButton, BorderLayout.SOUTH );
		
		processButton.addActionListener(
				new ActionListener() {

					public void actionPerformed(ActionEvent arg0) {
						System.out.println(pasteBox.getText());
						HL7Tree.setMessage( pasteBox.getText() );
						
						
					}
					
				});
	}
	
	private HL7TreePanel HL7Tree;
	private JTextArea pasteBox;
	private JButton processButton;
	
}
