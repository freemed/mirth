package com.webreach.mirth.client.ui.transformeditor;

import javax.swing.JFrame;
import javax.swing.UIManager;

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
        frame.add(new TransformerPane( null, null ) );
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	}

}
