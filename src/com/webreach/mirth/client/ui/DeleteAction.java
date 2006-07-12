package com.webreach.mirth.client.ui;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.text.JTextComponent;

/** Allows for Deleting in text components. */
public class DeleteAction extends AbstractAction
{
    JTextComponent comp;
    
    public DeleteAction(JTextComponent comp)
    { 
        super("Delete"); 
        this.comp = comp; 
    } 
 
    public void actionPerformed(ActionEvent e)
    { 
        comp.replaceSelection(null); 
    } 
 
    public boolean isEnabled()
    { 
        return comp.isEditable() 
                && comp.isEnabled() 
                && comp.getSelectedText() != null; 
    } 

}
