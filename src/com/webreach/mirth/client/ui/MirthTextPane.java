package com.webreach.mirth.client.ui;

import java.awt.event.KeyEvent;
import javax.swing.JPopupMenu;

import javax.swing.text.StyledDocument;

public class MirthTextPane extends javax.swing.JTextPane
{
    private Frame parent;
    private JPopupMenu menu;
    private CutAction cutAction;
    private CopyAction copyAction;
    private PasteAction pasteAction;
    private DeleteAction deleteAction;
    private SelectAllAction selectAllAction;

    public MirthTextPane()
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
    
    public MirthTextPane(StyledDocument doc)
    {
    	super(doc);
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
