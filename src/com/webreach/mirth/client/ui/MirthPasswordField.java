package com.webreach.mirth.client.ui;

import java.awt.event.KeyEvent;

/** 
 * Mirth's implementation of the JPasswordField.  Adds enabling of
 * the save button in parent.
 */
public class MirthPasswordField extends javax.swing.JPasswordField
{
    private Frame parent;
    
    public MirthPasswordField()
    {
        super();
        this.parent = PlatformUI.MIRTH_FRAME;
    }
    
    public void processKeyEvent(KeyEvent ev)
    {
        parent.enableSave();
        super.processKeyEvent(ev);
    }
}