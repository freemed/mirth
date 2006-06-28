package com.webreach.mirth.client.ui.editors;

import javax.swing.DefaultCellEditor;
import org.jdesktop.swingx.JXComboBox;

public class MyComboBoxEditor extends DefaultCellEditor {
    public MyComboBoxEditor(String[] items) {
        super(new JXComboBox(items));
    }
}