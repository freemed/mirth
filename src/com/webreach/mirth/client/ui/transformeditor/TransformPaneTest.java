package com.webreach.mirth.applets.stepeditor;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class TransformPaneTest {

	/***
	 * @param args
	 */
	//
	public static void main(String[] args) {
		try {
            UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) { }
        
        JFrame frame = new JFrame("mirth :: Transform Panel");
        frame.add(new TransformPane());
        frame.pack();
        frame.setVisible(true);
	}

}
