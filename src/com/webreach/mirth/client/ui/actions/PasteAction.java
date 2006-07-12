package com.webreach.mirth.client.ui.actions;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.text.JTextComponent;

/** Allows for Pasting in text components. */
public class PasteAction extends AbstractAction
{
    JTextComponent comp;
    
    public PasteAction(JTextComponent comp)
    { 
        super("Paste"); 
        this.comp = comp; 
    } 
 
    public void actionPerformed(ActionEvent e)
    { 
        comp.paste(); 
    } 
 
    public boolean isEnabled()
    { 
        if (comp.isEditable() && comp.isEnabled())
        { 
            Transferable contents = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(this); 
            return contents.isDataFlavorSupported(DataFlavor.stringFlavor); 
        }
        else 
            return false; 
    } 

}
