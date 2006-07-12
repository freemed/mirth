package com.webreach.mirth.client.ui.components;

import java.awt.event.KeyEvent;
import javax.swing.JPopupMenu;

import com.webreach.mirth.client.ui.CopyAction;
import com.webreach.mirth.client.ui.CutAction;
import com.webreach.mirth.client.ui.DeleteAction;
import com.webreach.mirth.client.ui.Frame;
import com.webreach.mirth.client.ui.PasteAction;
import com.webreach.mirth.client.ui.PlatformUI;
import com.webreach.mirth.client.ui.SelectAllAction;

/** 
 * Mirth's implementation of the JTextArea.  Adds enabling of
 * the save button in parent.  Also adds a trigger button (right click)
 * editor menu with Cut, Copy, Paste, Delete, and Select All.
 */
public class MirthTextArea extends javax.swing.JTextArea
{
    private Frame parent;
    private JPopupMenu menu;
    private CutAction cutAction;
    private CopyAction copyAction;
    private PasteAction pasteAction;
    private DeleteAction deleteAction;
    private SelectAllAction selectAllAction;

    public MirthTextArea()
    {
        super();
        this.parent = PlatformUI.MIRTH_FRAME;
        
        cutAction = new CutAction(this);
        copyAction = new CopyAction(this);
        pasteAction = new PasteAction(this);
        deleteAction = new DeleteAction(this);
        selectAllAction = new SelectAllAction(this);
        
        menu = new JPopupMenu(); 
        menu.add(cutAction); 
        menu.add(copyAction); 
        menu.add(pasteAction); 
        menu.add(deleteAction); 
        menu.addSeparator();
        menu.add(selectAllAction);

        this.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mousePressed(java.awt.event.MouseEvent evt)
            {
                showPopupMenu(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt)
            {
                showPopupMenu(evt);
            }
        });
    }
    
    /**
     * Shows the popup menu for the trigger button
     */
    private void showPopupMenu(java.awt.event.MouseEvent evt)
    {
        if (evt.isPopupTrigger())
        {
            menu.getComponent(0).setEnabled(cutAction.isEnabled());
            menu.getComponent(1).setEnabled(copyAction.isEnabled());
            menu.getComponent(2).setEnabled(pasteAction.isEnabled());
            menu.getComponent(3).setEnabled(deleteAction.isEnabled());
            menu.getComponent(5).setEnabled(selectAllAction.isEnabled());
            
            menu.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }
    
    public void processKeyEvent(KeyEvent ev)
    {
        parent.enableSave();
        super.processKeyEvent(ev);
    }
}
