package com.webreach.mirth.client.ui;

import java.awt.event.KeyEvent;

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