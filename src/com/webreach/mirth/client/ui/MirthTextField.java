package com.webreach.mirth.client.ui;

import java.awt.event.KeyEvent;

public class MirthTextField extends javax.swing.JTextField
{
    private Frame parent;

    public MirthTextField()
    {
        super();
        this.parent = PlatformUI.MIRTH_FRAME;
    }
    
    public void processKeyEvent(KeyEvent ev)
    {
        parent.channelEditTasks.getContentPane().getComponent(0).setVisible(true);
        super.processKeyEvent(ev);
    }
}