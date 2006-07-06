package com.webreach.mirth.client.ui;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class CenterCellRenderer extends DefaultTableCellRenderer
{
    public CenterCellRenderer()
    {
        super();
        this.setHorizontalAlignment(CENTER);
    }
}
