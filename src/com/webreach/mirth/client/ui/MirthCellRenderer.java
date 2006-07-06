package com.webreach.mirth.client.ui;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class MirthCellRenderer extends DefaultTableCellRenderer
{

  /*
   * @see TableCellRenderer#getTableCellRendererComponent(JTable, Object, boolean, boolean, int, int)
  ImageIcon icon;
  public MirthCellRenderer(String img)
  {
      icon = new ImageIcon(getClass().getResource(img));
      setHorizontalAlignment( CENTER );
  }
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) 
  {
    setIcon(icon);
    return this;
  }
   */

    public MirthCellRenderer()
    {
        super();
        this.setHorizontalAlignment(CENTER);
    }
    
    /*
    public void setValue(Object value)
    {
        if (format == null) {
        setText((value == null) ? "" : value.toString());
        } else {
        setText((value == null) ? "" : format.format(value));
        }
    }
     */
}
