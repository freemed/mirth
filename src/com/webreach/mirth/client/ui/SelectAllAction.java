package com.webreach.mirth.client.ui;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.text.JTextComponent;

class SelectAllAction extends AbstractAction
{
    JTextComponent comp;
    
    public SelectAllAction(JTextComponent comp)
    { 
        super("Select All"); 
        this.comp = comp; 
    } 
 
    public void actionPerformed(ActionEvent e)
    { 
        comp.selectAll(); 
    } 
 
    public boolean isEnabled()
    { 
        return comp.isEnabled() 
                && comp.getText().length() > 0; 
    } 

}
