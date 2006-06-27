package com.webreach.mirth.client.ui;

import java.awt.event.KeyEvent;

public class MirthPasswordField extends javax.swing.JPasswordField
{
    private Frame parent;
    
    public MirthPasswordField(Frame parent)
    {
        super();
        this.parent = parent;
    }
    
    public void processKeyEvent(KeyEvent ev)
    {
        parent.channelEditTasks.getContentPane().getComponent(0).setVisible(true);
        super.processKeyEvent(ev);
    }
}