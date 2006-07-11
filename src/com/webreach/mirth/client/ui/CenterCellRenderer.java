package com.webreach.mirth.client.ui;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/** CellRenderer that has the alignment set to CENTER. */
public class CenterCellRenderer extends DefaultTableCellRenderer
{
    public CenterCellRenderer()
    {
        super();
        this.setHorizontalAlignment(CENTER);
    }
}
